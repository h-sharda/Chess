package Version4.src;

class Pawn extends Piece{

    Pawn(char name, int value, char colour) {
        super(name, value, colour);
    }

    static boolean enPassantFlag = false;
    static boolean pawnPromotionFlag = false;

    @Override
    boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        enPassantFlag = false;
        pawnPromotionFlag = false;

        char currentPlayer = board[startRow][startCol].colour;
        char opponent = currentPlayer == 'W' ? 'B' : 'W';
        char target = board[endRow][endCol].colour;

        int direction = (currentPlayer == 'W') ? 1 : -1;

        // PAWN PROMOTION IS HANDLED BY THE GUI, NOT IN THIS FUNCTION
        if ( (direction ==1 && endRow == 7) || (direction ==-1 && endRow ==0) ){
            pawnPromotionFlag = true;
        }

        // MOVE FORWARD, CAPTURE DIAGONALLY
        if (startRow + direction == endRow){
            if ( endCol == startCol && target == ' ') return true;
            else if (target == opponent && Math.abs(endCol - startCol) == 1) return true;
        }

        // CAN MAKE 2 MOVES ON ITS 1st MOVE
        if (noOfMoves == 0) {
            if ( endRow == startRow + (2*direction) && startCol == endCol ) {
                return (target == ' ' && board[endRow - direction][endCol].colour == ' ');
            }
        }

        // EN PASSANT
        if (  (direction== 1 && startRow == 4) || (direction == -1 && startRow == 3) ){
            if ( Math.abs(endCol - startCol) == 1 && target == ' ' && startRow + direction == endRow){
                Piece AdjacentPiece = board[startRow][endCol];
                if (AdjacentPiece.name == 'P' && AdjacentPiece.colour == opponent && AdjacentPiece.noOfMoves == 1){
                    enPassantFlag = true;
                    return true;
                }
            }
        }

        return false;
    }
}