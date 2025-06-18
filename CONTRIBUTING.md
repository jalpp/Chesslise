## 👋 **How to add Chesslise to your discord server**
- Head over to [App Directory](https://discord.com/application-directory/930544707300393021)
- Click on "Add to Server"
- If you are not logged in to discord you might have to log in
- You would have to get a server selection list that you own, pick the server you want to install Lise in
- View Lise's permission and click "Authorize"
- To check if Lise has joined, go to the server where you installed Lise and run /help or /play and see if the LISEBOT selection menu opens up
- You're done! Please read the command information below you can also run /help to view it in discord.

## 🔧 **Running Chesslise Locally**
to run Chesslise you need to have Java 21, Maven, your own MongoDB instance with prod and beta collections.

Follow these steps to set up and run Chesslise on your local machine:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/jalpp/Chesslise.git
   cd Chesslise/chesslise
   ```
2. **Set up environment variables:**
    - Copy the `copy.env` file to `.env`:
   ```bash
   cp copy.env .env
   ```
    - Fill in the required variables in the `.env` file. Note ignore production Discord token, unless you want to run production/beta bots
3. **Build the project:**
   ```bash
   mvn clean install
   ```
4. **Run the bot:**
   ```bash
   java -jar target/ChessLise-17.0-SNAPSHOT-jar-with-dependencies.jar
   ```
5. **Check bot status:**
    - if you did everything right, the below should be printed on console, and Chesslise will be online in Discord server
   ```
   [Chesslise Status]: Beta Successfully Running
   [Chesslise Status]: Successfully Connected To Database
    ```   

The bot should now be running locally and can be tested on your Discord server!

---

## ⚙️ **Puzzle API Integration: Chessgubbins**
Chesslise now utilizes the Chessgubbins Puzzle API to fetch random puzzles based on themes. This integration allows for more varied and updated puzzles.

If the Chessgubbins API is temporarily unavailable, Chesslise falls back to using a local CSV database (lichess_db_puzzle.csv). When contributing to the project:

Please ensure any changes to puzzle handling or difficulty adjustments consider both API and local CSV processes.
Add or update error handling or logging if issues with the Chessgubbins API occur.

---

# Notes:

## CSSN test cases
you run run cssn test cases if you made any changes to the cssn pairing algo espically the Pairing class

## Lichess Puzzle DB

to run /puzzle <Lichess puzzle db> you need to download Lichess puzzle db from [here](https://database.lichess.org/#puzzles) and place it beside at the root of the project. I know this is (hacky) but for now, it is how it is.

##
`/chesslisesky` & `/chessliseskycc` contains source code for ChessLise puzzle on BlueSky

---
## Dev Discord
Join the dev Discord if you are helping with a issue or just want to hangout
[Discord](https://discord.gg/T2eH3tQjKC)


