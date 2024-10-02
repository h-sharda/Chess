package Version2.src;

import javax.swing.*;
import java.awt.*;

public class Trial {

    public static JLabel label = new JLabel("      ");
    public static JFrame frame = new JFrame("Simplified Chess Promotion");
    public static JButton button = new JButton("Click me for options");

    public static void main(String[] args) {

        System.out.println("Trying my best 😔😔😔");

        frame.setLayout(new BorderLayout());
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add action listener to the button
        button.addActionListener(e -> handleInput());

        // Add components to frame
        frame.add(button, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);

        // Display the frame
        frame.setVisible(true);
    }

    public static void handleInput() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};

        // Show dialog with options and return selected index
        int selectedOption = JOptionPane.showOptionDialog(
                frame,
                "Choose a piece for promotion:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0] // Default option
        );

        // Update label with selected option if a valid option is chosen
        if (selectedOption != -1) {
            label.setText("Promoted to: " + options[selectedOption]);
        }
    }
}
