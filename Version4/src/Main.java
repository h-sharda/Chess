package Version4.src;

import java.awt.*;

public class Main {

    // COLOUR AND FONT CONSTANTS
    static final Color MAIN_MENU_BACKGROUND_COLOR = new Color(220, 220, 220);
    static final Color WELCOME_MESSAGE_COLOR = new Color(60, 60, 60);
    static final Color BUTTON_COLOR = new Color(200,200,255);
    static final Color LIGHT_SQUARE = new Color(240, 210, 170);
    static final Color DARK_SQUARE = new Color(147, 106, 78);
    static final Color LIGHT_PIECE = new Color(255,255,255);
    static final Color DARK_PIECE = new Color(0, 0, 0);
    static final Color SELECTED_SQUARE = new Color(200,200,255);
    static final Color CAPTURABLE_SQUARE = new Color(255, 127, 127);
    static final Color MOVABLE_SQUARE = new Color(64,64,64);
    static final Color KING_UNDER_THREAT = new Color(255,0,0);

    static final Font WELCOME_MESSAGE_FONT = new Font("Goudy Old Style", Font.PLAIN, 50);
    static final Font BUTTON_FONT = new Font("Century Gothic", Font.BOLD, 20);
    static final Font DEFAULT_FONT = new Font("Century Gothic", Font.PLAIN, 16);
    static final Font PIECE_FONT = new Font("Segoe UI Symbol", Font.PLAIN,45);
    static final Font PLAYER_FONT = new Font("Serif", Font.PLAIN, 25);
    static final Font CONTROL_FONT = new Font("Century Gothic", Font.BOLD, 20);


    // GAME FEATURES
    public static int time = 600;
    public static int timeIncrement = 0;

    public static String player1Name = "Player 1";
    public static char player1Type = 'H';

    public static String player2Name = "Player 2";
    public static char player2Type = 'H';

    public static boolean boardFlip = false;

    public static void main(String[] args) {
        System.out.println("Building the game");
        MenuGUI.main(args);
    }
}
