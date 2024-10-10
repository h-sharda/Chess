package Version3.src;

import javax.swing.*;
import java.awt.*;

public class MenuGUI {

    // FONTS AND COLORS CONSTANTS
    static final Color BACKGROUND_COLOR = new Color(220, 220, 220);
    static final Font DEFAULT_FONT = new Font("Arial Unicode MS", Font.PLAIN, 16);

    static final Color WELCOME_MESSAGE_COLOR = new Color(60, 60, 60);
    static final Font WELCOME_MESSAGE_FONT = new Font("Arial Unicode MS", Font.BOLD, 50);

    static final Color START_BUTTON_COLOR = new Color(200,200,255);
    static final Font START_BUTTON_FONT = new Font("Arial Unicode MS", Font.BOLD, 20);


    // MENU COMPONENTS
    public static JFrame menuFrame = new JFrame("Chess Simulator");
    public static JLabel lblWelcomeMessage = new JLabel("<html> Chess ♚<sub>♞</sub></html>");

    public static JLabel lblInputPlayer1Name = new JLabel("Enter Player 1 (white) Name: ");
    public static JTextField txtInputPlayer1Name = new JTextField(20);
    public static JComboBox<String> cmbPlayer1Type = new JComboBox<>(new String[]{"Human", "Computer"});

    public static JLabel lblInputPlayer2Name = new JLabel("Enter Player 2 (black) Name: ");
    public static JTextField txtInputPlayer2Name = new JTextField(20);
    public static JComboBox<String> cmbPlayer2Type = new JComboBox<>(new String[]{"Human", "Computer"});

    public static JLabel lblInputTime = new JLabel("Enter time in seconds: ");
    public static JTextField txtInputTime = new JTextField(20);

    public static JLabel lblInputTimeIncrement = new JLabel("Enter time increment in seconds: ");
    public static JTextField txtInputTimeIncrement = new JTextField(20);

    public static JLabel lblBoardFlip = new JLabel("Flip Board: ");
    public static JCheckBox chkBoardFlip = new JCheckBox();

    public static JButton btnStart = new JButton("START");


