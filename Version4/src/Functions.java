package Version4.src;

import java.util.Stack;

class Functions {

    // MAKES A DEEP COPY OF A PIECE
    static Piece makePieceCopy(Piece A){
        char colour = A.colour;
        char name = A.name;

        Piece copy;
        if (A.name == 'P') copy = new Pawn (name, 1, colour);
        else if (A.name == 'B') copy = new Bishop (name, 3, colour);
        else if (A.name == 'N') copy = new Knight (name, 3, colour);
        else if (A.name == 'R') copy = new Rook (name, 5, colour);
        else if (A.name == 'Q') copy = new Queen (name, 9, colour);
        else if (A.name == 'K') copy = new King (name, 0, colour);
        else copy = new Empty (name, 0, colour);

        copy.noOfMoves = A.noOfMoves;
        copy.isSafeForBlack = A.isSafeForBlack;
        copy.isSafeForWhite = A.isSafeForWhite;

        return copy;
    }
    // MAKES A DEEP COPY OF THE BOARD
    static Piece[][] makeBoardCopy(Piece[][] board){
        Piece[][] copy = new Piece[8][8];
        for (int i =0; i< 8; i++){
            for (int j=0; j< 8; j++){
                copy[i][j] = makePieceCopy(board[i][j]);
            }
        }
        return copy;
    }


    // INITIALIZES THE BOARD
    static void initializeBoard(Piece[][] board, Stack<Piece[][]> st){
        // ALL PAWNS
        for (int i =0; i< 8; i++) {
            board[1][i] = new Pawn('P', 1, 'W');
            board[6][i] = new Pawn('P', 1, 'B');
        }

        // WHITE PIECES
        board[0][4] = new King('K',0,'W');
        board[0][3] = new Queen('Q',9,'W');
        board[0][0] = new Rook('R',5,'W');
        board[0][7] = new Rook('R',5,'W');
        board[0][1] = new Knight('N',3,'W');
        board[0][6] = new Knight('N',3,'W');
        board[0][2] = new Bishop('B',3,'W');
        board[0][5] = new Bishop('B',3,'W');
        // BLACK PIECES
        board[7][4] = new King('K',0,'B');
        board[7][3] = new Queen('Q',9,'B');
        board[7][0] = new Rook('R',5,'B');
        board[7][7] = new Rook('R',5,'B');
        board[7][1] = new Knight('N',3,'B');
        board[7][6] = new Knight('N',3,'B');
        board[7][2] = new Bishop('B',3,'B');
        board[7][5] = new Bishop('B',3,'B');

        // EMPTY SQUARES
        for (int i =2; i<= 5; i++){
            for (int j =0; j< 8; j++){
                board[i][j] = new Empty(' ', 0,' ');
            }
        }
        // PUSHING THE INITIAL STATE TO THE STACK
        st.push(makeBoardCopy(board));
    }


    // TO RESET ALL SQUARE AS SAFE, TO PREVENT CARRY-ON OF PREVIOUS THREATS
    static void boardSafetyReset(Piece[][] board){
        for (int i=0; i< 8; i++){
            for (int j =0; j< 8; j++){
                board[i][j].isSafeForWhite = true;
                board[i][j].isSafeForBlack = true;
            }
        }
    }


    // CHECKS WHICH SQUARE IS SAFE FOR WHICH COLOUR
    static void boardSafetyUpdate(Piece[][] board){
        for(int i =0; i< 8; i++){
            for (int j=0; j<8; j++){
                Piece A = board[i][j];
                char currentPlayer = A.colour;
                if (currentPlayer == ' ') continue;
                for (int k =0; k< 8; k++){
                    for (int l =0; l < 8; l++){
                        if ( A.moveTo(board, i, j, k, l)){
                            if (currentPlayer == 'W') board[k][l].isSafeForBlack = false;
                            else board[k][l].isSafeForWhite = false;
                        }
                    }
                }
            }
        }
    }


    // CHECKS IF THE KING OF A PLAYER IS SAFE
    static boolean isKingUnsafe(Piece[][] board, char currentPlayer){
        for (int i =0; i< 8; i++){
            for (int j =0; j< 8 ; j++){
                if ( board[i][j].name == 'K' && board[i][j].colour == currentPlayer){
                    return currentPlayer == 'W' ? !board[i][j].isSafeForWhite : !board[i][j].isSafeForBlack;
                }
            }
        }
        // UNREACHED POSITION, KING NOT FOUND
        return true;
    }


