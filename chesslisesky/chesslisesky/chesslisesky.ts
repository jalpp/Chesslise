import { BskyAgent, RichText } from '@atproto/api';
import axios from 'axios';
import { Chess } from 'chess.js';

export class Chesslisesky {
    private agent: BskyAgent;
    private username: string;
    private password: string;
    private side: string;

    constructor() {
        this.agent = new BskyAgent({ service: 'https://bsky.social' });
        this.username = process.env.BlueSkyUser || '';
        this.password = process.env.BlueSkyUserPassword || '';
        this.side = '';
    }

    private convertDataURIToUint8Array(dataURI: string): Uint8Array {
        const base64 = dataURI.split(',')[1];
        const binary = atob(base64);
        const array = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            array[i] = binary.charCodeAt(i);
        }
        return array;
    }

    public async fetchGifAsBase64(url: string): Promise<string> {
        const response = await axios.get(url, { responseType: 'arraybuffer' });
        const base64 = Buffer.from(response.data, 'binary').toString('base64');
        return `data:image/gif;base64,${base64}`;
    }

    public async fetchLichessPuzzle(): Promise<{ puzzleUrl: string; gifUrl: string }> {
        const response = await axios.get('https://lichess.org/api/puzzle/daily');
        const chess = new Chess();
        const { game, puzzle } = response.data;
        chess.loadPgn(game.pgn);
        console.log(puzzle);
        this.side = chess.fen().includes('b') ? 'black' : 'white';
        const puzzleUrl = `https://lichess.org/training/${puzzle.id}`;
        const gifUrl = `https://lichess1.org/export/fen.gif?fen=${chess.fen()}&color=${this.side}&theme=blue&piece=alpha`;
        return { puzzleUrl, gifUrl };
    }

    public async fetchChallengeUrls(tc: number, inc: number): Promise<{link: string}>{
        const res = await axios.post(`https://lichess.org/api/challenge/open?${tc*60}&${inc*60}`);
        const {id} = res.data;
        const link = `https://lichess.org/${id}`;
        return {link};
    }

    public async postChallengeURLs(): Promise<void> {
        try {
            await this.agent.login({ identifier: this.username, password: this.password });
            
            const rt = new RichText({
                text: `Hey #chessfeed! Its #chess #play time! ♟️ First 4 players to click the challenge links below can start the game on lichess! \n 
                1+0 ${(await this.fetchChallengeUrls(1,0)).link} \n 
                3+0 ${(await this.fetchChallengeUrls(3,0)).link} \n`,
            });

            await rt.detectFacets(this.agent);

            await this.agent.post({
                text: rt.text,
                facets: rt.facets,
                createdAt: new Date().toISOString(),
            });

            console.log('Challenge posted successfully!');
        } catch (error) {
            console.error('Error posting to BlueSky:', error);
        }
    }

    public async postPuzzle(puzzleUrl: string, gifBase64: string): Promise<void> {
        try {
            await this.agent.login({ identifier: this.username, password: this.password });

            const { data } = await this.agent.uploadBlob(this.convertDataURIToUint8Array(gifBase64), {
                encoding: 'image/gif',
            });

            const rt = new RichText({
                text: ` Lichess Daily Puzzle 🧩\n ${this.side} to move \n Try it here: ${puzzleUrl} \n #chess #chessfeed #puzzle`,
            });

            await rt.detectFacets(this.agent);

            await this.agent.post({
                text: rt.text,
                facets: rt.facets,
                embed: {
                    $type: 'app.bsky.embed.images',
                    images: [
                        {
                            alt: 'Lichess Daily Puzzle GIF',
                            image: data.blob,
                            aspectRatio: {
                                width: 400,
                                height: 400,
                            },
                        },
                    ],
                },
                createdAt: new Date().toISOString(),
            });

            console.log('Puzzle posted successfully!');
        } catch (error) {
            console.error('Error posting to BlueSky:', error);
        }
    }
}
