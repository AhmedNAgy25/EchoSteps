package echosteps;

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
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private ArrayList<Coin> coins;
    private Ghost ghost;
    private boolean gameOver = false;
    private boolean collectedCoin = false;
    private Font coinFont;
    private int coinsCollected = 0;
    private boolean showCoinGlow = false;
    private int glowTimer = 0;
    private Rectangle[] walls;
    private SoundManager soundManager;
    
    // Buffer for static elements
    private BufferedImage staticBuffer;
    private Graphics2D staticGraphics;

    public GamePanel() {
        coinFont = new Font("Monospaced", Font.BOLD, 24);
        setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        setBackground(GameConstants.BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(new KeyInput());
        
        // Initialize sound manager
        soundManager = new SoundManager();
        
        // Initialize walls
        walls = new Rectangle[] {
            new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT), // Left wall
            new Rectangle(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT), // Right wall
            new Rectangle(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE), // Top wall
            new Rectangle(0, GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE) // Bottom wall
        };

        // Create static buffer
        staticBuffer = new BufferedImage(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        staticGraphics = staticBuffer.createGraphics();
        staticGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw static elements once
        drawStaticElements();

        resetGame();
        timer = new Timer(GameConstants.GAME_TICK_RATE, this);
        timer.start();
    }

    private void drawStaticElements() {
        // Draw background
        staticGraphics.setColor(GameConstants.BACKGROUND_COLOR);
        staticGraphics.fillRect(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        
        // Draw walls
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
        ghost = new Ghost(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE * 3, 
                         GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE * 3);
        coinsCollected = 0;
        gameOver = false;

        int safeXMin = GameConstants.TILE_SIZE;
        int safeYMin = GameConstants.TILE_SIZE;
        int safeXMax = GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE - GameConstants.COIN_SIZE;
        int safeYMax = GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE - GameConstants.COIN_SIZE;

        // Generate Random coins
        for (int i = 0; i < GameConstants.INITIAL_COINS; i++) {
            int x, y;
            boolean validPosition;
            do {
                x = safeXMin + (int) (Math.random() * (safeXMax - safeXMin + 1));
                y = safeYMin + (int) (Math.random() * (safeYMax - safeYMin + 1));

                Rectangle newCoinBounds = new Rectangle(x, y, GameConstants.COIN_SIZE, GameConstants.COIN_SIZE);
                validPosition = true;

                // Check if coin intersects with another coin
                for (Coin c : coins) {
                    if (c.getBounds().intersects(newCoinBounds)) {
                        validPosition = false;
                        break;
                    }
                }

                // Check if player position intersects with coin position
                if (player.getBounds().intersects(newCoinBounds)) {
                    validPosition = false;
                }

            } while (!validPosition);

            coins.add(new Coin(x, y));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            player.move();
            ghost.chasePlayer(player.getX(), player.getY());

            // Check coin collection
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
            }

            // Check win condition
            if (coins.isEmpty()) {
                gameOver = true;
                soundManager.playWinSound();
                System.out.println("🎉 YOU WIN!");
            }

            // Check lose condition
            if (player.getBounds().intersects(ghost.getBounds())) {
                gameOver = true;
                soundManager.playLoseSound();
                System.out.println("💀 GAME OVER!");
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
            // Draw static elements from buffer
            g2d.drawImage(staticBuffer, 0, 0, null);

            // Draw dynamic elements
            player.draw(g2d);
            ghost.draw(g2d);
            for (Coin coin : coins) {
                coin.draw(g2d);
            }

            // Draw coin counter
            drawCoinCounter(g2d);
        } else {
            // Draw static elements from buffer
            g2d.drawImage(staticBuffer, 0, 0, null);
            
            // Draw game over message
            g2d.setColor(java.awt.Color.RED);
            g2d.drawString("GAME OVER! Press R to Restart", 300, 300);
        }
    }

    private void drawCoinCounter(Graphics g) {
        if (showCoinGlow && glowTimer > 0) {
            g.setColor(new java.awt.Color(255, 215, 0));
            glowTimer--;
        } else {
            g.setColor(new java.awt.Color(255, 223, 0));
            showCoinGlow = false;
        }

        g.setFont(coinFont);
        g.fillOval(20, 10, 16, 16);
        g.setColor(java.awt.Color.BLACK);
        g.drawOval(20, 10, 16, 16);
        g.drawString("x " + coinsCollected, 45, 28);
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
