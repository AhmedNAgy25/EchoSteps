package echosteps;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int dx, dy;
    private boolean isMoving = false;
    private int lastX, lastY;
    private final Rectangle bounds;
    private static final Rectangle[] WALLS = {
        new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT), // Left wall
        new Rectangle(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT), // Right wall
        new Rectangle(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE), // Top wall
        new Rectangle(0, GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE) // Bottom wall
    };

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.lastX = x;
        this.lastY = y;
        this.bounds = new Rectangle(x, y, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE);
    }

    public void move() {
        lastX = x;
        lastY = y;
        
        // Calculate new position
        int newX = x + dx;
        int newY = y + dy;
        
        // Create temporary bounds for collision detection
        bounds.setLocation(newX, newY);
        
        // Check wall collisions
        boolean canMoveX = true;
        boolean canMoveY = true;
        
        for (Rectangle wall : WALLS) {
            if (bounds.intersects(wall)) {
                if (wall.width > wall.height) { // Top or bottom wall
                    canMoveY = false;
                } else { // Left or right wall
                    canMoveX = false;
                }
            }
        }
        
        // Apply movement if no collision
        if (canMoveX) {
            x = newX;
        }
        if (canMoveY) {
            y = newY;
        }
        
        // Update bounds position
        bounds.setLocation(x, y);
        
        // Update movement state
        isMoving = (Math.abs(x - lastX) > GameConstants.MOVEMENT_THRESHOLD || 
                   Math.abs(y - lastY) > GameConstants.MOVEMENT_THRESHOLD);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GameConstants.PLAYER_COLOR);
        g2d.fillRect(x, y, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE);
        
        // Add a subtle highlight
        g2d.setColor(GameConstants.PLAYER_HIGHLIGHT);
        g2d.fillRect(x + 2, y + 2, GameConstants.PLAYER_SIZE - 4, GameConstants.PLAYER_SIZE - 4);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: 
                if (dx == 0) dx = -GameConstants.PLAYER_SPEED; 
                break;
            case KeyEvent.VK_RIGHT: 
                if (dx == 0) dx = GameConstants.PLAYER_SPEED; 
                break;
            case KeyEvent.VK_UP: 
                if (dy == 0) dy = -GameConstants.PLAYER_SPEED; 
                break;
            case KeyEvent.VK_DOWN: 
                if (dy == 0) dy = GameConstants.PLAYER_SPEED; 
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: 
                if (dx < 0) dx = 0; 
                break;
            case KeyEvent.VK_RIGHT: 
                if (dx > 0) dx = 0; 
                break;
            case KeyEvent.VK_UP: 
                if (dy < 0) dy = 0; 
                break;
            case KeyEvent.VK_DOWN: 
                if (dy > 0) dy = 0; 
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return isMoving;
    }
}