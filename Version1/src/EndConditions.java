package Version1.src;

import java.util.Stack;

public class EndConditions {

    private static int moves;
    private static boolean isKingUnsafe;

    // CHECKMATE IF NO LEGAL MOVES AND KING IS UNDER THREAT
    public static boolean checkMate(Piece[][] board, char player, Stack<Piece[][]> st) {
        moves = Functions.totalLegalMoves(board, player, st);
        isKingUnsafe = Functions.isKingUnsafe(board, player);
        return (moves == 0 && isKingUnsafe);
    }

    // STALEMATE IF NO LEGAL MOVES AND KING ISN'T UNDER THREAT
    public static boolean staleMate() {
        return (moves == 0 && !isKingUnsafe);
    }
}
