package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class GameClient implements ActionListener {

    private JFrame frame = new JFrame();
    private JLabel instructionsLabel = new JLabel("Waiting for opponent to connect...");

    private JPanel questionnairePanel = new JPanel();
    private JLabel questionLabel = new JLabel();
    private List<QButton> optionButtons = new ArrayList<>();

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public GameClient() throws IOException {
        socket = new Socket("localhost", 9999);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        setQuestionnairePanel();
        setQuestionLabel();
        setFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        QButton clickedButton = (QButton) e.getSource();
        String selectedOption = clickedButton.getText();
        out.println(selectedOption);
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

    private void setQuestionLabel() {
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        questionLabel.setPreferredSize(new Dimension(700, 75));
        questionLabel.setText("Question goes here...");
    }

    private void setFrame() {
        frame.getContentPane().add(questionLabel, BorderLayout.NORTH);
        frame.getContentPane().add(questionnairePanel, BorderLayout.CENTER);
        frame.getContentPane().add(instructionsLabel, BorderLayout.SOUTH);

        frame.setSize(700, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void play() throws IOException {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                instructionsLabel.setText(response);
                frame.setTitle("QuizClash: Player %S".formatted(response.substring(8)));
            }

            while (true) {
                response = in.readLine();
                if (response == null) {
                    instructionsLabel.setText("Waiting for server");
                    continue;
                }

                if (response.startsWith("QUESTION")) {
                    optionButtons.forEach(button -> button.setEnabled(true));
                    String category = response.substring(9, response.indexOf("#"));
                    String[] questionData = response.substring(response.indexOf("#") + 1).split("/");
                    String question = questionData[0];

                    String[] options = questionData[1].split(",");
                    List<String> shuffledOptions = Arrays.asList(options);
                    Collections.shuffle(shuffledOptions);

                    instructionsLabel.setText("%S | Choose an option".formatted(category));
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
                } else if (response.startsWith("TIE")) {
                    instructionsLabel.setText(response);
                } else if (response.startsWith("LOSE")) {
                    instructionsLabel.setText(response);
                } else if (response.startsWith("CORRECT")) {
                    highlightButtonWithColor(response, 8, Color.GREEN);
                } else if (response.startsWith("WRONG")) {
                    highlightButtonWithColor(response, 6, Color.RED);
                }
            }
        } finally {
            socket.close();
        }
    }

    private void highlightButtonWithColor(String response, int beginningIndex, Color color) {
        String option = response.substring(beginningIndex);
        QButton clickedButton = optionButtons.stream()
                .filter(button -> button.getText().equals(option))
                .findFirst()
                .orElseThrow();
        clickedButton.setBackground(color);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickedButton.setBackground(null);
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
