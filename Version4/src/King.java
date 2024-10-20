package Version4.src;

class King extends Piece {

    King(char name, int value, char colour) {
        super(name, value, colour);
    }

    static boolean castlingFlag = false;

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        castlingFlag = false;

        char currentPlayer = board[startRow][startCol].colour;

        // CANT MOVE TO UNSAFE SQUARE
        if (currentPlayer == 'W' && !board[endRow][endCol].isSafeForWhite) return false;
        if (currentPlayer == 'B' && !board[endRow][endCol].isSafeForBlack) return false;

        // NORMAL MOVEMENT
        if (Math.abs(startCol-endCol) <=1 && Math.abs(startRow-endRow) <=1 ) return true;

        // CASTLING INITIATED BY THE KING
        if (noOfMoves == 0){

            // CHECKING FOR WHITE KING
            if (endRow == 0 && endCol == 6){
                Piece R = board[0][7];
                if (R.name == 'R' && R.colour == 'W' && R.noOfMoves == 0){
                    if( board[0][5].colour == ' ' && board[0][6].colour == ' '){
                        if (board[0][5].isSafeForWhite && board[0][6].isSafeForWhite){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            else if(endRow == 0 && endCol == 2){
                Piece R = board[0][0];
                if (R.name == 'R' && R.colour == 'W' && R.noOfMoves == 0){
                    if( board[0][3].colour == ' ' && board[0][2].colour == ' ' && board[0][1].colour == ' '){
                        if (board[0][2].isSafeForWhite && board[0][3].isSafeForWhite){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            // CHECKING FOR BLACK KING
            else if (endRow == 7 && endCol == 6){
                Piece R = board[7][7];
                if (R.name == 'R' && R.colour == 'B' && R.noOfMoves == 0){
                    if( board[7][5].colour == ' ' && board[7][6].colour == ' '){
                        if (board[7][5].isSafeForBlack && board[7][6].isSafeForBlack){
                            castlingFlag = true;
                            return true;
                        }
                    }
                }
            }
            else if(endRow == 7 && endCol == 2){
                Piece R = board[7][0];
                if (R.name == 'R' && R.colour == 'B' && R.noOfMoves==0){
                    if( board[7][3].colour == ' ' && board[7][2].colour == ' ' && board[7][1].colour == ' '){
                        if (board[7][2].isSafeForBlack && board[7][3].isSafeForBlack){
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