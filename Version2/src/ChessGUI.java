package Version2.src;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class ChessGUI {

    static final int BOARD_SIZE = 8;

    // COLOUR AND FONT CONSTANTS
    static final Color lightSquare = new Color(240, 217, 181);
    static final Color darkSquare = new Color(181, 136, 99);
    static final Color lightPiece = Color.WHITE;
    static final Color darkPiece = Color.BLACK;
    static final Color selectedSquare = new Color(200,200,255);
    static final Color capturableSquare = new Color(255, 127, 127);
    static final Color moveableSquare = Color.DARK_GRAY;
    static final Color kingUnderThreat = Color.RED;
    static final Font globalFont = new Font("Arial Unicode MS", Font.BOLD,40);

    // BOARD COMPONENTS
    public static Stack<Piece[][]> BOARD_HISTORY = new Stack<>();
    public static JFrame FRAME =  new JFrame("CHESS");

    public static JPanel BOARD_PANEL = new JPanel( new GridLayout(BOARD_SIZE, BOARD_SIZE));
    public static JButton[][] DISPLAY_BOARD = new JButton[BOARD_SIZE][BOARD_SIZE];
    public static Piece[][] ACTUAL_BOARD = new Piece[BOARD_SIZE][BOARD_SIZE];

    public static JPanel CONTROL_PANEL = new JPanel(new FlowLayout());
    public static JButton UNDO = new JButton("UNDO");
    public static JButton RESET = new JButton("RESET");

    // VARIABLES USED FOR MAKING MOVES
    static char CURRENT_PLAYER = 'W';
    static int START_ROW, START_COL, END_ROW, END_COL;
    static boolean IS_REAL_MOVE = false, MOVE_SELECTOR = true;
    // (true selects the starting square, false selects the ending square)

    public static void main(String[] args) {

        Functions.initializeBoard(ACTUAL_BOARD, BOARD_HISTORY);

        for (int i =BOARD_SIZE-1; i>= 0; i--){
            for(int j =0; j< BOARD_SIZE; j++){
                DISPLAY_BOARD[i][j] = new JButton(ACTUAL_BOARD[i][j].getName());
                DISPLAY_BOARD[i][j].setFont(globalFont);
                BOARD_PANEL.add(DISPLAY_BOARD[i][j]);
                int row = i, col = j;
                DISPLAY_BOARD[i][j].addActionListener(e -> {
                    if (MOVE_SELECTOR) {
                        handleFirstClick(row, col);
                    } else {
                        handleSecondClick(row, col);
                    }
                });
            }
        }
        UNDO.addActionListener(e-> handleUndo());
        RESET.addActionListener(e-> handleReset());

        updateDisplayBoard();

        FRAME.setLayout(new BorderLayout());

        CONTROL_PANEL.setLayout(new FlowLayout());
        CONTROL_PANEL.add(UNDO);
        CONTROL_PANEL.add(RESET);

        FRAME.add(CONTROL_PANEL, "North");
        FRAME.add(BOARD_PANEL, "Center");

        FRAME.setSize(640,640);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setVisible(true);
    }

    public static void updateDisplayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                Piece A = ACTUAL_BOARD[i][j];

                if (A.COLOUR == 'W') DISPLAY_BOARD[i][j].setForeground(lightPiece);
                else if (A.COLOUR == 'B') DISPLAY_BOARD[i][j].setForeground(darkPiece);
                else if (A.COLOUR == ' ') DISPLAY_BOARD[i][j].setForeground(moveableSquare);

                if ((i + j) % 2 == 1) DISPLAY_BOARD[i][j].setBackground(lightSquare);
                else DISPLAY_BOARD[i][j].setBackground(darkSquare);

                if (A.NAME == 'K' && A.COLOUR == CURRENT_PLAYER) {
                    if (CURRENT_PLAYER == 'W' && !A.IS_SAFE_FOR_WHITE) DISPLAY_BOARD[i][j].setBackground(kingUnderThreat);
                    else if (CURRENT_PLAYER == 'B' && !A.IS_SAFE_FOR_BLACK) DISPLAY_BOARD[i][j].setBackground(kingUnderThreat);
                }
                DISPLAY_BOARD[i][j].setText(ACTUAL_BOARD[i][j].getName());
            }
        }
    }

    public static void displayPossibleMoves(){
        DISPLAY_BOARD[START_ROW][START_COL].setBackground(selectedSquare);
        char comp = CURRENT_PLAYER=='W'? 'B': 'W';
        for (int i=0; i< BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++){
                boolean moved = Functions.makeMove(ACTUAL_BOARD,START_ROW,START_COL,i,j,CURRENT_PLAYER,BOARD_HISTORY);
                if (moved){
                    Functions.undo(ACTUAL_BOARD, BOARD_HISTORY);
                    if (ACTUAL_BOARD[i][j].COLOUR == ' ') {
                        DISPLAY_BOARD[i][j].setText("o");
                    } else if (ACTUAL_BOARD[i][j].COLOUR == comp){
                        DISPLAY_BOARD[i][j].setBackground(capturableSquare);
                    }
                }
            }
        }
    }

    public static void handleFirstClick(int row, int col){
        if (!MOVE_SELECTOR) return;

        START_ROW = row;
        START_COL = col;

        if(ACTUAL_BOARD[START_ROW][START_COL].COLOUR == CURRENT_PLAYER ) displayPossibleMoves();

        MOVE_SELECTOR = false;
    }

    public static void handleSecondClick(int row, int col){
        if (MOVE_SELECTOR) return;

        END_ROW = row;
        END_COL = col;

        IS_REAL_MOVE = true;
        boolean moved = Functions.makeMove(ACTUAL_BOARD, START_ROW, START_COL, END_ROW, END_COL, CURRENT_PLAYER, BOARD_HISTORY);
        IS_REAL_MOVE = false;

        updateDisplayBoard();

        MOVE_SELECTOR = true;
        if (moved) {
            CURRENT_PLAYER = CURRENT_PLAYER == 'W' ? 'B' : 'W';
            updateDisplayBoard();

            if (Pawn.pawnPromotionFlag) handlePromotion();

            if (EndConditions.checkMate(ACTUAL_BOARD, CURRENT_PLAYER, BOARD_HISTORY)){
                char winner = CURRENT_PLAYER=='W' ? 'B' : 'W';
                JOptionPane.showMessageDialog(FRAME, winner +" WON THE GAME");
            } else if (EndConditions.staleMate()){
                JOptionPane.showMessageDialog(FRAME, "GAME IS DRAW");
            }

        } else {
            handleFirstClick(row, col);
        }
    }

    public static void handleUndo(){
        if (BOARD_HISTORY.size() <= 1) return;
        Functions.undo(ACTUAL_BOARD, BOARD_HISTORY);
        CURRENT_PLAYER = CURRENT_PLAYER=='W'?'B':'W';
        MOVE_SELECTOR = true;
        updateDisplayBoard();
    }

    public static void handleReset(){
        Functions.reset(ACTUAL_BOARD, BOARD_HISTORY);
        CURRENT_PLAYER = 'W';
        MOVE_SELECTOR = true;
        updateDisplayBoard();
    }

    public static int handlePromotion() {
        String[] options = {"Queen", "Knight", "Rook", "Bishop"};

        return JOptionPane.showOptionDialog(
            FRAME,
            "Choose a piece for promotion:",
            "Pawn Promotion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
    }

}
