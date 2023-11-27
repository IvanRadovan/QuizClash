package game;

import Properties.GameProperties;

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

    private JFrame frame = new JFrame("QuizClash");
    private JLabel instructionsLabel = new JLabel("Instructions goes here...");

    private JLabel questionLabel = new JLabel();
    private JPanel questionnairePanel = new JPanel();

    private List<QButton> optionButtons = new ArrayList<>();


    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    private GameProperties gameProperties;
    private int roundsPlayed;

    private GameEngine gameEngine;


    public GameClient() throws IOException {
        socket = new Socket("localhost", 9999);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String alias = JOptionPane.showInputDialog("Enter alias:");
        frame.setTitle(frame.getTitle() + " " + alias);
        setQuestionnairePanel();
        setQuestionArea();
        setFrame();
        gameProperties = new GameProperties();
        roundsPlayed = 0;
        gameEngine = new GameEngine();

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        QButton clickedButton = (QButton) e.getSource();
        String selectedOption = clickedButton.getText();
        out.println(selectedOption);
        System.out.println("You clicked " + selectedOption); // For testing


//        try {
//            if ((in.readLine().equals("CORRECT"))) {
//                clickedButton.setForeground(Color.GREEN);
//            } else {
//                clickedButton.setForeground(Color.RED);
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }


    }

    private void setQuestionnairePanel() {
        questionnairePanel.setLayout(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++) {
            QButton optionButton = new QButton();
            optionButton.addActionListener(this);
            optionButtons.add(optionButton);
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

        while (roundsPlayed < gameProperties.getRounds()) {
            try {
                response = in.readLine();
                System.out.println(response);

                if (response == null) {
                    instructionsLabel.setText("Waiting for server");
                    continue;
                }

                if (response.equals("WELCOME")) {
                    instructionsLabel.setText(response);
                }

                List<Question> questions = gameEngine.getQuestions(); // Fetch new questions
                while (true) {
                    response = in.readLine();
                    if (response == null) {
                        instructionsLabel.setText("Waiting for server");
                        continue;
                    }

                    System.out.println(response);

                    if (response.startsWith("QUESTION")) {
                        String[] questionData = response.substring(8).split("/");
                        String question = questionData[0];
                        String[] options = questionData[1].split(",");
                        instructionsLabel.setText("Choose an option");
                        questionLabel.setText(question);
                        IntStream.range(0, optionButtons.size())
                                .forEach(i -> optionButtons.get(i).setText(options[i]));

                        break; // Break the inner loop to wait for user input
                    } else if (response.startsWith("MESSAGE")) {
                        instructionsLabel.setText(response.substring(8));
                    } else if (response.startsWith("VICTORY") || response.startsWith("TIE") || response.startsWith("LOSE")) {
                        instructionsLabel.setText(response);
                        roundsPlayed++; // Increment rounds played
                        break; // Break the inner loop to wait for next round or game end
                    }
                }
            } finally {
                // Add any necessary cleanup or finalization steps
            }
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
