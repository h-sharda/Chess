package Version3.src;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class ChessGUI {

    // COLOUR AND FONT CONSTANTS
    static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    static final Color DARK_SQUARE = new Color(181, 136, 99);
    static final Color LIGHT_PIECE = new Color(255,255,255);
    static final Color DARK_PIECE = new Color(0,0,0);
    static final Color SELECTED_SQUARE = new Color(200,200,255);
    static final Color CAPTURABLE_SQUARE = new Color(255, 127, 127);
    static final Color MOVABLE_SQUARE = new Color(64,64,64);
    static final Color KING_UNDER_THREAT = new Color(255,0,0);

    static final Font PIECE_FONT = new Font("Arial Unicode MS", Font.BOLD,40);
    static final Font PLAYER_FONT = new Font("Arial Unicode MS", Font.PLAIN, 20);
    static final Font CONTROL_FONT = new Font("Arial Unicode MS", Font.BOLD, 15);

    static final String CLOCK_EMOJI = "\uD83D\uDD53 ";


    // BOARD INITIALIZATION
    static final int BOARD_SIZE = 8;
    public static Piece[][] actualBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
    public static Stack<Piece[][]> boardHistory = new Stack<>();


    // VARIABLES USED FOR MAKING MOVES
    static char currentPlayer = 'W';
    static int player1Time = Main.time, player2Time = Main.time;
    static int startRow, startCol, endRow, endCol;
    static int evaluation = 0;
    static boolean boardFlip = Main.boardFlip;
    static boolean isMoveReal = false;
    static boolean clickSelector = true; // true for first click, false for second click


    // GAME GUI COMPONENTS
    public static JFrame gameFrame =  new JFrame("CHESS");

    public static JPanel boardPanel = new JPanel( new GridLayout(BOARD_SIZE, BOARD_SIZE));
    public static JButton[][] displayBoard = new JButton[BOARD_SIZE][BOARD_SIZE];

    public static JPanel controlPanel = new JPanel(new FlowLayout());
    public static JButton btnUndo = new JButton("UNDO");
    public static JButton btnReset = new JButton("RESET");
    public static JCheckBox chkFlipBoard = new JCheckBox("FLIP");
    public static JButton lblEvaluation = new JButton( ""+ 0);

    public static JPanel player1Panel = new JPanel(new BorderLayout());
    public static JLabel lblPlayer1Name = new JLabel(Main.player1Name);
    public static JLabel lblPlayer1Time = new JLabel(CLOCK_EMOJI + player1Time /60 +":" + String.format("%02d", player1Time % 60));
    public static Timer player1Timer = new Timer(1000, e-> handleTime());

    public static JPanel player2Panel = new JPanel(new BorderLayout());
    public static JLabel lblPlayer2Name = new JLabel(Main.player2Name);
    public static JLabel lblPlayer2Time = new JLabel(CLOCK_EMOJI + player2Time /60 +":" + String.format("%02d", player2Time % 60));
    public static Timer player2Timer = new Timer(1000, e-> handleTime());


    // MAIN RUNNER PROGRAM OF THE GAME
    public static void main(String[] args) {

        Functions.initializeBoard(actualBoard, boardHistory);

        for (int i =BOARD_SIZE-1; i>= 0; i--){
            for(int j =0; j< BOARD_SIZE; j++){
                displayBoard[i][j] = new JButton(actualBoard[i][j].getName());
                displayBoard[i][j].setFont(PIECE_FONT);
                boardPanel.add(displayBoard[i][j]);
                int row = i, col = j;
                displayBoard[i][j].addActionListener(e -> {
                    if (clickSelector) {
                        handleFirstClick(row, col);
                    } else {
                        handleSecondClick(row, col);
                    }
                });
            }
        }

        handleTime();
        updateDisplayBoard();

        gameFrame.setLayout(new BorderLayout());

        controlPanel.setLayout(new GridLayout(3, 1));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        btnUndo.setFont(CONTROL_FONT);
        btnUndo.addActionListener(e-> handleUndo());
        controlPanel.add(btnUndo);
        chkFlipBoard.setSelected(boardFlip);
        chkFlipBoard.setFont(CONTROL_FONT);
        chkFlipBoard.addActionListener(e-> boardFlip = chkFlipBoard.isSelected());
        controlPanel.add(chkFlipBoard);
        btnReset.setFont(CONTROL_FONT);
        btnReset.addActionListener(e-> handleReset());
        controlPanel.add(btnReset);

        player1Panel.setBorder(BorderFactory.createTitledBorder("White"));
        lblPlayer1Name.setFont(PLAYER_FONT);
        lblPlayer1Name.setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
        player1Panel.add(lblPlayer1Name, "West");
        lblPlayer1Time.setBorder(BorderFactory.createEmptyBorder(0,0,0,120));
        lblPlayer1Time.setFont(PLAYER_FONT);
        player1Panel.add(lblPlayer1Time,"East");

        player2Panel.setBorder(BorderFactory.createTitledBorder("Black"));
        lblPlayer2Name.setFont(PLAYER_FONT);
        lblPlayer2Name.setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
        player2Panel.add(lblPlayer2Name, "West");
        lblPlayer2Time.setBorder(BorderFactory.createEmptyBorder(0,0,0, 120));
        lblPlayer2Time.setFont(PLAYER_FONT);
        player2Panel.add(lblPlayer2Time, "East");

        lblEvaluation.setFont(PLAYER_FONT);

        gameFrame.add(player1Panel, "South");
        gameFrame.add(player2Panel, "North");
        gameFrame.add(boardPanel, "Center");
        gameFrame.add(lblEvaluation, "West");
        gameFrame.add (controlPanel, "East");

        gameFrame.setSize(840,720);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }


    public static void updateDisplayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                Piece A = actualBoard[i][j];

                if (A.COLOUR == 'W') displayBoard[i][j].setForeground(LIGHT_PIECE);
                else if (A.COLOUR == 'B') displayBoard[i][j].setForeground(DARK_PIECE);
                else if (A.COLOUR == ' ') displayBoard[i][j].setForeground(MOVABLE_SQUARE);

                if ((i + j) % 2 == 1) displayBoard[i][j].setBackground(LIGHT_SQUARE);
                else displayBoard[i][j].setBackground(DARK_SQUARE);

                if (A.NAME == 'K' && A.COLOUR == currentPlayer) {
                    if (currentPlayer == 'W' && !A.IS_SAFE_FOR_WHITE) displayBoard[i][j].setBackground(KING_UNDER_THREAT);
                    else if (currentPlayer == 'B' && !A.IS_SAFE_FOR_BLACK) displayBoard[i][j].setBackground(KING_UNDER_THREAT);
                }
                displayBoard[i][j].setText(actualBoard[i][j].getName());

                lblEvaluation.setText("" + evaluation);
            }
        }
    }


    public static void displayPossibleMoves(){
        displayBoard[startRow][startCol].setBackground(SELECTED_SQUARE);
        char comp = currentPlayer =='W'? 'B': 'W';
        for (int i=0; i< BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++){
                boolean moved = Functions.makeMove(actualBoard, startRow, startCol,i,j, currentPlayer, boardHistory);
                if (moved){
                    Functions.undo(actualBoard, boardHistory);
                    if (actualBoard[i][j].COLOUR == ' ') {
                        displayBoard[i][j].setText("○");
                    } else if (actualBoard[i][j].COLOUR == comp){
                        displayBoard[i][j].setBackground(CAPTURABLE_SQUARE);
                    }
                }
            }
        }
    }


    public static void handleFirstClick(int row, int col){
        if (!clickSelector) return;

        startRow = row;
        startCol = col;

        if(actualBoard[startRow][startCol].COLOUR == currentPlayer) displayPossibleMoves();

        clickSelector = false;
    }


    public static void handleSecondClick(int row, int col){
        if (clickSelector) return;

        endRow = row;
        endCol = col;

        isMoveReal = true;
        boolean moved = Functions.makeMove(actualBoard, startRow, startCol, endRow, endCol, currentPlayer, boardHistory);
        isMoveReal = false;

        evaluation = Functions.calculateEvaluation(actualBoard);
        updateDisplayBoard();

        clickSelector = true;

        if (moved) {
            if (currentPlayer == 'W' ) player1Time += Main.timeIncrement;
            else player2Time += Main.timeIncrement;

            currentPlayer = currentPlayer == 'W' ? 'B' : 'W';
            flipBoard();
            updateDisplayBoard();

            if (Pawn.pawnPromotionFlag) handlePromotion();


            if (EndConditions.checkMate(actualBoard, currentPlayer, boardHistory)){
                String winner = currentPlayer =='W' ? "BLACK" : "WHITE";
                JOptionPane.showMessageDialog(gameFrame, winner +" WON THE GAME");
            } else if (EndConditions.staleMate()){
                JOptionPane.showMessageDialog(gameFrame, "GAME IS DRAW BY STALEMATE");
            }

        } else {
            handleFirstClick(row, col);
        }
    }


    public static void handleUndo(){
        if (boardHistory.size() <= 1) return;
        Functions.undo(actualBoard, boardHistory);
        currentPlayer = currentPlayer =='W'?'B':'W';
        clickSelector = true;
        evaluation = Functions.calculateEvaluation(actualBoard);
        flipBoard();
        updateDisplayBoard();

    }


    public static void handleReset(){
        Functions.reset(actualBoard, boardHistory);
        currentPlayer = 'W';
        clickSelector = true;
        evaluation = 0;
        player1Time = Main.time;
        player2Time = Main.time;
        flipBoard();
        updateDisplayBoard();
    }


    public static int handlePromotion() {
        String[] options = {"Queen", "Knight", "Rook", "Bishop"};

        return JOptionPane.showOptionDialog(
                gameFrame,
            "Choose a piece for promotion:",
            "Pawn Promotion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
    }


    public static void handleTime(){

        lblPlayer1Time.setText(CLOCK_EMOJI + player1Time /60 +":" + String.format("%02d", player1Time % 60));
        lblPlayer2Time.setText(CLOCK_EMOJI + player2Time /60 +":" + String.format("%02d", player2Time % 60));

        if (currentPlayer == 'W'){
            player2Timer.stop();
            player1Timer.start();
            player1Time--;
            if (player1Time <= 0) {
                player2Timer.stop();
                String winner = currentPlayer =='W'?"BLACK":"WHITE";
                JOptionPane.showMessageDialog(gameFrame, winner + " WON BY TIMEOUT");
            }
        } else if (currentPlayer == 'B'){
            player1Timer.stop();
            player2Timer.start();
            player2Time--;
            if (player2Time <= 0) {
                player2Timer.stop();
                String winner = currentPlayer =='W'?"BLACK":"WHITE";
                JOptionPane.showMessageDialog(gameFrame, winner + " WON BY TIMEOUT");
            }
        }
    }

    public static void flipBoard(){

        if (!boardFlip) return;

        boardPanel.removeAll();
        for (int i = 0; i < BOARD_SIZE; i++) {
            int row = i;
            if (currentPlayer == 'W') row = BOARD_SIZE-i-1;
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardPanel.add(displayBoard[row][j]);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();

        gameFrame.remove(player1Panel);
        gameFrame.remove(player2Panel);

        if (currentPlayer == 'W'){
            gameFrame.add(player1Panel, "South");
            gameFrame.add(player2Panel, "North");
        } else {
            gameFrame.add(player1Panel, "North");
            gameFrame.add(player2Panel, "South");
        }
        gameFrame.revalidate();
        gameFrame.repaint();
    }

}
