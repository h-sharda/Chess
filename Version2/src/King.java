package Version2.src;

class King extends Piece {

    King(char name, int points, char colour) {
        super(name, points, colour);
    }

    public static boolean castlingFlag = false;
    @Override
    public boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        castlingFlag = false;

        char player = board[startRow][startCol].COLOUR;

        // CANT MOVE TO UNSAFE SQUARE
        if (player=='W' && !board[endRow][endCol].IS_SAFE_FOR_WHITE) return false;
        if (player=='B' && !board[endRow][endCol].IS_SAFE_FOR_BLACK) return false;

        // NORMAL MOVEMENT
        if (Math.abs(startCol-endCol) <=1 && Math.abs(startRow-endRow) <=1 ) return true;

        // CASTLING INITIATED BY THE KING
        if (NO_OF_MOVES == 0){
            // CHECKING FOR WHITE KING
            if (startRow == 0 && endRow == 0 && endCol == 6){
                Piece R = board[0][7];
                if (R.NAME == 'R' && R.COLOUR == 'W' && R.NO_OF_MOVES==0){
                    if( board[0][5].COLOUR==' ' && board[0][6].COLOUR==' '){
                        if (board[0][5].IS_SAFE_FOR_WHITE && board[0][6].IS_SAFE_FOR_WHITE){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            else if(startRow == 0 && endRow == 0 && endCol == 2){
                Piece R = board[0][0];
                if (R.NAME == 'R' && R.COLOUR == 'W' && R.NO_OF_MOVES==0){
                    if( board[0][3].COLOUR==' ' && board[0][2].COLOUR==' ' && board[0][1].COLOUR ==' '){
                        if (board[0][2].IS_SAFE_FOR_WHITE && board[0][3].IS_SAFE_FOR_WHITE){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            // CHECKING FOR BLACK KING
            else if (startRow == 7 && endRow == 7 && endCol == 6){
                Piece R = board[7][7];
                if (R.NAME == 'R' && R.COLOUR == 'B' && R.NO_OF_MOVES==0){
                    if( board[7][5].COLOUR==' ' && board[7][6].COLOUR==' '){
                        if (board[7][5].IS_SAFE_FOR_BLACK && board[7][6].IS_SAFE_FOR_BLACK){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            else if(startRow == 7 && endRow == 7 && endCol == 2){
                Piece R = board[7][0];
                if (R.NAME == 'R' && R.COLOUR == 'B' && R.NO_OF_MOVES==0){
                    if( board[7][3].COLOUR==' ' && board[7][2].COLOUR==' ' && board[7][1].COLOUR ==' '){
                        if (board[7][2].IS_SAFE_FOR_BLACK && board[7][3].IS_SAFE_FOR_BLACK){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}