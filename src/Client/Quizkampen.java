package Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Quizkampen implements ActionListener {

    private MessageSender messageSender;
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


    public Quizkampen(MessageSender messageSender) {
       //waitingScreen = new WaitingScreen();
        //showAliasInputPanel();
        this.messageSender = messageSender;
        initializeQuestions();
        setQuestionnairePanel();
        setQuestionArea();
        setFrame();
        displayQuestion(currentQuestionIndex);


    }



    public void showAliasInputPanel() {
        aliasInputPanel = new AliasInputPanel(messageSender);
        //waitingScreen.closeWindow();
        waitingForAlias = true;
    }



    public void initializeQuestions() {
        questions = QuizQuestions.getSampleQuestions();
    }

    public void displayQuestion(int index) {
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
        System.out.println(selectedOption);

        messageSender.sendMessage(selectedOption);

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
}

