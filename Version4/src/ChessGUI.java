package Version4.src;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class ChessGUI {

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
                displayBoard[i][j].setFont(Main.PIECE_FONT);
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
        btnUndo.setFont(Main.CONTROL_FONT);
        btnUndo.addActionListener(e-> handleUndo());
        controlPanel.add(btnUndo);
        chkFlipBoard.setSelected(boardFlip);
        chkFlipBoard.setFont(Main.CONTROL_FONT);
        chkFlipBoard.addActionListener(e-> boardFlip = chkFlipBoard.isSelected());
        controlPanel.add(chkFlipBoard);
        btnReset.setFont(Main.CONTROL_FONT);
        btnReset.addActionListener(e-> handleReset());
        controlPanel.add(btnReset);

        player1Panel.setBorder(BorderFactory.createTitledBorder("White"));
        lblPlayer1Name.setFont(Main.PLAYER_FONT);
        lblPlayer1Name.setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
        player1Panel.add(lblPlayer1Name, "West");
        lblPlayer1Time.setBorder(BorderFactory.createEmptyBorder(0,0,0,120));
        lblPlayer1Time.setFont(Main.PLAYER_FONT);
        player1Panel.add(lblPlayer1Time,"East");

        player2Panel.setBorder(BorderFactory.createTitledBorder("Black"));
        lblPlayer2Name.setFont(Main.PLAYER_FONT);
        lblPlayer2Name.setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
        player2Panel.add(lblPlayer2Name, "West");
        lblPlayer2Time.setBorder(BorderFactory.createEmptyBorder(0,0,0, 120));
        lblPlayer2Time.setFont(Main.PLAYER_FONT);
        player2Panel.add(lblPlayer2Time, "East");

        lblEvaluation.setFont(Main.PLAYER_FONT);

        gameFrame.add(player1Panel, "South");
        gameFrame.add(player2Panel, "North");
        gameFrame.add(boardPanel, "Center");
        gameFrame.add(lblEvaluation, "West");
        gameFrame.add (controlPanel, "East");

        gameFrame.setSize(840,720);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        if (currentPlayer == 'W'){
            if (Main.player1Type == 'C') makeBotMove();
        }
    }


    public static void updateDisplayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                Piece A = actualBoard[i][j];

                if (A.COLOUR == 'W') displayBoard[i][j].setForeground(Main.LIGHT_PIECE);
                else if (A.COLOUR == 'B') displayBoard[i][j].setForeground(Main.DARK_PIECE);
                else if (A.COLOUR == ' ') displayBoard[i][j].setForeground(Main.MOVABLE_SQUARE);

                if ((i + j) % 2 == 1) displayBoard[i][j].setBackground(Main.LIGHT_SQUARE);
                else displayBoard[i][j].setBackground(Main.DARK_SQUARE);

                if (A.NAME == 'K' && A.COLOUR == currentPlayer) {
                    if (currentPlayer == 'W' && !A.IS_SAFE_FOR_WHITE) displayBoard[i][j].setBackground(Main.KING_UNDER_THREAT);
                    else if (currentPlayer == 'B' && !A.IS_SAFE_FOR_BLACK) displayBoard[i][j].setBackground(Main.KING_UNDER_THREAT);
                }
                displayBoard[i][j].setText(actualBoard[i][j].getName());

                lblEvaluation.setText("" + evaluation);
            }
        }
    }


    public static void displayPossibleMoves(){
        displayBoard[startRow][startCol].setBackground(Main.SELECTED_SQUARE);
        char comp = currentPlayer =='W'? 'B': 'W';
        for (int i=0; i< BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++){
                boolean moved = Functions.makeMove(actualBoard, startRow, startCol,i,j, currentPlayer, boardHistory);
                if (moved){
                    Functions.undo(actualBoard, boardHistory);
                    if (actualBoard[i][j].COLOUR == ' ') {
                        displayBoard[i][j].setText("○");
                    } else if (actualBoard[i][j].COLOUR == comp){
                        displayBoard[i][j].setBackground(Main.CAPTURABLE_SQUARE);
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

            if (currentPlayer == 'W'){
                if (Main.player1Type == 'C') makeBotMove();
            } else {
                if (Main.player2Type == 'C') makeBotMove();
            }

        } else {
            handleFirstClick(row, col);
        }
    }

    public static void makeBotMove(){
        Bot.makeMove(actualBoard, currentPlayer);

        if (currentPlayer == 'W' ) player1Time += Main.timeIncrement;
        else player2Time += Main.timeIncrement;

        currentPlayer = currentPlayer == 'W' ? 'B' : 'W';
        flipBoard();
        updateDisplayBoard();

        if (EndConditions.checkMate(actualBoard, currentPlayer, boardHistory)){
            String winner = currentPlayer =='W' ? "BLACK" : "WHITE";
            JOptionPane.showMessageDialog(gameFrame, winner +" WON THE GAME");
        } else if (EndConditions.staleMate()){
            JOptionPane.showMessageDialog(gameFrame, "GAME IS DRAW BY STALEMATE");
        }

    }

    public static void handleUndo(){
        if (boardHistory.size() <= 1) return;
        Functions.undo(actualBoard, boardHistory);
        currentPlayer = currentPlayer =='W'?'B':'W';
        if (currentPlayer == 'W' && Main.player1Type == 'C') {
            Functions.undo(actualBoard, boardHistory);
            currentPlayer = currentPlayer =='W'?'B':'W';
            flipBoard();
        }
        if (currentPlayer == 'B' && Main.player2Type == 'C') {
            Functions.undo(actualBoard, boardHistory);
            currentPlayer = currentPlayer =='W'?'B':'W';
            flipBoard();
        }
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
        if(Main.player1Type == 'C'){
            makeBotMove();
        }
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
                int col = j;
                if (currentPlayer == 'B') col = BOARD_SIZE-j-1;
                boardPanel.add(displayBoard[row][col]);
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