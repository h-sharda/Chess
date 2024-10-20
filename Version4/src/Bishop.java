package Version4.src;

class Bishop extends Piece {

    Bishop(char name, int value, char colour) {
        super(name, value, colour);
    }

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        if ( Math.abs(startCol-endCol) == Math.abs(startRow-endRow)){
            int diff = Math.abs (startCol-endCol);

            // CHECKING ALL 4 DIRECTIONS A BISHOP CAN MOVE
            if ( startCol > endCol && startRow > endRow ){
                for (int i = 1; i< diff; i++){
                    if (board[startRow-i][startCol-i].colour != ' ') return false;
                }
            } else if ( startCol > endCol ){
                for (int i = 1; i< diff; i++){
                    if (board[startRow+i][startCol-i].colour != ' ') return false;
                }
            } else if ( startCol < endCol && startRow > endRow ){
                for (int i = 1; i< diff; i++){
                    if (board[startRow-i][startCol+i].colour != ' ') return false;
                }
            } else if ( startCol < endCol ){
                for (int i = 1; i< diff; i++){
                    if (board[startRow+i][startCol+i].colour != ' ') return false;
                }
            }
            return true;
        }

        return false;
    }
}
