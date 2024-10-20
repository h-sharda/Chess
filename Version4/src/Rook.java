package Version4.src;

class Rook extends Piece {

    Rook(char name, int value, char colour) {
        super(name, value, colour);
    }

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        // MOVE VERTICALLY
        if ( endRow == startRow ){
            if (startCol > endCol){
                for (int i = startCol-1; i > endCol; i--){
                    if (board[startRow][i].colour != ' ' ) return false;
                }
            } else {
                for (int i = startCol +1; i < endCol ; i++){
                    if (board[startRow][i].colour != ' ') return false;
                }
            }
            return true;
        }

        // MOVE HORIZONTALLY
        if ( endCol == startCol){
            if (startRow > endRow){
                for (int i = startRow-1; i > endRow; i--){
                    if (board[i][startCol].colour != ' ' ) return false;
                }
            } else {
                for (int i = startRow+1; i < endRow; i++){
                    if (board[i][startCol].colour != ' ' ) return false;
                }
            }
            return true;
        }

        // CASTLING IS DONE BY KING NOT THE ROOK
        return false;
    }
}