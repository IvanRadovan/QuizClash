package game;

import java.util.List;
import java.util.stream.Collectors;

public class Question {

    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        final char SPLIT_MARKER = '/';
        final String allOptions = options.stream()
                .map(option -> option.concat(","))
                .collect(Collectors.joining());

        return "%s%c%s".formatted(questionText, SPLIT_MARKER, allOptions);
    }
}