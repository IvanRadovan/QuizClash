package Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionPanel {
    private JFrame frame = new JFrame("Quiz Question");
    private JTextArea questionArea = new JTextArea();
    private List<JButton> optionButtons;

    public QuestionPanel() {
        frame.setLayout(new BorderLayout());

        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        optionButtons = createOptionButtons();

        for (JButton button : optionButtons) {
            optionsPanel.add(button);
        }

        frame.add(questionArea, BorderLayout.NORTH);
        frame.add(optionsPanel, BorderLayout.CENTER);

        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void displayQuestion(Question currentQuestion) {
        questionArea.setText(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        for (int i = 0; i < optionButtons.size(); i++) {
            optionButtons.get(i).setText(options.get(i));
        }
    }


    private List<JButton> createOptionButtons() {


        return null;
    }


}