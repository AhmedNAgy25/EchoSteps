package echosteps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int dx, dy;
    private final int SIZE = 32;
    private final int SPEED = 4;
    private final int MAX_X = 800 - SIZE;
    private final int MAX_Y = 600 - SIZE;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;
        
        // Wall collision
        x = Math.max(SIZE, Math.min(x, MAX_X - SIZE));
        y = Math.max(SIZE, Math.min(y, MAX_Y - SIZE));
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: dx = -SPEED; break;
            case KeyEvent.VK_RIGHT: dx = SPEED; break;
            case KeyEvent.VK_UP: dy = -SPEED; break;
            case KeyEvent.VK_DOWN: dy = SPEED; break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: case KeyEvent.VK_RIGHT: dx = 0; break;
            case KeyEvent.VK_UP: case KeyEvent.VK_DOWN: dy = 0; break;
        }
    }
    public int getX() {
    return x;
    }

    public int getY() {
        return y;
    }
}