package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GameClient implements ActionListener {

    private JFrame frame = new JFrame();
    private JLabel instructionsLabel = new JLabel("Waiting for opponent to connect...");

    private JLabel questionLabel = new JLabel();
    private JPanel questionnairePanel = new JPanel();
    private String correctAnswer;
    private List<QButton> optionButtons = new ArrayList<>();

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;


    public GameClient() throws IOException {
        socket = new Socket("localhost", 9999);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        setQuestionnairePanel();
        setQuestionArea();
        setFrame();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        QButton clickedButton = (QButton) e.getSource();
        String selectedOption = clickedButton.getText();

        out.println(selectedOption);
        System.out.println("You clicked " + selectedOption); // For testing

        if (selectedOption.equalsIgnoreCase(correctAnswer)) {
            clickedButton.setBackground(Color.green);
        } else {
            System.out.println("From client: wrong");

        }

    }

    private void setQuestionnairePanel() {
        questionnairePanel.setLayout(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++) {
            QButton optionButton = new QButton();
            optionButton.addActionListener(this);
            optionButtons.add(optionButton);
            optionButton.setEnabled(false);
            questionnairePanel.add(optionButton);
        }
    }

    private void setQuestionArea() {
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        questionLabel.setText("Question goes here...");
    }

    private void setFrame() {
        frame.getContentPane().add(questionLabel, BorderLayout.NORTH);
        frame.getContentPane().add(questionnairePanel, BorderLayout.CENTER);
        frame.getContentPane().add(instructionsLabel, BorderLayout.SOUTH);

        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void play() throws IOException {
        String response;

        try {
            response = in.readLine();
            System.out.println(response);
            if (response.startsWith("WELCOME")) {
                instructionsLabel.setText(response);
                frame.setTitle("QuizClash: Player %S".formatted(response.substring(8)));
            }
            while (true) {
                correctAnswer = null;
                response = in.readLine();
                if (response == null) {
                    instructionsLabel.setText("Waiting for server");
                    continue;
                }
                if (correctAnswer == null) {
                    correctAnswer = response;
                }

                System.out.println("From client: " + correctAnswer);

                if (response.startsWith("QUESTION")) {
                    optionButtons.forEach(button -> button.setEnabled(true));
                    String[] questionData = response.substring(8).split("/");
                    String question = questionData[0];
                    String[] options = questionData[1].split(",");
                    instructionsLabel.setText("Choose an option");
                    questionLabel.setText(question);
                    IntStream.range(0, optionButtons.size()).forEach(i -> optionButtons.get(i).setText(options[i]));
                } else if (response.startsWith("MESSAGE")) {
                    instructionsLabel.setText(response.substring(8));
                } else if (response.startsWith("ROUND_SCORE")) {
                    instructionsLabel.setText(response.substring(12));
                } else if (response.startsWith("WAITING")) {
                    optionButtons.forEach(button -> button.setEnabled(false));
                    instructionsLabel.setText("Waiting for other player");
                } else if (response.startsWith("VICTORY")) {
                    instructionsLabel.setText(response);
                    break;
                } else if (response.startsWith("TIE")) {
                    instructionsLabel.setText(response);
                    break;
                } else if (response.startsWith("LOSE")) {
                    instructionsLabel.setText(response);
                    break;
                }
            }
        } finally {
            socket.close();
        }
    }


    class QButton extends JButton {
        public QButton() {
            setPreferredSize(new Dimension(200, 50));
        }
    }

    public static void main(String[] args) throws IOException {
        new GameClient().play();
    }

}
