# **♟️ Chesslise**

![](https://img.shields.io/badge/Status-Verified%20Discord%20Bot-brightgreen)  
![](https://img.shields.io/badge/Status-Online-brightgreen)  
![](https://img.shields.io/badge/Discord%20API-JDA-purple)  
![](https://img.shields.io/badge/Available%20On-Discord%20App%20Directory-blue)  
![GitHub License](https://img.shields.io/github/license/jalpp/Chesslise)

<p align="center">
  <img src="https://raw.githubusercontent.com/jalpp/DojoIcons/dd7365ea7d768fe17056d9b14ee6740c2bf4e261/oldIcons/Black%20Blue%20White%20Tactical%20eSports%20Discord%20Logo.png" alt="ChessLise" width="200"/>
</p>

## 🚀 **What is Chesslise?**
**Chesslise** is a **community-driven, open-source** chess bot designed to bring the **ultimate chess experience** to Discord. Whether you're looking to **play, learn, or spectate**, Chesslise makes it seamless—all within your Discord server, Group DMs, or friend chat rooms.

🔥 **Ranked 2nd in Discord’s official App Directory for Chess**, Chesslise is trusted by **1,200+ servers worldwide** to deliver an engaging chess experience.



## 🎯 **Features**
✅ **Play Chess Anywhere** – Challenge friends or compete in public games directly in Discord.  
✅ **Learn & Improve** – Solve more than 40k chess puzzle. From Lichess Puzzle Database  
✅ **Watch Live Games** – Spectate real-time online matches and follow top players.  
✅ **Seamless Integration** – Works effortlessly in **Group DMs, private chats, and community servers**.  
✅ **Stockfish Engine Support** – Play Stockfish with group matches, with easy, medium, hard level support.  
✅ **CSSN – Chesslise Social Server Network** – Discover new chess friends, opponents, and challenges.



## 🔗 **APIs Used**
Chesslise integrates with **multiple powerful chess APIs** to provide a high-quality experience:

- ♟️ [Lichess API](https://lichess.org/api)
- ♟️ [Chess.com API](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
- ♟️ [JDA 5 (Discord API)](https://github.com/DV8FromTheWorld/JDA)
- ♟️ [Stockfish API](https://stockfish.online/)
- ♟️ [ChessDB CN (Opening Book)](https://chessdb.cn/cloudbookc_info_en.html)

---

## 🛠 **Tech Stack**
- **Language**: Java 21
- **Build Tool**: Maven
- **Database**: MongoDB
- **Hosting**: AWS

---

## 📚 **Libraries Used**
- [Lichess Java Client](https://github.com/tors42/chariot)
- [Tors42 JBang-Chariot (Java Client Examples)](https://github.com/tors42/jbang-chariot)
- [Chess.com Java Wrapper](https://github.com/sornerol/chess-com-pubapi-java-wrapper)
- [Java Chess Library](https://github.com/bhlangonijr/chesslib)

---

## 🎨 **Image Generation**
- [Lichess GIFs](https://github.com/lichess-org/lila-gif)

## 🧩 **Puzzle Database**
- [Lichess Puzzle Database](https://database.lichess.org/#puzzles)



## 🔧 **Running Chesslise Discord App Locally**


Follow these steps to set up and run Chesslise on your local machine:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/jalpp/Chesslise.git
   cd Chesslise
   ```
2. **Set up beta environment variables:**
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

## 🔧 **Running Chesslise BlueSky App Locally**

you must have the following:

- AWS account
- AWS CLI
- AWS SAM CLI
- Typescript
- BlueSky account

each lambda as sam readme you can follow 





## 🤝 **Contributing**
Want to contribute? Awesome! 🎉 Check out our [CONTRIBUTING.md](CONTRIBUTING.md) for setup instructions and guidelines.

---

## 🌍 **Join the Chesslise Community**
🔹 **App Directory** – [View Chesslise on Discord](https://discord.com/application-directory/930544707300393021)  
🔹 **Development Discord** – [Join Our Dev Server](https://discord.gg/T2eH3tQjKC)

♟️ **Let’s bring chess to every Discord server!** 🚀



