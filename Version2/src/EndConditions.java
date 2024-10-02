package Version2.src;

import java.util.Stack;

public class EndConditions {

    private static int moves;
    private static boolean isKingUnsafe;

    public static boolean checkMate(Piece[][] board, char player, Stack<Piece[][]> st){
        moves = Functions.totalLegalMoves(board, player, st);
        isKingUnsafe = Functions.isKingUnsafe(board, player);
        return ( moves == 0 && isKingUnsafe);
    }

    public static boolean staleMate(){
        return ( moves == 0 && !isKingUnsafe);
    }

}