package Version4.src;

class Knight extends Piece{

    Knight(char name, int points, char colour) {
        super(name, points, colour);
    }

    @Override
    public boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        if ( Math.abs(startRow - endRow) == 2 && Math.abs(startCol-endCol) == 1) return true;
        return (Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2);

    }
}