    // MAIN RUNNER FOR THE MENU
    public static void main(String[] args) {

        menuFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        menuFrame.setSize(800, 600);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // REDUCES THE TOOLTIP DELAY FOR FASTER RESPONSE
        ToolTipManager.sharedInstance().setInitialDelay(250);

        // ADDING WELCOME HEADER MESSAGE
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        lblWelcomeMessage.setForeground(WELCOME_MESSAGE_COLOR);
        lblWelcomeMessage.setFont(WELCOME_MESSAGE_FONT);
        menuFrame.add(lblWelcomeMessage, gbc);

        // ADDING INPUT COMPONENTS
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // PLAYER 1 INPUTS
        gbc.gridy = 1;

        gbc.gridx = 0;
        lblInputPlayer1Name.setFont(DEFAULT_FONT);
        menuFrame.add(lblInputPlayer1Name, gbc);

        gbc.gridx = 1;
        txtInputPlayer1Name.setText("Harshit");
        txtInputPlayer1Name.setFont(DEFAULT_FONT);
        txtInputPlayer1Name.setToolTipText("Name <= 20 characters");
        menuFrame.add(txtInputPlayer1Name, gbc);

        gbc.gridx = 2;
        cmbPlayer1Type.setFont(DEFAULT_FONT);
        cmbPlayer1Type.addActionListener(e-> handlePlayerType(1));
        menuFrame.add(cmbPlayer1Type, gbc);

        // PLAYER 2 INPUTS
        gbc.gridy = 2;

        gbc.gridx = 0;
        lblInputPlayer2Name.setFont(DEFAULT_FONT);
        menuFrame.add(lblInputPlayer2Name, gbc);

        gbc.gridx = 1;
        txtInputPlayer2Name.setText("Opponent");
        txtInputPlayer2Name.setFont(DEFAULT_FONT);
        txtInputPlayer2Name.setToolTipText("Name <= 20 characters");
        menuFrame.add(txtInputPlayer2Name, gbc);

        gbc.gridx = 2;
        cmbPlayer2Type.setFont(DEFAULT_FONT);
        cmbPlayer2Type.addActionListener(e-> handlePlayerType(2));
        menuFrame.add(cmbPlayer2Type, gbc);

        // TIME INPUTS
        gbc.gridy = 3;

        gbc.gridx = 0;
        lblInputTime.setFont(DEFAULT_FONT);
        menuFrame.add(lblInputTime, gbc);

        gbc.gridx = 1;
        txtInputTime.setText("600");
        txtInputTime.setFont(DEFAULT_FONT);
        txtInputTime.setToolTipText("Time should be a integer, 10 <= time <= 9999");
        menuFrame.add(txtInputTime, gbc);

        // TIME INCREMENT INPUTS
        gbc.gridy = 4;

        gbc.gridx = 0;
        lblInputTimeIncrement.setFont(DEFAULT_FONT);
        menuFrame.add(lblInputTimeIncrement, gbc);

        gbc.gridx = 1;
        txtInputTimeIncrement.setText("5");
        txtInputTimeIncrement.setFont(DEFAULT_FONT);
        txtInputTimeIncrement.setToolTipText("Increment should be a integer, 0 <= increment <= 99");
        menuFrame.add(txtInputTimeIncrement, gbc);

        // BOARD FLIP INPUTS
        gbc.gridy = 5;

        gbc.gridx = 0;
        lblBoardFlip.setFont(DEFAULT_FONT);
        menuFrame.add(lblBoardFlip, gbc);

        gbc.gridx = 1;
        chkBoardFlip.setFont(DEFAULT_FONT);
        chkBoardFlip.addActionListener(e-> handleBoardFlip());
        chkBoardFlip.setToolTipText("Flips the board after each turn in the current players direction");
        menuFrame.add(chkBoardFlip, gbc);

        // START GAME BUTTON
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        btnStart.setToolTipText("Click to start the game");
        btnStart.setBackground(START_BUTTON_COLOR);
        btnStart.setFont(START_BUTTON_FONT);
        btnStart.addActionListener(e -> handleStart());
        menuFrame.add(btnStart, gbc);

        menuFrame.setVisible(true);
    }


    // ACTION HANDLERS

    // HANDLES THE OPTION BOX FOR THE PLAYER TYPE
    public static void handlePlayerType(int i){
        if (i == 1){
            int option = cmbPlayer1Type.getSelectedIndex();
            Main.player1Type = option == 0 ? 'H' : 'C';
        } else if ( i == 2){
            int option = cmbPlayer2Type.getSelectedIndex();
            Main.player2Type = option == 0 ? 'H' : 'C';
        }
    }

    // HANDLES THE BOARD FLIP AFTER EACH MOVE
    public static void handleBoardFlip(){
        Main.boardFlip = chkBoardFlip.isSelected();
    }

    // HANDLES THE START BUTTON, IF INPUTS ARE VALID IT SWITCHES TO GAME_FRAME
    public static void handleStart(){
        try {
            String player1Name = txtInputPlayer1Name.getText();
            if (player1Name.length() > 20 ) throw new IllegalArgumentException();

            String player2Name = txtInputPlayer2Name.getText();
            if (player2Name.length() > 20 ) throw new IllegalArgumentException();

            Main.player1Name = player1Name;
            Main.player2Name = player2Name;

            int time = Integer.parseInt(txtInputTime.getText());
            if (time < 10 || time > 9999) throw new IllegalArgumentException();

            int increment = Integer.parseInt(txtInputTimeIncrement.getText());
            if (increment < 0 || increment > 99) throw new IllegalArgumentException();

            Main.time = time;
            Main.timeIncrement = increment;

            menuFrame.setVisible(false);
            String[] args = {""};
            ChessGUI.main(args);

        } catch (Exception e) {
            String exceptionMessage = """
                    Enter Valid details
                    Player names < 30 chars
                    Time is in seconds it should be a integer (10 <= time <= 9999)
                    Increment is in seconds it should be a integer (0 <= increment <= 99)""";
            JOptionPane.showMessageDialog(menuFrame, exceptionMessage);
        }
    }

}
