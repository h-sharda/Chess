package Version2.src;

import java.util.Stack;

public class Functions {

    public static void initializeBoard(Piece[][] board, Stack<Piece[][]> st){
        // ALL PAWNS
        for (int i =0; i< 8; i++){
            board [1][i] = new Pawn('P', 1, 'W');
            board [6][i] = new Pawn('P', 1, 'B');
        }
        for (int i = 0; i< 8; i++){
            // WHITE PIECES
            if(i == 4) board[0][i] = new King('K',0,'W');
            if(i == 3) board[0][i] = new Queen('Q',9,'W');
            if(i == 0 || i == 7) board[0][i] = new Rook('R',5,'W');
            if(i == 1 || i == 6) board[0][i] = new Knight('N',3,'W');
            if(i == 2 || i == 5) board[0][i] = new Bishop('B',3,'W');
            // BLACK PIECES
            if(i == 4) board[7][i] = new King('K',0,'B');
            if(i == 3) board[7][i] = new Queen('Q',9,'B');
            if(i == 0 || i == 7) board[7][i] = new Rook('R',5,'B');
            if(i == 1 || i == 6) board[7][i] = new Knight('N',3,'B');
            if(i == 2 || i == 5) board[7][i] = new Bishop('B',3,'B');
        }
        // EMPTY SQUARES
        for (int i =2; i<= 5; i++){
            for (int j =0; j< 8; j++){
                board[i][j] = new Empty(' ', 0,' ');
            }
        }
        // PUSHING THE INITIAL STATE TO THE STACK
        st.push(makeBoardCopy(board));
    }

    // TO RESET ALL SQUARE AS SAFE, TO PREVENT CARRY ON PREVIOUS THREATS
    public static void boardSafetyReset(Piece[][] board){
        for (int i=0; i< 8; i++){
            for (int j =0; j< 8; j++){
                board[i][j].IS_SAFE_FOR_BLACK = true;
                board[i][j].IS_SAFE_FOR_WHITE = true;
            }
        }
    }

    // CHECKS WHICH SQUARE IS SAFE FOR WHICH COLOUR
    public static void boardSafetyUpdate(Piece[][] board){
        for(int i =0; i< 8; i++){
            for (int j=0; j<8; j++){
                Piece A = board[i][j];
                char player = A.COLOUR;
                if (player == ' ') continue;
                for (int k =0; k< 8; k++){
                    for (int l =0; l < 8; l++){
                        if ( A.moveTo(board, i, j, k, l)){
                            if (player == 'W') board[k][l].IS_SAFE_FOR_BLACK = false;
                            if (player == 'B') board[k][l].IS_SAFE_FOR_WHITE = false;
                        }
                    }
                }
            }
        }
    }

    // CHECKS IF THE KING OF A PLAYER IS SAFE
    public static boolean isKingUnsafe(Piece[][] board, char player){
        for (int i =0; i< 8; i++){
            for (int j =0; j< 8 ; j++){
                if ( board[i][j].NAME == 'K' && board[i][j].COLOUR == player){
                    if (player == 'W') return !board[i][j].IS_SAFE_FOR_WHITE;
                    else return !board[i][j].IS_SAFE_FOR_BLACK;
                }
            }
        }
        // KING NOT FOUND
        return true;
    }

    // MAKES A DEEP COPY OF A PIECE
    public static Piece makePieceCopy(Piece A){

        Piece copy;

        if (A.NAME == 'P') copy = new Pawn(A.NAME, A.POINTS, A.COLOUR);
        else if (A.NAME == 'B') copy = new Bishop(A.NAME, A.POINTS, A.COLOUR);
        else if (A.NAME == 'N') copy = new Knight(A.NAME, A.POINTS, A.COLOUR);
        else if (A.NAME == 'R') copy = new Rook(A.NAME, A.POINTS, A.COLOUR);
        else if (A.NAME == 'Q') copy = new Queen(A.NAME, A.POINTS, A.COLOUR);
        else if (A.NAME == 'K') copy = new King(A.NAME, A.POINTS, A.COLOUR);
        else copy = new Empty(A.NAME, A.POINTS, A.COLOUR);

        copy.NO_OF_MOVES = A.NO_OF_MOVES;
        copy.IS_SAFE_FOR_BLACK = A.IS_SAFE_FOR_BLACK;
        copy.IS_SAFE_FOR_WHITE = A.IS_SAFE_FOR_WHITE;

        return copy;
    }

