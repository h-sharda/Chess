package Version3.src;

public class Empty extends Piece {

    Empty(char name, int points, char colour) {
        super(name, points, colour);
    }

    @Override
    public boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {
        return false;
    }
}