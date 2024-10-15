package Version4.src;

class Bishop extends Piece {

    Bishop(char name, int points, char colour) {
        super(name, points, colour);
    }

    @Override
    public boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        if ( Math.abs(startCol-endCol) == Math.abs(startRow-endRow)){
            int diff = Math.abs (startCol-endCol);

            // CHECKING ALL 4 DIRECTIONS A BISHOP CAN MOVE
            if ( startCol > endCol && startRow > endRow){
                for (int i = 1; i< diff; i++){
                    if (board[startRow-i][startCol-i].COLOUR != ' ') return false;
                }
            }
            if ( startCol > endCol && startRow < endRow){
                for (int i = 1; i< diff; i++){
                    if (board[startRow+i][startCol-i].COLOUR != ' ') return false;
                }
            }
            if ( startCol < endCol && startRow > endRow){
                for (int i = 1; i< diff; i++){
                    if (board[startRow-i][startCol+i].COLOUR != ' ') return false;
                }
            }
            if ( startCol < endCol && startRow < endRow){
                for (int i = 1; i< diff; i++){
                    if (board[startRow+i][startCol+i].COLOUR != ' ') return false;
                }
            }
            return true;
        }

        return false;
    }
}