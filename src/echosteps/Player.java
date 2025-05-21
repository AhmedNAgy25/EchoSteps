package echosteps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int dx, dy;
    private boolean isMoving = false;
    private String direction = "idle"; // "left", "right", "idle"
    private int lastX, lastY;
    private final Rectangle bounds;
    private static final Rectangle[] WALLS = {
            new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.WINDOW_HEIGHT), // Left wall
            new Rectangle(GameConstants.WINDOW_WIDTH - GameConstants.TILE_SIZE, 0, GameConstants.TILE_SIZE,
                    GameConstants.WINDOW_HEIGHT), // Right wall
            new Rectangle(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.TILE_SIZE), // Top wall
            new Rectangle(0, GameConstants.WINDOW_HEIGHT - GameConstants.TILE_SIZE, GameConstants.WINDOW_WIDTH,
                    GameConstants.TILE_SIZE) // Bottom wall
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

        int newX = x + dx;
        int newY = y + dy;

        bounds.setLocation(newX, newY);

        boolean canMoveX = true;
        boolean canMoveY = true;

        for (Rectangle wall : WALLS) {
            if (bounds.intersects(wall)) {
                if (wall.width > wall.height) {
                    canMoveY = false;
                } else {
                    canMoveX = false;
                }
            }
        }

        if (canMoveX) {
            x = newX;
        }
        if (canMoveY) {
            y = newY;
        }

        bounds.setLocation(x, y);

        isMoving = (Math.abs(x - lastX) > GameConstants.MOVEMENT_THRESHOLD ||
                Math.abs(y - lastY) > GameConstants.MOVEMENT_THRESHOLD);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        switch (direction) {
            case "left":
                drawFacingLeft(g2d);
                break;
            case "right":
                drawFacingRight(g2d);
                break;
            default:
                drawIdle(g2d);
                break;
        }
    }

   private void drawIdle(Graphics2D g2d) {
    int offsetX = x + (GameConstants.TILE_SIZE - 14) / 2;
    int offsetY = y + (GameConstants.TILE_SIZE - 33) / 2;

    g2d.setColor(new Color(0, 100, 0)); 
    g2d.fillRoundRect(offsetX, offsetY, 14, 7, 4, 4);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 3, 11, 7, 4, 4);

    g2d.setColor(Color.BLACK);
    g2d.fillOval(offsetX + 3, offsetY + 6, 1, 1);
    g2d.fillOval(offsetX + 8, offsetY + 6, 1, 1); 

    g2d.setColor(new Color(0, 150, 0));
    g2d.fillRoundRect(offsetX, offsetY + 11, 14, 12, 4, 4);

    g2d.setColor(new Color(60, 60, 60)); 
    g2d.fillRect(offsetX, offsetY + 23, 14, 2);

    g2d.setColor(new Color(100, 50, 0)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 26, 4, 7, 2, 2); 
    g2d.fillRoundRect(offsetX + 9, offsetY + 26, 4, 7, 2, 2);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX - 3, offsetY + 14, 3, 7, 2, 2);  
    g2d.fillRoundRect(offsetX + 14, offsetY + 14, 3, 7, 2, 2);
}


private void drawFacingLeft(Graphics2D g2d) {
    int offsetX = x + (GameConstants.TILE_SIZE - 14) / 2;
    int offsetY = y + (GameConstants.TILE_SIZE - 33) / 2;

    g2d.setColor(new Color(0, 100, 0)); 
    g2d.fillRoundRect(offsetX, offsetY, 14, 7, 4, 4);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 3, 11, 7, 4, 4);

    g2d.setColor(Color.BLACK);
    g2d.fillOval(offsetX + 3, offsetY + 6, 1, 1);
    g2d.fillOval(offsetX + 6, offsetY + 6, 1, 1);

    g2d.setColor(new Color(0, 150, 0));
    g2d.fillRoundRect(offsetX, offsetY + 11, 14, 12, 4, 4);

    g2d.setColor(new Color(60, 60, 60)); 
    g2d.fillRect(offsetX, offsetY + 23, 14, 2);

    g2d.setColor(new Color(100, 50, 0)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 26, 4, 7, 2, 2); 
    g2d.fillRoundRect(offsetX + 9, offsetY + 26, 4, 7, 2, 2);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX - 4, offsetY + 14, 4, 7, 2, 2);
}


private void drawFacingRight(Graphics2D g2d) {
    int offsetX = x + (GameConstants.TILE_SIZE - 14) / 2;
    int offsetY = y + (GameConstants.TILE_SIZE - 33) / 2;

    g2d.setColor(new Color(0, 100, 0)); 
    g2d.fillRoundRect(offsetX, offsetY, 14, 7, 4, 4);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 3, 11, 7, 4, 4);

    g2d.setColor(Color.BLACK);
    g2d.fillOval(offsetX + 6, offsetY + 6, 1, 1);
    g2d.fillOval(offsetX + 9, offsetY + 6, 1, 1);

    g2d.setColor(new Color(0, 150, 0));
    g2d.fillRoundRect(offsetX, offsetY + 11, 14, 12, 4, 4);

    g2d.setColor(new Color(60, 60, 60)); 
    g2d.fillRect(offsetX, offsetY + 23, 14, 2);

    g2d.setColor(new Color(100, 50, 0)); 
    g2d.fillRoundRect(offsetX + 1, offsetY + 26, 4, 7, 2, 2); 
    g2d.fillRoundRect(offsetX + 9, offsetY + 26, 4, 7, 2, 2);

    g2d.setColor(new Color(255, 220, 180)); 
    g2d.fillRoundRect(offsetX + 15, offsetY + 14, 4, 7, 2, 2);
}



    public Rectangle getBounds() {
        return bounds;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (dx == 0)
                    dx = -GameConstants.PLAYER_SPEED;
                direction = "left";
                break;
            case KeyEvent.VK_RIGHT:
                if (dx == 0)
                    dx = GameConstants.PLAYER_SPEED;
                direction = "right";
                break;
            case KeyEvent.VK_UP:
                if (dy == 0)
                    dy = -GameConstants.PLAYER_SPEED;
                break;
            case KeyEvent.VK_DOWN:
                if (dy == 0)
                    dy = GameConstants.PLAYER_SPEED;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (dx < 0)
                    dx = 0;
                direction = "idle";
                break;
            case KeyEvent.VK_RIGHT:
                if (dx > 0)
                    dx = 0;
                direction = "idle";
                break;
            case KeyEvent.VK_UP:
                if (dy < 0)
                    dy = 0;
                break;
            case KeyEvent.VK_DOWN:
                if (dy > 0)
                    dy = 0;
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

    public void setDirection(String dir) {
        this.direction = dir;
    }

    public String getDirection() {
        return direction;
    }
}
