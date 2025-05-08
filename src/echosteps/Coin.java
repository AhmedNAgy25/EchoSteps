package echosteps;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {

    private int x, y;
    private final int SIZE = 16;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, SIZE, SIZE);

        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("$");
        int textHeight = fm.getHeight();
        int textX = x + (SIZE - textWidth) / 2;
        int textY = y + (SIZE + textHeight) / 2 - fm.getDescent();
        g.drawString("$", textX, textY);

        g.setColor(Color.BLACK);
        g.drawOval(x, y, SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}
