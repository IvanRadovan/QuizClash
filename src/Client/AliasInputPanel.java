package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AliasInputPanel {
    private JFrame frame;

    public AliasInputPanel() {
        frame = new JFrame("Enter Alias");
        frame.setLayout(new BorderLayout());

        JLabel aliasLabel = new JLabel("Enter your alias:");
        aliasLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField aliasField = new JTextField();
        JButton submitButton = new JButton("Submit");

        JPanel aliasPanel = new JPanel();
        aliasPanel.setLayout(new GridLayout(3, 1));
        aliasPanel.add(aliasLabel);
        aliasPanel.add(aliasField);
        aliasPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alias = aliasField.getText();

                frame.dispose();
            }
        });

        frame.add(aliasPanel, BorderLayout.CENTER);

        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
