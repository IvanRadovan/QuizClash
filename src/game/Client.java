package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements ActionListener {

    private JFrame frame = new JFrame("QuizClash");
    private JTextArea questionArea = new JTextArea("Question goes here...");
    private JLabel messageLabel = new JLabel("Game info goes here...");
    private JPanel questionnairePanel = new JPanel();

    private QButton optionOne = new QButton();
    private QButton optionTwo = new QButton();
    private QButton optionThree = new QButton();
    private QButton optionFour = new QButton();

    private static int PORT = 9999;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client() throws Exception {
//        socket = new Socket("localhost", PORT);
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);

        setQuestionnairePanel();
        setQuestionArea();
        setFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void setQuestionnairePanel() {
        questionnairePanel.setLayout(new GridLayout(2, 2, 2, 2));
        questionnairePanel.add(optionOne);
        questionnairePanel.add(optionTwo);
        questionnairePanel.add(optionThree);
        questionnairePanel.add(optionFour);

        optionOne.addActionListener(this);
        optionTwo.addActionListener(this);
        optionThree.addActionListener(this);
        optionFour.addActionListener(this);
    }

    private void setQuestionArea() {
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
    }

    private void setFrame() {
        frame.getContentPane().add(questionArea, "North");
        frame.getContentPane().add(questionnairePanel, "Center");
        frame.getContentPane().add(messageLabel, "South");
        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class QButton extends JButton {
        private JButton button = new JButton();
        private static int option = 1;

        public QButton(String question) {
            setText(question);
            option++;
        }

        public QButton() {
            this("OPTION %s".formatted(option));
        }

        public void setQuestion(String question) {
            button.setText(question);
        }

        public String getQuestion() {
            return button.getText();
        }
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }

}