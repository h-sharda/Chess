package Version4.src;

import java.util.Stack;

class EndConditions {

    private static int moves;
    private static boolean isKingUnsafe;

    // CHECKMATE IF NO LEGAL MOVES AND KING IS UNDER THREAT
    static boolean checkMate(Piece[][] board, char currentPlayer, Stack<Piece[][]> st) {
        moves = Functions.totalLegalMoves(board, currentPlayer, st);
        isKingUnsafe = Functions.isKingUnsafe(board, currentPlayer);
        return (moves == 0 && isKingUnsafe);
    }

    // STALEMATE IF NO LEGAL MOVES AND KING ISN'T UNDER THREAT
    static boolean staleMate() {
        return (moves == 0 && !isKingUnsafe);
    }

    /* To add
    1) Draw by insufficient material
    2) 3 peat repetition
    3) 50 Move Rule
    4) Resignation
     */
}
