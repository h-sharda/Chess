package Version4.src;

class Empty extends Piece {

    Empty(char name, int value, char colour) {
        super(name, value, colour);
    }

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        return false;
    }
}