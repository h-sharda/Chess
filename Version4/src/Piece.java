package Version4.src;

abstract class Piece {

    char name;
    int value;
    char colour;
    int noOfMoves = 0;
    boolean isSafeForWhite = true;
    boolean isSafeForBlack = true;

    Piece(char name, int value, char colour){
        this.name = name;
        this.value = value;
        this.colour = colour;
    }

    String getName(){
        //♔♕♖♗♘♙ ♚♛♜♝♞♟

        if (name == 'P') return "♟";
        else if (name == 'B') return "♝";
        else if (name == 'N') return "♞";
        else if (name == 'R') return "♜";
        else if (name == 'Q') return "♛";
        else if (name == 'K') return "♚";

        return " ";
    }

    abstract boolean moveTo( Piece[][] board, int startRow, int startCol, int endRow, int endCol);

}
