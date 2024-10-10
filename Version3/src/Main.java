package Version3.src;

public class Main {

    // GAME FEATURES
    public static int time = 600;
    public static int timeIncrement = 5;

    public static String player1Name = "Harshit";
    public static char player1Type = 'H';

    public static String player2Name = "Opponent";
    public static char player2Type = 'H';

    public static boolean boardFlip = false;

    public static void main(String[] args) {
        System.out.println("Building the game");
        MenuGUI.main(args);
    }
}
