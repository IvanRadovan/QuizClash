package Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizQuestions {
    public static List<Question> getSampleQuestions() {
        List<Question> questions = new ArrayList<>();
        //Rond 1
        List<String> options1 = Arrays.asList("Paris", "London", "Berlin", "Madrid");
        Question question1 = new Question("What is the capital of France?", options1, "Paris", 10);
        questions.add(question1);

        List<String> options2 = Arrays.asList("Mars", "Jupiter", "Venus", "Mercury");
        Question question2 = new Question("Which planet is known as the Red Planet?", options2, "Mars", 10);
        questions.add(question2);

        //Rond 2
        List<String> options3 = Arrays.asList("New York", "London", "Paris", "California");
        Question question3 = new Question("In which city is the Statue of Liberty found?", options3, "New York", 10);
        questions.add(question3);

        List<String> options4 = Arrays.asList("1937 - 1945", "1938 - 1946", "1938 - 1945", "1914 - 1924");
        Question question4 = new Question("Between which years did World War II take place??", options4, "1038 - 1945", 10);
        questions.add(question4);



        return questions;
    }
}