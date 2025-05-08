package echosteps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    private final int TILE_SIZE = 32;
    private final int WIDTH = 800, HEIGHT = 600;
    private Timer timer;
    private Player player;
    private ArrayList<Coin> coins;
    private Ghost ghost;
    private boolean gameOver = false;
    private boolean collectedCoin = false;

    // Coin counter style
    private Font coinFont;
    private int coinsCollected = 0;
    private boolean showCoinGlow = false;
    private int glowTimer = 0;

    public GamePanel() {
        coinFont = new Font("Monospaced", Font.BOLD, 24);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyInput());

        resetGame();
        timer = new Timer(16, this);
        timer.start();
    }

    public void startGame() {
        timer.start();
    }

    private void resetGame() {
        player = new Player(TILE_SIZE * 2, TILE_SIZE * 2);
        coins = new ArrayList<>();
        ghost = new Ghost(WIDTH - TILE_SIZE * 3, HEIGHT - TILE_SIZE * 3);
        coinsCollected = 0;
        gameOver = false;

        int coinSize = 16;
        int safeXMin = TILE_SIZE;
        int safeYMin = TILE_SIZE;
        int safeXMax = WIDTH - TILE_SIZE - coinSize;
        int safeYMax = HEIGHT - TILE_SIZE - coinSize;
        int coinsNum = 10;

        // Generate Random coins
        for (int i = 0; i < coinsNum; i++) {
            int x, y;
            boolean validPosition;
            do {
                x = safeXMin + (int) (Math.random() * (safeXMax - safeXMin + 1));
                y = safeYMin + (int) (Math.random() * (safeYMax - safeYMin + 1));

                Rectangle newCoinBounds = new Rectangle(x, y, coinSize, coinSize);

                validPosition = true;

                 // Check if coin intersect with anothor coin 
                for (Coin c : coins) {
                    if (c.getBounds().intersects(newCoinBounds)) {
                        validPosition = false;
                        break;
                    }
                }

                // Check if player position intersect with coin position
                if (player.getBounds().intersects(newCoinBounds)) {
                    validPosition = false;
                }

            } while (!validPosition);

            coins.add(new Coin(x, y));
        }

    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        if (!gameOver) {
            player.move();
            ghost.chasePlayer(player.getX(), player.getY());

            collectedCoin = false;
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
                glowTimer = 15;
            }

            if (coins.isEmpty()) {
                gameOver = true;
                System.out.println("🎉 YOU WIN!");
            }

            if (player.getBounds().intersects(ghost.getBounds())) {
                gameOver = true;
                System.out.println("💀 GAME OVER!");
            }

            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g
    ) {
        super.paintComponent(g);
        if (!gameOver) {
            g.setColor(new Color(40, 40, 40));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            drawWalls(g);

            player.draw(g);
            ghost.draw(g);
            for (Coin coin : coins) {
                coin.draw(g);
            }

            drawCoinCounter(g);
        } else {
            g.setColor(Color.RED);
            g.drawString("GAME OVER! Press R to Restart", 300, 300);
        }
    }

    private void drawWalls(Graphics g) {
        g.setColor(Color.GRAY);
        for (int x = 0; x < WIDTH; x += TILE_SIZE) {
            g.fillRect(x, 0, TILE_SIZE, TILE_SIZE);
        }
        for (int x = 0; x < WIDTH; x += TILE_SIZE) {
            g.fillRect(x, HEIGHT - TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        for (int y = 0; y < HEIGHT; y += TILE_SIZE) {
            g.fillRect(0, y, TILE_SIZE, TILE_SIZE);
        }
        for (int y = 0; y < HEIGHT; y += TILE_SIZE) {
            g.fillRect(WIDTH - TILE_SIZE, y, TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawCoinCounter(Graphics g) {
        if (showCoinGlow && glowTimer > 0) {
            g.setColor(new Color(255, 215, 0));
            glowTimer--;
        } else {
            g.setColor(new Color(255, 223, 0));
            showCoinGlow = false;
        }

        g.setFont(coinFont);
        g.fillOval(20, 10, 16, 16);
        g.setColor(Color.BLACK);
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
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
