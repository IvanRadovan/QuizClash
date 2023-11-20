package Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizQuestions {
    public static List<Question> getSampleQuestions() {
        List<Question> questions = new ArrayList<>();

        List<String> options1 = Arrays.asList("Paris", "London", "Berlin", "Madrid");
        Question question1 = new Question("What is the capital of France?", options1, "Paris");
        questions.add(question1);

        List<String> options2 = Arrays.asList("Mars", "Jupiter", "Venus", "Mercury");
        Question question2 = new Question("Which planet is known as the Red Planet?", options2, "Mars");
        questions.add(question2);

        return questions;
    }
}