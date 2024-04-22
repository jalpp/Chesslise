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
- run the application ``` java -jar /target/Chesslise-13.5-SNAPSHOT-jar-with-dependencies.jar ```
