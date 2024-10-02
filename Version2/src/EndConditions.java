package Version2.src;

import java.util.Stack;

public class EndConditions {

    private static int moves;

    public static boolean checkMate(Piece[][] board, char player, Stack<Piece[][]> st){
        moves = Functions.totalLegalMoves(board, player, st);
        return ( moves == 0 && Functions.isKingUnsafe(board, player))  ;
    }

    public static boolean staleMate(Piece[][] board, char player){
        return ( moves == 0 && !Functions.isKingUnsafe(board, player))  ;
    }

}