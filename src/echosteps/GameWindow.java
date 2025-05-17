package echosteps;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;

    public GameWindow() {
        setTitle("EchoSteps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);

        // Add window listener for cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.cleanup();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        new GameWindow();
    }
} 