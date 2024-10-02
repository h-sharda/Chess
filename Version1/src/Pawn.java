package Version1.src;

class Pawn extends Piece{

    Pawn(char name, int points, char colour) {
        super(name, points, colour);
    }

    public static boolean enPassantFlag = false;
    public static boolean pawnPromotionFlag = false;

    @Override
    public boolean moveTo(Piece[][] board, int startRow, int startCol, int endRow, int endCol) {

        if ( startRow == endRow && startCol == endCol) return false;

        enPassantFlag = false;
        pawnPromotionFlag = false;

        char player = board[startRow][startCol].COLOUR;
        char comp = player == 'W' ? 'B' : 'W';
        char target = board[endRow][endCol].COLOUR;

        int direction = (player == 'W') ? 1 : -1;


        // PAWN PROMOTION IS HANDLED BY MOVE MAKER
        // AS PAWN MOVING TO THAT SQUARE IS JUST NORMAL MOVEMENT
        if ( direction ==1 && endRow == 7 || direction ==-1 && endRow ==0){
            pawnPromotionFlag = true;
        }

        // MOVE FORWARD, CAPTURE DIAGONALLY
        if (startRow + direction == endRow){
            if ( endCol == startCol && target == ' ') return true;
            else if (target == comp && Math.abs(endCol - startCol) == 1) return true;
        }

        // CAN MAKE 2 MOVES ON ITS 1st MOVE
        if (NO_OF_MOVES == 0) {
            if ( endRow == startRow+ direction+ direction && startCol == endCol ) {
                return (target == ' ' && board[endRow - direction][endCol].COLOUR == ' ');
            }
        }

        // EN PASSANT
        // CURRENTLY DOESN'T CHECK IF THE ENEMY PAWN MOVED IN LAST TURN OR NOT
        if (  (direction== 1 && startRow == 4) || (direction == -1 && startRow == 3)) {
            if ( Math.abs(endCol - startCol) == 1 && target == ' ' && startRow + direction == endRow){
                Piece AdjacentPiece = board[startRow][endCol];
                if (AdjacentPiece.COLOUR == comp && AdjacentPiece.NAME == 'P' && AdjacentPiece.NO_OF_MOVES == 1){
                    enPassantFlag = true;
                    return true;
                }
            }
        }

        return false;
    }
}