package Version4.src;

class Queen extends Piece{

    Queen(char name, int value, char colour) {
        super(name, value, colour);
    }

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        char player = board[startRow][startCol].colour;

        // MOVES LIKE A BISHOP AND A ROOK
        Rook R = new Rook('R', 5, player);
        Bishop B = new Bishop('B', 3, player);

        boolean moveStraight = R.moveTo(board, startRow, startCol, endRow, endCol);
        boolean moveDiagonally = B.moveTo(board, startRow, startCol, endRow, endCol);

        return  moveStraight || moveDiagonally ;
    }
}
