package Version1.src;

public abstract class Piece {

    public char NAME;
    public int POINTS;
    public char COLOUR;
    public int NO_OF_MOVES = 0;
    public boolean IS_SAFE_FOR_WHITE = true;
    public boolean IS_SAFE_FOR_BLACK = true;

    public Piece(char name, int points, char colour){
        NAME = name;
        POINTS = points;
        COLOUR = colour;
    }

    public String getName(){
        return ""+ COLOUR + NAME;
    }

    public abstract boolean moveTo( Piece[][] board, int startRow, int startCol, int endRow, int endCol);

}