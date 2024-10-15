import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trial extends JFrame {
    List<String> fontNames;
    int currentPage = 0;
    int fontsPerPage = 10;
    JPanel fontPanel;
    JLabel pageLabel;

    public Trial() {
        setTitle("Font Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        fontNames = new ArrayList<>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));

        fontPanel = new JPanel();
        fontPanel.setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane(fontPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        pageLabel = new JLabel();

        prevButton.addActionListener(this::previousPage);
        nextButton.addActionListener(this::nextPage);

        navigationPanel.add(prevButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextButton);

        add(navigationPanel, BorderLayout.SOUTH);

        displayFonts();
    }

    private void displayFonts() {
        fontPanel.removeAll();
        int start = currentPage * fontsPerPage;
        int end = Math.min(start + fontsPerPage, fontNames.size());

        for (int i = start; i < end; i++) {
            String fontName = fontNames.get(i);
            JLabel label = new JLabel(fontName + ": ♔♕♖♗♘♙♚♛♜♝♞♟"+ " \uD83D\uDD53");
            label.setFont(new Font(fontName, Font.PLAIN, 16));
            fontPanel.add(label);
        }

        pageLabel.setText("Page " + (currentPage + 1) + " of " + ((fontNames.size() - 1) / fontsPerPage + 1));

        fontPanel.revalidate();
        fontPanel.repaint();
    }

    private void previousPage(ActionEvent e) {
        if (currentPage > 0) {
            currentPage--;
            displayFonts();
        }
    }

    private void nextPage(ActionEvent e) {
        if ((currentPage + 1) * fontsPerPage < fontNames.size()) {
            currentPage++;
            displayFonts();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Trial app = new Trial();
            app.setVisible(true);
        });
    }
}

/*
UNICODE supporting Minimalist fonts:

DialogInput
Monospaced
Serif
Segoe UI Symbol

Emoji Font:
Dialog
DialogInput
Monospaced
SansSerif
Segoe UI Symbol
Serif

MS UI Gothic
MS PGothic + all above


 */