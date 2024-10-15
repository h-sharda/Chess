package Version4.src;

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
        //♔♕♖♗♘♙ ♚♛♜♝♞♟
        if (NAME == 'K') return ""+ '♚';
        if (NAME == 'Q') return ""+ '♛';
        if (NAME == 'R') return ""+ '♜';
        if (NAME == 'N') return ""+ '♞';
        if (NAME == 'B') return ""+ '♝';
        if (NAME == 'P') return ""+ '♟';

        return " ";
    }

    public abstract boolean moveTo( Piece[][] board, int startRow, int startCol, int endRow, int endCol);

}