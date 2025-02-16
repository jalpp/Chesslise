# How to add Chesslise to your discord server
- Head over to [App Directory](https://discord.com/application-directory/930544707300393021)
- Click on "Add to Server"
- If you are not logged in to discord you might have to log in
- You would have to get a server selection list that you own, pick the server you want to install Lise in
- View Lise's permission and click "Authorize"
- To check if Lise has joined, go to the server where you installed Lise and run /help or /play and see if the LISEBOT selection menu opens up
- You're done! Please read the command information below you can also run /help to view it in discord.

# How to set up lise locally?

- To set up lise you need to have Java 21, IntelliJ, and Maven
- git clone the project
- use any IDE (IntelliJ recommended)
- configure `ENV_BETA` to either true or false to use the current working env, ideally for development use `DISCORD_BETA_TOKEN`
- configure ``` DISCORD_{ENV}_TOKEN ``` for your own bots' discord token in `.env` 
- configure ``CONNECTION_STRING`` the MongoDB connection String, ``DB_NAME`` The Database name `DB_PLAYER_COLL` the name for player collection (production) ``DB_PLAYER_COLL_Beta`` the name for player collection (Beta) ``DB_CHALL_COLL`` the name for network collection (production) ``DB_CHALL_COLL_Beta`` the network collection (Beta)
- you are ready to run the bot with ``` mvn clean ```
- finally, compile the code ``` mvn compile ```
- Please note you need to follow the same steps as adding Chesslise for your own local bot
- build the project ``` mvn package ```
- run the application ``` java -jar /target/Chesslise-16.0-SNAPSHOT-jar-with-dependencies.jar ```

# CSSN test cases
you run run cssn test cases if you made any changes to the cssn pairing algo espically the Pairing class

# Lichess Puzzle DB

to run /puzzle <Lichess puzzle db> you need to download Lichess puzzle db from [here](https://database.lichess.org/#puzzles) and place it beside at the root of the project. I know this is (hacky) but for now, it is how it is.

## Dev Discord
Join the dev Discord if you are helping with a issue or just want to hangout
[Discord](https://discord.gg/T2eH3tQjKC)


# API

 1. [lichess API](https://lichess.org/api) 
 2. [Chess.com API](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
 3. [JDA 5 discord API](https://github.com/DV8FromTheWorld/JDA)
 4. [StockFish API](https://stockfish.online/)
5. [Chessdb cn](https://chessdb.cn/cloudbookc_info_en.html)

# Database
- MongoDB
# Libraries

 1. [lichess Java Client](https://github.com/tors42/chariot) 
 2. [Tors42 JBang-chariot Java Client Examples](https://github.com/tors42/jbang-chariot)
 3. [Chess.com Java Wrapper](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
 4. [Java Chess Lib](https://github.com/bhlangonijr/chesslib)

# Engines

- [Stockfish](https://stockfishchess.org/)

# Image Generation
- [lichess Gifs](https://github.com/lichess-org/lila-gif)

# Chesslise High-level Design
![Lisev10B drawio](https://github.com/jalpp/LichessSearchEngineBot/assets/92553013/ab1aa349-135b-4f57-a592-bba4e6faf733)