    // MAKES A DEEP COPY OF THE BOARD
    public static Piece[][] makeBoardCopy(Piece[][] board){
        Piece[][] copy = new Piece[8][8];
        for (int i =0; i< 8; i++){
            for (int j=0; j< 8; j++){
                copy[i][j] = makePieceCopy(board[i][j]);
            }
        }
        return copy;
    }

    // MOVE MAKING FUNCTION, HEART OF THE LOGIC
    public static boolean makeMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol, char player, Stack<Piece[][]> st){

        Piece A = board[startRow][startCol];
        Piece B = board[endRow][endCol];

        // CHECKS VARIOUS CONDITIONS BEFORE MAKING THE MOVE
        if (A.COLOUR != player) return false;
        else if (B.COLOUR == A.COLOUR)return false;
        else if (!A.moveTo(board, startRow, startCol,endRow, endCol)) return false;

        // TAKING ACCOUNT FOR SPECIAL MOVES
        if(A.NAME == 'P' && Pawn.pawnPromotionFlag) {
            if(!pawnPromotion(board, startRow, startCol, endRow, endCol)) return false;
        } else if (A.NAME == 'P' && Pawn.enPassantFlag){
            enPassant(board, startRow, startCol, endRow, endCol);
        } else if (A.NAME =='K' && King.castlingFlag){
            castling(board, startRow, startCol, endRow, endCol);
        } else {
            // MOVING NORMALLY
            A.NO_OF_MOVES++;
            board[endRow][endCol] = A;
            board[startRow][startCol] = new Empty(' ', 0, ' ');
        }

        boardSafetyReset(board);
        boardSafetyUpdate(board);
        st.push(makeBoardCopy(board));

        // REVERTING TO PREVIOUS BOARD STATE IF THE KING IS UNSAFE
        if(isKingUnsafe(board, player)){
            undo (board, st);
            return false;
        }
        return true;
    }
    // SPECIAL MOVES
    public static void castling(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        board[endRow][endCol] = board[startRow][startCol];
        board[endRow][endCol].NO_OF_MOVES++;
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        if ( endCol == 6) {
            board[endRow][5] = board[endRow][7];
            board[endRow][7] = new Empty(' ', 0,' ');
        }
        if ( endCol == 2) {
            board[endRow][3] = board[endRow][0];
            board[endRow][0] = new Empty(' ', 0, ' ');
        }
    }
    public static boolean pawnPromotion(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        int choice = -1;
        if (ChessGUI.IS_REAL_MOVE) choice = ChessGUI.handlePromotion();
        char player = board[startRow][startCol].COLOUR;
        switch (choice){
            case 1:
                board[endRow][endCol] = new Knight('N', 3, player);
                break;
            case 2:
                board[endRow][endCol] = new Rook('R', 5, player);
                break;
            case 3:
                board[endRow][endCol] = new Bishop('B', 3, player);
                break;
            default: // SELECTING THE QUEEN AND NO INPUT
                board[endRow][endCol] = new Queen('Q', 9, player);
                break;
        }
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        return true;
    }
    public static void enPassant(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        board[endRow][endCol] = board[startRow][startCol];
        board[endRow][endCol].NO_OF_MOVES++;
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        board[startRow][endCol] = new Empty(' ', 0, ' ');
    }

    // CALCULATES TOTAL NUMBER OF LEGAL MOVES TO BE USED FOR VARIOUS PURPOSES
    public static int totalLegalMoves(Piece[][] board, char player, Stack<Piece[][]> st){
        int moves = 0;
        for(int i =0; i< 8; i++){
            for (int j=0; j<8; j++){
                if (player != board[i][j].COLOUR) continue;
                for (int k =0; k< 8; k++){
                    for (int l =0; l < 8; l++){
                        if (makeMove(board,i,j,k,l,player,st)){
                            moves++;
                            undo(board, st);
                            return moves;
                        }
                    }
                }
            }
        }
        return moves;
    }

    // UNDOES THE PREVIOUS AND REVERTS THE BOARD TO PREVIOUS STATE
    public static void undo(Piece[][] board, Stack<Piece[][]> st) {
        if (st.size() <= 1) return;
        st.pop();
        Piece[][] lastState = st.peek();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = makePieceCopy(lastState[i][j]);
            }
        }
    }

    // RESETS THE BOARD TO INITIAL STAGE
    public static void reset (Piece[][] board, Stack<Piece[][]> st){
        System.out.println("Resetting the board");
        Piece[][] newBoard = new Piece[8][8];
        st.clear();
        initializeBoard(newBoard, st);
        for (int i=0; i< 8; i++){
            System.arraycopy(newBoard[i], 0, board[i], 0, 8);
        }
    }

}
