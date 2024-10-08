package Version1.src;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    // SCANNER
    public static Scanner sc = new Scanner(System.in);

    // BOARD COMPONENTS
    public static Piece[][] BOARD= new Piece[8][8];
    public static Stack<Piece[][]> BOARD_HISTORY = new Stack<>();

    // MOVE ELEMENTS
    public static char CURRENT_PLAYER = 'W';
    public static int START_ROW, START_COL, END_ROW, END_COL;
    public static boolean IS_REAL_MOVE = false;


    // RUNNER FUNCTION
    public static void main(String[] args){

        // INITIALIZING THE BOARD
        Functions.initializeBoard(BOARD, BOARD_HISTORY);
        displayBoard(BOARD);

        // WHILE GAME ISN'T OVER
        while ( true ){

            System.out.println("\n" + CURRENT_PLAYER + "'s TURN");
            if (Functions.isKingUnsafe(BOARD, CURRENT_PLAYER)){
                System.out.println("You are under check");
            }

            boolean moved = false;
            while (!moved){
                try {
                    // TAKING USER INPUT
                    System.out.print("Enter starting square: ");
                    String start = sc.next();
                    System.out.print("Enter ending square: ");
                    String end = sc.next();
                    // MAKING THE VALID MOVE
                    if (isMoveValid(start, end)){
                        IS_REAL_MOVE = true;
                        moved = Functions.makeMove(BOARD, START_ROW, START_COL, END_ROW, END_COL, CURRENT_PLAYER, BOARD_HISTORY);
                        IS_REAL_MOVE = false;
                    }
                } catch (Exception e){
                    System.out.println("Enter valid Rows and columns");
                    sc.nextLine();
                }
            }

            //DISPLAYING BOARD STATE
            displayBoard(BOARD);

            //VALIDATING NEXT MOVE
            CURRENT_PLAYER = (CURRENT_PLAYER == 'W') ? 'B' : 'W';

            //CHECKING FOR GAME END CONDITIONS
            if (EndConditions.checkMate(BOARD, CURRENT_PLAYER, BOARD_HISTORY)) {
                char winner = (CURRENT_PLAYER == 'W') ? 'B' : 'W';
                System.out.println(winner + " wins by checkmate!");
                break;
            } else if (EndConditions.staleMate()) {
                System.out.println("game is draw");
                break;
            }
        }
    }

    // DISPLAY BOARD FUNCTION
    public static void displayBoard (Piece [][] board){
        System.out.println("\n   -----------------------------------------");
        for (int i =0 ; i< 8; i++){
            for (int j=0; j< 8; j++){
                if (j==0) System.out.print( (8-i) + "  | ");
                String name = board[7-i][j].getName();
                System.out.print(  name + " | ");
            }
            System.out.println("\n   -----------------------------------------");
        }
        System.out.println("     a    b    c    d    e    f    g    h");
    }

    // DECIDING IF AN INPUT IS VALID TO BE USED FOR MAKING MOVE
    public static boolean isMoveValid(String s1, String s2){

        if (s1.length() != 2 || s2.length() != 2) {
            System.out.println("Invalid input, please enter a valid move (e.g., e2 e4).");
            return false;
        }

        boolean ans = true;
        int a = (s1.charAt(0) - 'a');
        int b = (s1.charAt(1) - '1');
        int c = (s2.charAt(0) - 'a');
        int d = (s2.charAt(1) - '1');
        if ( a> 7 || a< 0) ans = false;
        if ( b> 7 || b< 0) ans = false;
        if ( c> 7 || c< 0) ans = false;
        if ( d> 7 || d< 0) ans = false;

        START_ROW = b;
        START_COL = a;
        END_ROW = d;
        END_COL = c;

        if (!ans){
            System.out.println("Invalid input, please enter a valid move (e.g., e2 e4).");
        }
        return ans;
    }

    // TAKING USER INPUT FOR THE SELECTION OF PIECE FOR PAWN PROMOTION
    public static int handlePromotion(){
        System.out.println("Pawn Promotion, make your choice");
        System.out.println("1: Queen");
        System.out.println("2: Knight");
        System.out.println("3: Rook");
        System.out.println("4: Bishop");

        int choice = 0;
        while (choice == 0){
            try {
                choice = sc.nextInt();
                if ( choice < 1 || choice> 4) throw new IllegalArgumentException();
            } catch (Exception e){
                System.out.println("Enter valid choice");
            }
        }
        return choice;
    }
}
