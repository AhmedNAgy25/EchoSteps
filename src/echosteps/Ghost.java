package echosteps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ghost {

    private int x, y;
    private final int SIZE = 32;
    private final int SPEED = 4;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
    
     * Moves the ghost towards the target position (e.g., player), with a small
     * delay and randomness to simulate smarter or more natural behavior. -
     * Moves only every 3 frames (based on moveTimer). - Has a 20% chance to
     * skip movement to create unpredictable behavior. - Adjusts X and Y
     * position to move closer to the target.
    
     */

    private int moveTimer = 0;

    public void chasePlayer(int targetX, int targetY) {
        moveTimer++;

        if (moveTimer % 3 != 0) {
            return;
        }

        // 20% chance to pause
        if (Math.random() < 0.2) {
            return;
        }

        if (x < targetX) {
            x += SPEED;
        } else if (x > targetX) {
            x -= SPEED;
        }

        if (y < targetY) {
            y += SPEED;
        } else if (y > targetY) {
            y -= SPEED;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);

        // Draws the upper half of a circle to represent the ghost's head.
        // The arc starts at 0 degrees and spans 180 degrees counterclockwise,
        // forming a smooth top dome using a bounding box of SIZE x SIZE.
        g2.fillArc(x, y, SIZE, SIZE, 0, 180);

        int waveRadius = SIZE / 6;

        // Draws 3 small adjacent circles under the ghost's head to form the wavy tail.
        // Each circle is placed side-by-side using its diameter (waveRadius * 2).
        for (int i = 0; i < 3; i++) {
            g2.fillOval(x + i * waveRadius * 2, y + SIZE / 2, waveRadius * 2, waveRadius * 2);
        }

        g2.setColor(Color.BLACK);
        int eyeSize = SIZE / 6;
        // Draw the ghost's eyes as two small filled circles.
        // The eyes are positioned symmetrically on the upper part of the ghost's head.
        g2.fillOval(x + SIZE / 4, y + SIZE / 4, eyeSize, eyeSize);
        g2.fillOval(x + 2 * SIZE / 4, y + SIZE / 4, eyeSize, eyeSize);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}
