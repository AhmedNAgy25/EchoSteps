package echosteps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    //mohamed
    private final Timer timer;
    private boolean playerwon;
    private Player player;
    private ArrayList<Coin> coins;
    private ArrayList<Ghost> ghosts;
    private boolean gameOver = false;
    private boolean collectedCoin = false;
    private final Font coinFont;
    private int coinsCollected = 0;
    private boolean showCoinGlow = false;
    private int glowTimer = 0;
    private final Rectangle[] walls;
    private final SoundManager soundManager;
    private final BufferedImage staticBuffer;
    private final Graphics2D staticGraphics;

    private String playerName = "";
    private int currentLevel = 1;
    private boolean playerWon;

    public GamePanel() {
        //mohamed
        this.playerwon = false;
        coinFont = new Font("Monospaced", Font.BOLD, 24);
        setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        setBackground(GameConstants.BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(new KeyInput());

        soundManager = new SoundManager();

        walls = new Rectangle[] {
                new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT),
                new Rectangle(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE, 0, GameConstants.TILE_SIZE,
                        GameConstants.WINDOW_HEIGHT),
                new Rectangle(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE),
                new Rectangle(0, GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE, GameConstants.WINDOW_WIDTH,
                        GameConstants.TILE_SIZE)
        };

        staticBuffer = new BufferedImage(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        staticGraphics = staticBuffer.createGraphics();
        staticGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawStaticElements();

        playerName = JOptionPane.showInputDialog(null, "Enter your name", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            System.exit(0);
        }

        chooseLevel();
        resetGame();

        timer = new Timer(GameConstants.GAME_TICK_RATE, this);
        timer.start();
    }

    private int askLevelOnly() {
        //mohamed
        String[] options = { "Level 1 - Easy", "Level 2 - Medium", "Level 3 - Hard" };
        int choice = JOptionPane.showOptionDialog(null, "Choose a level:", "Level Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == -1) {
            return -1;
        }

        return choice;
    }

    private void drawStaticElements() {
        staticGraphics.setColor(GameConstants.BACKGROUND_COLOR);
        staticGraphics.fillRect(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        staticGraphics.setColor(GameConstants.WALL_COLOR);
        for (Rectangle wall : walls) {
            staticGraphics.fill(wall);
        }
    }

    public void startGame() {
        timer.start();
    }

    private void resetGame() {
        player = new Player(GameConstants.TILE_SIZE * 2, GameConstants.TILE_SIZE * 2);
        coins = new ArrayList<>();
        ghosts = new ArrayList<>();
        coinsCollected = 0;
        gameOver = false;

        int safeXMin = GameConstants.TILE_SIZE;
        int safeYMin = GameConstants.TILE_SIZE;
        int safeXMax = GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE - GameConstants.COIN_SIZE;
        int safeYMax = GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE - GameConstants.COIN_SIZE;

        for (int i = 0; i < GameConstants.INITIAL_COINS; i++) {
            int x, y;
            boolean validPosition;
            do {
                x = safeXMin + (int) (Math.random() * (safeXMax - safeXMin + 1));
                y = safeYMin + (int) (Math.random() * (safeYMax - safeYMin + 1));

                Rectangle newCoinBounds = new Rectangle(x, y, GameConstants.COIN_SIZE, GameConstants.COIN_SIZE);
                validPosition = true;

                for (Coin c : coins) {
                    if (c.getBounds().intersects(newCoinBounds)) {
                        validPosition = false;
                        break;
                    }
                }

                if (player.getBounds().intersects(newCoinBounds)) {
                    validPosition = false;
                }

            } while (!validPosition);

            coins.add(new Coin(x, y));
        }

        for (int i = 0; i < currentLevel; i++) {
            ghosts.add(new Ghost(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE * (i + 2),
                    GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE * (i + 2)));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //mohamed
        if (!gameOver) {
            player.move();
            for (Ghost ghost : ghosts) {
                ghost.chasePlayer(player.getX(), player.getY());
            }

            coins.removeIf(coin -> {
                if (player.getBounds().intersects(coin.getBounds())) {
                    collectedCoin = true;
                    return true;
                }
                return false;
            });

            if (collectedCoin) {
                coinsCollected++;
                showCoinGlow = true;
                glowTimer = GameConstants.COIN_GLOW_DURATION;
                collectedCoin = false;
                soundManager.playCoinSound();

                if (coinsCollected % 2 == 0) {
                    ghosts.add(new Ghost(GameConstants.TILE_SIZE + (int) (Math.random() * 500),
                            GameConstants.TILE_SIZE + (int) (Math.random() * 300)));
                    if (GameConstants.GHOST_SPEED < 5) {
                        GameConstants.GHOST_SPEED += 2;
                    }
                }
            }

            if (coins.isEmpty()) {
                gameOver = true;
                this.playerWon = true;
                soundManager.playWinSound();
                JOptionPane.showMessageDialog(this, "🎉 You Win, " + playerName + "!", "Victory",
                        JOptionPane.INFORMATION_MESSAGE);

                int choice = askLevelOnly();

                if (choice == -1) {
                    System.exit(0);
                } else {

                    chooseLevel();
                    resetGame();
                    gameOver = false;
                    timer.start();

                }
            }
            for (Ghost ghost : ghosts) {
                if (player.getBounds().intersects(ghost.getBounds())) {
                    gameOver = true;
                    soundManager.playLoseSound();
                    JOptionPane.showMessageDialog(this, "💀 Game Over, " + playerName + "!", "Defeat",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            if (showCoinGlow) {
                glowTimer--;
                if (glowTimer <= 0) {
                    showCoinGlow = false;
                }
            }
            repaint();

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!gameOver) {
            g2d.drawImage(staticBuffer, 0, 0, null);

            player.draw(g2d);
            for (Ghost ghost : ghosts) {
                ghost.draw(g2d);
            }
            for (Coin coin : coins) {
                coin.draw(g2d);
            }

            g2d.setColor(new Color(30, 30, 30));
            g2d.fillRect(0, 0, GameConstants.WINDOW_WIDTH, 32);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2d.drawString("Player: " + playerName, 20, 22);
            g2d.drawString("Level: " + currentLevel, GameConstants.WINDOW_WIDTH - 140, 22);

        } else {
            g2d.drawImage(staticBuffer, 0, 0, null);
            g2d.setColor(Color.RED);
            g2d.drawString("GAME OVER! Press R to Restart", 300, 300);
        }
        drawCoinCounter(g2d);

    }

    private void chooseLevel() {

        int choice = askLevelOnly();
        if (choice == -1) {
            System.exit(0);
        } else {
            currentLevel = choice + 1;

            switch (currentLevel) {
                case 1:
                    GameConstants.GHOST_SPEED = 4;
                    break;
                case 2:
                    GameConstants.GHOST_SPEED = 7;
                    break;
                case 3:
                    GameConstants.GHOST_SPEED = 10;
                    break;
                default:
                    GameConstants.GHOST_SPEED = 4;
                    break;
            }
        }

    }

    private void drawCoinCounter(Graphics g) {
        if (showCoinGlow && glowTimer > 0) {
            g.setColor(new Color(255, 215, 0));
            glowTimer--;
        } else {
            g.setColor(new Color(255, 235, 100));
            showCoinGlow = false;
        }

        g.setFont(coinFont);

        String cointText = "x " + coinsCollected;
        int cointTextWidth = g.getFontMetrics().stringWidth(cointText);
        int centerX = GameConstants.WINDOW_WIDTH / 2;
        int coinIconSize = 16;
        int totalWidth = coinIconSize + 5 + cointTextWidth;
        int coinStartX = centerX - totalWidth / 2;
        g.fillOval(coinStartX, 10, coinIconSize, coinIconSize);
        g.setColor(Color.BLACK);
        g.drawOval(coinStartX, 10, coinIconSize, coinIconSize);

        g.setColor(Color.WHITE);
        g.drawString(cointText, coinStartX + coinIconSize + 5, 25);
    }

    private class KeyInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                resetGame();
                repaint();
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_M) {
                soundManager.toggleMute();
            }
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

    public void cleanup() {
        soundManager.cleanup();
        staticGraphics.dispose();
    }
}
