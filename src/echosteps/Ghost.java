package echosteps;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Ghost {
    private int x, y;
    private int moveTimer = 0;
    private int lastX, lastY;
    private boolean isMoving = false;
    private final Rectangle bounds;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.lastX = x;
        this.lastY = y;
        this.bounds = new Rectangle(x, y, GameConstants.GHOST_SIZE, GameConstants.GHOST_SIZE);
    }

    public void chasePlayer(int targetX, int targetY) {
        lastX = x;
        lastY = y;

        moveTimer++;

        if (moveTimer % GameConstants.GHOST_MOVE_DELAY != 0) {
            return;
        }

        if (Math.random() < GameConstants.GHOST_PAUSE_CHANCE) {
            return;
        }

        // Calculate direction to player
        int dx = targetX - x;
        int dy = targetY - y;

        // Normalize movement to prevent diagonal speed boost
        if (Math.abs(dx) > Math.abs(dy)) {
            x += (dx > 0) ? GameConstants.GHOST_SPEED : -GameConstants.GHOST_SPEED;
        } else {
            y += (dy > 0) ? GameConstants.GHOST_SPEED : -GameConstants.GHOST_SPEED;
        }

        // Update bounds position
        bounds.setLocation(x, y);

        // Update movement state
        isMoving = (Math.abs(x - lastX) > GameConstants.MOVEMENT_THRESHOLD ||
                Math.abs(y - lastY) > GameConstants.MOVEMENT_THRESHOLD);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw ghost with gradient effect
        g2.setColor(GameConstants.GHOST_COLOR);
        g2.fillArc(x, y, GameConstants.GHOST_SIZE, GameConstants.GHOST_SIZE, 0, 180);

        // Draw wavy tail with smoother curves
        int waveRadius = GameConstants.GHOST_SIZE / 6;
        for (int i = 0; i < 3; i++) {
            g2.fillOval(x + i * waveRadius * 2, y + GameConstants.GHOST_SIZE / 2, waveRadius * 2, waveRadius * 2);
        }

        // Draw eyes with better positioning
        g2.setColor(new java.awt.Color(0, 0, 0, 180));
        int eyeSize = GameConstants.GHOST_SIZE / 6;
        int eyeOffset = GameConstants.GHOST_SIZE / 4;
        g2.fillOval(x + eyeOffset, y + eyeOffset, eyeSize, eyeSize);
        g2.fillOval(x + GameConstants.GHOST_SIZE - eyeOffset - eyeSize, y + eyeOffset, eyeSize, eyeSize);

        // Add subtle glow effect
        g2.setColor(new java.awt.Color(255, 255, 255, 50));
        g2.drawArc(x - 2, y - 2, GameConstants.GHOST_SIZE + 4, GameConstants.GHOST_SIZE + 4, 0, 180);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
