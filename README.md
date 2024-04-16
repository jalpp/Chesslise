![](https://img.shields.io/badge/Status-Verified%20Discord%20Bot-brightgreen)
![](https://img.shields.io/badge/Status-Online-brightgreen)
![](https://img.shields.io/badge/Discord%20API-JDA-purple)
![](https://img.shields.io/badge/Available%20On-Discord%20App%20Directory%20-blue)

Chesslise is open source Chess based Discord bot, which allows users to play, learn, and watch chess on their Discord server

# Sources

# API

 1. [Lichess API](https://lichess.org/api) 
 2. [Chess.com API](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
 3. [JDA 5 Discord API](https://github.com/DV8FromTheWorld/JDA)
 4. [StockFish API](https://stockfish.online/)

# Libraries

 1. [Lichess Java Client](https://github.com/tors42/chariot) 
 2. [Tors42 JBang-chariot Java Client Examples](https://github.com/tors42/jbang-chariot)
 3. [Chess.com Java Wrapper](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
 4. [Java Chess Lib](https://github.com/bhlangonijr/chesslib)

# Engines

- [Lise Chess Engine](https://github.com/jalpp/LiseChessEngine) 
- [Stockfish](https://stockfishchess.org/)

# Image Generation
- [Lichess Gifs](https://github.com/lichess-org/lila-gif)

# Chesslise High-level Design
![Lisev10B drawio](https://github.com/jalpp/LichessSearchEngineBot/assets/92553013/ab1aa349-135b-4f57-a592-bba4e6faf733)


# How to add Chesslise to your Discord server
- Head over to [App Directory](https://discord.com/application-directory/930544707300393021)
- Click on "Add to Server"
- If you are not logged in to Discord you might have to log in
- You would have to get a server selection list that you own, pick the server you want to install Lise in
- View Lise's permission and click "Authorize"
- To check if Lise has joined, go to the server where you installed Lise and run /help or /play and see if the LISEBOT selection menu opens up
- You're done! Please read the command information below you can also run /help to view it in Discord.

# How to set up lise locally?

- To set up lise you need to have Java 17+, IntelliJ, and Maven
- git clone the project
- use any IDE (IntelliJ recommended)
- configure ``` DISCORD_TOKEN ``` for your own bots' Discord token
- configure ``` LICHESS_TOKEN ``` if you also want to run lise on Lichess
- you are ready to run the bot with ``` mvn clean ```
- finally, compile the code ``` mvn compile ```
- Please note you need to follow the same steps as adding Chesslise for your own local bot
- build the project ``` mvn package ```

# Lichess Commands:
| Commands                   | Description |
|----------------------------| ----------- |
| /help                      | To see command information for the LISEBOT       |
| /profile                   | To see Lichess profiles for given username       |
| /streamers                 | Watch current live streamers |
| /puzzle                    | See the daily Lichess Chesscom puzzle and try to solve it! |
| /play                      | Create an open-ended challenge for Discord users to play with, select time control[1+0,3+0,3+2,5+0,etc] and mode [casual/rated] |
| /arena                     | See the standings and tournament information for the given tournament link|
| /watch                     | View the last Lichess game played by a user  |
| /watchmaster               | View random GM games live in Discord         |
| /broadcast                 | View broadcast info for the current Lichess broadcast |



# Chess.com Commands
| Commands     | Description |
| ----------- | ----------- |
| /puzzle      | Do Daily/Random Chess.com puzzles     |
| /profilecc   | View Chess.com user profile           |


# Engine [Stockfish & Lise] Commands
| Commands     | Description |
| ----------- | ----------- |
| /analyze    |  Pass in FEN to analyze with Stockfish in Discord    |
| /move       |  Play Stockfish engine in Discord          |

# Puzzles
| Commands     | Description |
| ----------- | ----------- |
| /puzzlesolv    |  Pass in FEN to trigger a puzzle solver module   |
| /puzzle      |  solve Lichess/Chess.com puzzles live in Discord        |
| /solve    |  solve live puzzles using chess moves        |


## Create Lichess Games

![Screen Shot 2023-12-24 at 1 50 54 PM](https://github.com/jalpp/LichessSearchEngineBot/assets/92553013/bdf9c7bb-c908-4c57-91c5-165def5cf605)

## Demo

https://youtu.be/ChFw8mT7zBo?si=H5lpF9uOjIKDEIGm

## Terms of Service
The user agrees that they will have to use the latest updated versions of LISEBOT, The User also agrees that some commands may be deleted if the developer does not want to maintain those commands in future. The user is fully responsible for their discord server, and LISEBOT has no access to the server information/ management. The user also agrees to the privacy policy which states that LISEBOT does not and will not store any private information

## Privacy Policy
LISEBOT Does not and will not store any private user information, all bot commands are Lichess, Chess.com, and Stockfish and use slash commands to run commands properly. LISEBOT is not responsible for any user actions on their servers.

## License 
Lise is a non-commercial software made for chess lovers to use their loved sites/features in Discord, this software is licensed under GPL, please read the license for more details. LISEBOT will never charge users for its service, users may get the option to donate to devs if they want.

## App Directory 
[View More](https://discord.com/application-directory/930544707300393021)

## Discord Support Server
[Join](https://discord.gg/uncmhknmYg)

## Version number: 13

## Road map for chesslise
- add chessxplain API
- better profile cards 

## Authors:
- @jalpp Main maintainer of Chesslise 
- @ddugovic Thanks for setting up the GitHub yaml file!
- @tors42 Thanks for helping with the chariot integration! 
- @pad918 Thanks for helping out with the watch command!
- @BreakTos Thanks for /learnchess command!




