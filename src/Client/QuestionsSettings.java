package Client;

import java.util.List;

public class QuestionsSettings {
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private int timeLimitInSeconds; // Tidsgräns i sekunder

    public QuestionsSettings(String questionText, List<String> options, String correctAnswer, int timeLimitInSeconds) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.timeLimitInSeconds = timeLimitInSeconds;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getTimeLimitInSeconds() {
        return timeLimitInSeconds;
    }
}

