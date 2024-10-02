package Version2.src;

public class PGN {
    public static String generatePGN(int startRow, int startCol, int endRow, int endCol, char player, char target, char type){
        String sRow = "" + (char)(startRow+'1');
        String sCol = "" + (char)(startCol+'a');
        String eRow = "" + (char)(endRow+'1');
        String eCol = "" + (char)(endCol+'a');

        String startSquare =  sCol + sRow ;
        String endSquare = eCol + eRow;

        String ans = "";

        char comp = player == 'W'? 'B': 'W';

        if (type == 'P'){
            if (target == ' '){
              ans = endSquare;
            } else if (target == comp){
                ans = sCol + 'x' + endSquare;
            }
        } else {
            ans += type;
            if (target == ' '){
                ans += endSquare;
            } else if (target == comp){
                ans += 'x' + endSquare;
            }
        }

        if (!ans.isEmpty()) return ans;

        return startSquare + " to " + endSquare;
    }
}