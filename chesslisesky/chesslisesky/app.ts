import { APIGatewayProxyEvent, APIGatewayProxyResult } from 'aws-lambda';
import { Chesslisesky } from './chesslisesky';

/**
 *
 * Event doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-input-format
 * @param {Object} event - API Gateway Lambda Proxy Input Format
 *
 * Return doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html
 * @returns {Object} object - API Gateway Lambda Proxy Output Format
 *
 */

export const lambdaHandler = async (event: APIGatewayProxyEvent): Promise<APIGatewayProxyResult> => {
    try {
        console.log('starting bot');
        const bot = new Chesslisesky();
        const { gifUrl, puzzleUrl } = await bot.fetchLichessPuzzle();
        const gifBase64 = await bot.fetchGifAsBase64(gifUrl);
        console.log('Fetched puzzle URL:', puzzleUrl);
        await bot.postPuzzle(puzzleUrl, gifBase64);
        await bot.postChallengeURLs();
        console.log('ending...');
        return {
            statusCode: 200,
            body: JSON.stringify({
                message: puzzleUrl,
                gif: gifUrl,
            }),
        };
    } catch (err) {
        console.log(err);
        return {
            statusCode: 500,
            body: JSON.stringify({
                message: 'some error happened',
            }),
        };
    }
};