    // MOVE MAKING FUNCTION, HEART OF THE LOGIC
    static boolean makeMove(Piece[][] board, int startRow, int startCol, int endRow, int endCol, char currentPlayer, Stack<Piece[][]> st){

        Piece A = board[startRow][startCol];
        Piece B = board[endRow][endCol];

        // CHECKS VARIOUS CONDITIONS BEFORE MAKING THE MOVE
        if (A.colour != currentPlayer) return false;
        else if (B.colour == A.colour) return false;
        else if (!A.moveTo(board, startRow, startCol,endRow, endCol)) return false;

        // TAKING ACCOUNT FOR SPECIAL MOVES
        if(A.name == 'P' && Pawn.pawnPromotionFlag) {
            if(!pawnPromotion(board, startRow, startCol, endRow, endCol)) return false;
        } else if (A.name == 'P' && Pawn.enPassantFlag){
            enPassant(board, startRow, startCol, endRow, endCol);
        } else if (A.name =='K' && King.castlingFlag){
            castling(board, startRow, startCol, endRow, endCol);
        } else { // MOVING NORMALLY
            A.noOfMoves++;
            board[endRow][endCol] = A;
            board[startRow][startCol] = new Empty(' ', 0, ' ');
        }

        boardSafetyReset(board);
        boardSafetyUpdate(board);
        st.push(makeBoardCopy(board));

        // REVERTING TO PREVIOUS BOARD STATE IF THE KING IS UNSAFE
        if(isKingUnsafe(board, currentPlayer)){
            undo (board, st);
            return false;
        } else return true;
    }
    // SPECIAL MOVES
    static void castling(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        board[endRow][endCol] = board[startRow][startCol];
        board[endRow][endCol].noOfMoves++;
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        if ( endCol == 6) {
            board[endRow][5] = board[endRow][7];
            board[endRow][7] = new Empty(' ', 0,' ');
        } else if ( endCol == 2) {
            board[endRow][3] = board[endRow][0];
            board[endRow][0] = new Empty(' ', 0, ' ');
        }
    }
    static void enPassant(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        board[endRow][endCol] = board[startRow][startCol];
        board[endRow][endCol].noOfMoves++;
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        board[startRow][endCol] = new Empty(' ', 0, ' ');
    }
    static boolean pawnPromotion(Piece [][] board, int startRow, int startCol, int endRow, int endCol){
        int choice = 0;
        if (ChessGUI.isMoveReal) choice = ChessGUI.handlePromotion();
        char player = board[startRow][startCol].colour;
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
            default: // SELECTING THE QUEEN FOR DEFAULT SITUATIONS, LIKE WHEN BOT GOES FOR PROMOTION
                board[endRow][endCol] = new Queen('Q', 9, player);
                break;
        }
        board[startRow][startCol] = new Empty(' ', 0, ' ');
        return true;
    }


    // CALCULATES TOTAL NUMBER OF LEGAL MOVES TO BE USED FOR VARIOUS PURPOSES
    static int totalLegalMoves(Piece[][] board, char player, Stack<Piece[][]> st){
        int moves = 0;
        for(int i =0; i< 8; i++){
            for (int j=0; j<8; j++){
                if (player != board[i][j].colour) continue;
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


    // CALCULATES EVALUATION OF CURRENT BOARD STATE
    static int calculateEvaluation(Piece[][] board){
        int ans =0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].colour =='W') ans += board[i][j].value;
                if (board[i][j].colour =='B') ans -= board[i][j].value;
            }
        }
        return ans;
    }


    // UNDOES THE PREVIOUS AND REVERTS THE BOARD TO PREVIOUS STATE
    static void undo(Piece[][] board, Stack<Piece[][]> st) {
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
    static void reset (Piece[][] board, Stack<Piece[][]> st){
        Piece[][] newBoard = new Piece[8][8];
        st.clear();
        initializeBoard(newBoard, st);
        for (int i=0; i< 8; i++){
            System.arraycopy(newBoard[i], 0, board[i], 0, 8);
        }
    }

}
