package echosteps;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {
    private int x, y;
    private final Rectangle bounds;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, GameConstants.COIN_SIZE, GameConstants.COIN_SIZE);
    }

    public void draw(Graphics g) {
        g.setColor(GameConstants.COIN_COLOR);
        g.fillOval(x, y, GameConstants.COIN_SIZE, GameConstants.COIN_SIZE);

        g.setColor(java.awt.Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("$");
        int textHeight = fm.getHeight();
        int textX = x + (GameConstants.COIN_SIZE - textWidth) / 2;
        int textY = y + (GameConstants.COIN_SIZE + textHeight) / 2 - fm.getDescent();
        g.drawString("$", textX, textY);

        g.setColor(java.awt.Color.BLACK);
        g.drawOval(x, y, GameConstants.COIN_SIZE, GameConstants.COIN_SIZE);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
