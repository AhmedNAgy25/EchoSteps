package echosteps;

import java.awt.Color;

public final class GameConstants {
    // Window dimensions
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    
    // Tile and entity sizes
    public static final int TILE_SIZE = 32;
    public static final int PLAYER_SIZE = 32;
    public static final int GHOST_SIZE = 32;
    public static final int COIN_SIZE = 16;
    
    // Movement speeds
    public static final int PLAYER_SPEED = 5;
    public static final int GHOST_SPEED = 6;
    
    // Game settings
    public static final int INITIAL_COINS = 10;
    public static final int GAME_TICK_RATE = 16;
    public static final int COIN_GLOW_DURATION = 10;
    
    // Ghost behavior
    public static final int GHOST_MOVE_DELAY = 2;
    public static final double GHOST_PAUSE_CHANCE = 0.15;
    public static final int MOVEMENT_THRESHOLD = 1;
    
    // Colors
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    public static final Color WALL_COLOR = Color.GRAY;
    public static final Color PLAYER_COLOR = new Color(0, 120, 255);
    public static final Color PLAYER_HIGHLIGHT = new Color(100, 180, 255, 100);
    public static final Color COIN_COLOR = Color.YELLOW;
    public static final Color GHOST_COLOR = new Color(255, 255, 255, 200);
    
    private GameConstants() {
        // Prevent instantiation
    }
} 