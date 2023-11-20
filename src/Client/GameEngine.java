package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class GameEngine {
    private List<Question> questions;
    private static final int PORT = 9999;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private boolean connectedToServer = false;
    private boolean waitingForAlias = false;
    private int currentQuestionIndex = 0;

    private WaitingScreen waitingScreen;
    private AliasInputPanel aliasInputPanel;
    private QuestionPanel questionPanel;


    public GameEngine() {
        waitingScreen = new WaitingScreen();
        questions = QuizQuestions.getSampleQuestions();
        questionPanel = new QuestionPanel();
    }

    public void startGame() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            connectedToServer = true;
            waitingScreen.closeWindow();
            waitingForAlias = true;
            showAliasInputPanel();
            handleGame();
        } catch (IOException e) {
            System.out.println("Server connection failed.");
        }
    }

    private void showAliasInputPanel() {
        aliasInputPanel = new AliasInputPanel();
        waitingForAlias = true;
    }

    private void handleGame() {

        while (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionPanel.displayQuestion(currentQuestion);


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
}