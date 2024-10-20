package Version4.src;

import javax.swing.*;
import java.awt.*;

class MenuGUI {

    // MENU COMPONENTS
    static JFrame menuFrame = new JFrame("Chess Simulator");
    static JLabel lblWelcomeMessage = new JLabel("<html> <b>Chess</b> ♚<sub>♞</sub></html>");

    static JLabel lblInputPlayer1Name = new JLabel("Enter Player 1 (white) Name: ");
    static JTextField txtInputPlayer1Name = new JTextField(10);
    static JComboBox<String> cmbPlayer1Type = new JComboBox<>(new String[]{"Human", "Computer"});

    static JLabel lblInputPlayer2Name = new JLabel("Enter Player 2 (black) Name: ");
    static JTextField txtInputPlayer2Name = new JTextField(10);
    static JComboBox<String> cmbPlayer2Type = new JComboBox<>(new String[]{"Human", "Computer"});

    static JLabel lblInputTime = new JLabel("Enter time in seconds: ");
    static JTextField txtInputTime = new JTextField(10);
    static JLabel lblInputTimeIncrement = new JLabel("Enter time increment in seconds: ");
    static JTextField txtInputTimeIncrement = new JTextField(10);

    static JLabel lblThrowableDialog = new JLabel();
    static JButton btnHelp = new JButton("HELP");
    static JCheckBox chkBoardFlip = new JCheckBox("Flip Board: ");
    static JButton btnStart = new JButton("START");


    // MAIN RUNNER FOR THE MENU
    public static void main(String[] args) {

        menuFrame.getContentPane().setBackground(Main.MAIN_MENU_BACKGROUND_COLOR);
        menuFrame.setSize(800, 550);
        menuFrame.setMinimumSize(new Dimension(800,550));
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new GridBagLayout());
        lblThrowableDialog.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // REDUCES THE TOOLTIP DELAY FOR FASTER RESPONSE
        ToolTipManager.sharedInstance().setInitialDelay(250);

        // ADDING WELCOME HEADER MESSAGE
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        lblWelcomeMessage.setForeground(Main.WELCOME_MESSAGE_COLOR);
        lblWelcomeMessage.setFont(Main.WELCOME_MESSAGE_FONT);
        menuFrame.add(lblWelcomeMessage, gbc);

