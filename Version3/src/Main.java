package Version3.src;

public class Main {

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
