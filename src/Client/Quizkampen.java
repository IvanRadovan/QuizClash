package Client;
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

public class Quizkampen implements ActionListener {
    private JFrame frame = new JFrame("Quizkampen");
    private JTextArea questionArea = new JTextArea();
    private JLabel messageLabel = new JLabel("Welcome to Quizkampen!");
    private JPanel questionnairePanel = new JPanel();

    private List<QButton> optionButtons = new ArrayList<>();
    private int currentQuestionIndex = 0;

    private WaitingScreen waitingScreen;
    private AliasInputPanel aliasInputPanel;

    private boolean connectedToServer = false;
    private boolean waitingForAlias = false;



    private List<Question> questions;
    private static final int PORT = 9999;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Quizkampen() {
        waitingScreen = new WaitingScreen();
        initializeQuestions();
        setQuestionnairePanel();
        setQuestionArea();
        setFrame();
        displayQuestion(currentQuestionIndex);

        connectToServer();

    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            connectedToServer = true;
            showAliasInputPanel();
            handleGame();
        } catch (IOException e) {
            System.out.println("Server connection failed.");
        }
    }
    private void showAliasInputPanel() {
        aliasInputPanel = new AliasInputPanel();
        waitingScreen.closeWindow();
        waitingForAlias = true;
    }
    private void handleGame() {

        while (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            displayQuestion(currentQuestionIndex);


            out.println(currentQuestion.getQuestion());


            String receivedAnswer = null;
            try {
                receivedAnswer = in.readLine();
            } catch (IOException e) {
                System.out.println("Error reading answer from server.");
            }



            currentQuestionIndex++;
        }
    }

    private void initializeQuestions() {
        questions = QuizQuestions.getSampleQuestions();
    }

    private void displayQuestion(int index) {
        if (index < questions.size()) {
            Question currentQuestion = questions.get(index);
            questionArea.setText(currentQuestion.getQuestion());

            for (int i = 0; i < optionButtons.size(); i++) {
                optionButtons.get(i).setText(currentQuestion.getOptions().get(i));
            }
        } else {

            questionArea.setText("Quiz Over. Thanks for playing!");
            for (QButton button : optionButtons) {
                button.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        QButton clickedButton = (QButton) e.getSource();
        String selectedOption = clickedButton.getText();




        currentQuestionIndex++;
        displayQuestion(currentQuestionIndex);
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
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
    }

    private void setFrame() {
        frame.getContentPane().add(questionArea, BorderLayout.NORTH);
        frame.getContentPane().add(questionnairePanel, BorderLayout.CENTER);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class QButton extends JButton {
        public QButton() {
            setPreferredSize(new Dimension(200, 50));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Quizkampen());
    }
}