        // ADDING INPUT COMPONENTS
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // PLAYER 1 INPUTS
        gbc.gridy = 1;
        gbc.gridx = 0;
        lblInputPlayer1Name.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblInputPlayer1Name, gbc);

        gbc.gridx = 1;
        txtInputPlayer1Name.setText(Main.player1Name);
        txtInputPlayer1Name.setFont(Main.DEFAULT_FONT);
        txtInputPlayer1Name.setToolTipText("Name <= 20 characters");
        menuFrame.add(txtInputPlayer1Name, gbc);

        gbc.gridx = 2;
        cmbPlayer1Type.setFont(Main.DEFAULT_FONT);
        cmbPlayer1Type.addActionListener(e-> handlePlayerType(1));
        menuFrame.add(cmbPlayer1Type, gbc);

        // PLAYER 2 INPUTS
        gbc.gridy = 2;
        gbc.gridx = 0;
        lblInputPlayer2Name.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblInputPlayer2Name, gbc);

        gbc.gridx = 1;
        txtInputPlayer2Name.setText(Main.player2Name);
        txtInputPlayer2Name.setFont(Main.DEFAULT_FONT);
        txtInputPlayer2Name.setToolTipText("Name <= 20 characters");
        menuFrame.add(txtInputPlayer2Name, gbc);

        gbc.gridx = 2;
        cmbPlayer2Type.setFont(Main.DEFAULT_FONT);
        cmbPlayer2Type.addActionListener(e-> handlePlayerType(2));
        menuFrame.add(cmbPlayer2Type, gbc);

        // TIME INPUTS
        gbc.gridy = 3;
        gbc.gridx = 0;
        lblInputTime.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblInputTime, gbc);

        gbc.gridx = 1;
        txtInputTime.setText("" + Main.time);
        txtInputTime.setFont(Main.DEFAULT_FONT);
        txtInputTime.setToolTipText("Time should be a integer, 10 <= time <= 9999");
        menuFrame.add(txtInputTime, gbc);

        // TIME INCREMENT INPUTS
        gbc.gridy = 4;
        gbc.gridx = 0;
        lblInputTimeIncrement.setFont(Main.DEFAULT_FONT);
        menuFrame.add(lblInputTimeIncrement, gbc);

        gbc.gridx = 1;
        txtInputTimeIncrement.setText("" + Main.timeIncrement);
        txtInputTimeIncrement.setFont(Main.DEFAULT_FONT);
        txtInputTimeIncrement.setToolTipText("Increment should be a integer, 0 <= increment <= 99");
        menuFrame.add(txtInputTimeIncrement, gbc);

        // HELP BUTTON
        gbc.gridy = 5;
        gbc.gridx = 0;
        btnHelp.setBackground(Main.BUTTON_COLOR);
        btnHelp.setFont(Main.BUTTON_FONT);
        btnHelp.addActionListener(e-> handleHelp());
        btnHelp.setToolTipText("Click to display Help dialog");
        menuFrame.add(btnHelp, gbc);

        // BOARD FLIP INPUT
        gbc.gridx = 1;
        chkBoardFlip.setFont(Main.DEFAULT_FONT);
        chkBoardFlip.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        chkBoardFlip.addActionListener(e -> handleBoardFlip());
        chkBoardFlip.setToolTipText("Flips the board after each turn in the current player's direction");
        menuFrame.add(chkBoardFlip, gbc);

        // START GAME BUTTON
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnStart.setBackground(Main.BUTTON_COLOR);
        btnStart.setFont(Main.BUTTON_FONT);
        btnStart.addActionListener(e -> handleStart());
        btnStart.setToolTipText("Click to start the game");
        menuFrame.add(btnStart, gbc);

        menuFrame.setVisible(true);

    }


    // ACTION LISTENERS

    // HANDLES THE OPTION BOX FOR THE PLAYER TYPE
    static void handlePlayerType(int i){
        if (i == 1){
            int option = cmbPlayer1Type.getSelectedIndex();
            Main.player1Type = option == 0 ? 'H' : 'C';
        } else if ( i == 2){
            int option = cmbPlayer2Type.getSelectedIndex();
            Main.player2Type = option == 0 ? 'H' : 'C';
        }
    }

    // HANDLES THE BOARD FLIP AFTER EACH MOVE
    static void handleBoardFlip(){
        Main.boardFlip = chkBoardFlip.isSelected();
    }

    static void handleHelp(){
        String helpMessage = "<html><h2>Help</h2>" +
                "<p>Welcome to the Chess Game!</p><br>" +
                "<b>Game Rules:</b> This game follows the standard chess rules<br><br>" +
                "<b>Flip board:</b> It allows you to flip the board after each turn to orient towards the current Player<br>" +
                "When playing with bot, turn this off after board is in your orientation for smooth gameplay<br><br>"+
                "<b>Input Rules:</b><br>" +
                "<ul>" +
                "<li><b>Player Names:</b> Each name should be 20 characters or less.</li>" +
                "<li><b>Time:</b> It is in seconds, it must be an integer (10 &lt= time &lt= 9999).</li>" +
                "<li><b>Increment:</b> It is in seconds, it must be a integer (0 &lt= increment &lt= 99)</li>" +
                "</ul>" +
                "<p>If any input is invalid, you'll be prompted to correct it. Enjoy the game!</p></html>";
        lblThrowableDialog.setText(helpMessage);
        JOptionPane.showMessageDialog(menuFrame, lblThrowableDialog, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    // HANDLES THE START BUTTON, IF ALL THE INPUTS ARE VALID IT SWITCHES TO GAME_FRAME
    static void handleStart(){
        try {
            if(Main.player2Type == 'C' && Main.player1Type == 'C') throw new IllegalArgumentException("Both players cant be bots");

            String player1Name = txtInputPlayer1Name.getText();
            if (player1Name.length() > 20 ) throw new IllegalArgumentException("Player 1 Name too long, name <= 20");

            String player2Name = txtInputPlayer2Name.getText();
            if (player2Name.length() > 20 ) throw new IllegalArgumentException("Player 2 Name too long, name <= 20");

            Main.player1Name = player1Name;
            Main.player2Name = player2Name;

            int time = Integer.parseInt(txtInputTime.getText());
            if (time < 10 || time > 9999) throw new IllegalArgumentException("Invalid time, 10 <= time <= 9999");

            int increment = Integer.parseInt(txtInputTimeIncrement.getText());
            if (increment < 0 || increment > 99) throw new IllegalArgumentException("Invalid time increment, 0 <= increment <= 99");

            Main.time = time;
            Main.timeIncrement = increment;

            menuFrame.setVisible(false);
            ChessGUI.main(new String[] {""});

        } catch (NumberFormatException e){
            String exceptionMessage = "Time and Time Increment must be integer values";
            lblThrowableDialog.setText(exceptionMessage);
            JOptionPane.showMessageDialog(menuFrame, lblThrowableDialog, "Invalid Input", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException e) {
            lblThrowableDialog.setText(e.getMessage());
            JOptionPane.showMessageDialog(menuFrame, lblThrowableDialog, "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

}
