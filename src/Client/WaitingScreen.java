package Client;


import javax.swing.*;
import java.awt.*;

public class WaitingScreen {
    private JFrame frame;

    public WaitingScreen() {
        frame = new JFrame("Waiting for Connection");
        frame.setLayout(new BorderLayout());

        JLabel waitingLabel = new JLabel("Waiting for connection...");
        waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(waitingLabel, BorderLayout.CENTER);

        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void closeWindow() {
        frame.dispose();
    }
}