# EchoSteps

- EchoSteps is a 2D Java-based arcade game built using the Swing library. In this game, the player navigates a grid-based map to collect coins while avoiding ghosts. With each level, the difficulty increases by introducing more ghosts and faster challenges. The game features smooth animations, sound effects, and a clean retro UI.

![Game](https://github.com/user-attachments/assets/0b6fc591-f4b5-4642-ae74-ab1b2362011a)


## 📁 Project Structure

```
├── src/
│   └── echosteps/
│       ├── Main.java              # Entry point of the game
│       ├── GamePanel.java         # Main game panel and logic
│       ├── GameWindow.java        # Game window management
│       ├── Player.java            # Player character implementation
│       ├── Ghost.java             # Ghost entity implementation
│       ├── Coin.java              # Collectible items
│       ├── SoundManager.java      # Sound system management
│       └── GameConstants.java     # Game constants and configurations
├── assets/                        # Game assets and resources
├── sounds/                        # Sound files
└── .gitignore                     # Git ignore configuration
```

## ✨ Features

![Player_Name](https://github.com/user-attachments/assets/c9978a09-6848-41b5-a795-a97e1b1c3bcb)

![Levels](https://github.com/user-attachments/assets/17d7430f-247d-46f9-9682-767259273fb2)


- 🎵 Interactive gameplay with sound-based mechanics
- 🎯 Player movement and controls
- 👻 Ghost entities with unique behaviors
- 💰 Coin collection system
- 🔊 Sound management system
- 🖼️ Custom game window and panel implementation

![Game_Over](https://github.com/user-attachments/assets/536e9fb4-465f-424d-ba71-c158487a35ad)

## 🛠️ Under the Hood

- Manual game loop using Timer and event-driven updates

- Custom rendering using Graphics2D and double buffering

- Object-oriented structure with encapsulated game entities

- Simple and extendable architecture for future enhancement


## 💻 Requirements

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)

## 🚀 How to Run

1. Ensure you have Java installed on your system
2. Compile the Java files:
   ```bash
   javac src/echosteps/*.java
   ```
3. Run the game:
   ```bash
   java -cp src echosteps.Main
   ```

## 🛠️ Development

The game is built using Java Swing for the graphical interface and includes custom implementations for:

- 🎮 Game mechanics
- 🔊 Sound management
- 👾 Entity behaviors
- 💥 Collision detection
- 🖥️ Window management

## Team Members

Thanks to these amazing people for their contributions:

- [Ahmed Nagy]
- [Abdelrahman Elaraby]
- [Mohamed Abdullah]
- [Ali Maher]
- [Fekry Ahmed]
- [Abdelrahman Mostafa]
- [Mazen Abdelrasheed]
