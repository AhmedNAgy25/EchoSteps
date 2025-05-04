package echosteps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ghost {
    private int x, y;
    private final int SIZE = 32;
    private final int SPEED = 4;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int moveTimer = 0;
public void chasePlayer(int targetX, int targetY) {
    moveTimer++;
    
    if (moveTimer % 3 != 0) return;
    
    // 20% chance to pause
    if (Math.random() < 0.2) return;
    
    if (x < targetX) x += SPEED;
    else if (x > targetX) x -= SPEED;
    
    if (y < targetY) y += SPEED;
    else if (y > targetY) y -= SPEED;
}

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}