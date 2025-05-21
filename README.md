# 🎮 Echo Steps

A Java-based game where players navigate through levels while managing sound and movement mechanics.

## 📝 Description

Echo Steps is a Java game built using Swing that features unique gameplay mechanics centered around sound and movement. The game includes various elements such as players, ghosts, coins, and sound management systems.

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

- 🎵 Interactive gameplay with sound-based mechanics
- 🎯 Player movement and controls
- 👻 Ghost entities with unique behaviors
- 💰 Coin collection system
- 🔊 Sound management system
- 🖼️ Custom game window and panel implementation

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

## 🤝 Contributing

Feel free to contribute to this project by:

1. Forking the repository
2. Creating a new branch
3. Making your changes
4. Submitting a pull request
