package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizQuestions {

    private Map<String, List<Question>> listCategory = new HashMap<>();

    public QuizQuestions() {
        listCategory.put("Geography", getGeographyQuestions());
        listCategory.put("Space", getSpaceQuestions());
        //listCategory.put("Sport", getSportQuestions());
    }

    public List<Question> getGeographyQuestions() {

        List<Question> list = new ArrayList<>();

        List<String> options1 = List.of("Paris", "London", "Berlin", "Madrid");
        Question question1 = new Question("What is the capital of France?", options1, "Paris");
        list.add(question1);

        List<String> options2 = List.of("Mars", "Jupiter", "Venus", "Mercury");
        Question question2 = new Question("Which planet is known as the Red Planet?", options2, "Mars");
        list.add(question2);

        return list;
    }

    public List<Question> getSpaceQuestions() {

        List<Question> list = new ArrayList<>();

        List<String> options1 = List.of("Paris", "London", "Berlin", "Madrid");
        Question question1 = new Question("What is the capital of France?", options1, "Paris");
        list.add(question1);

        List<String> options2 = List.of("Mars", "Jupiter", "Venus", "Mercury");
        Question question2 = new Question("Which planet is known as the Red Planet?", options2, "Mars");
        list.add(question2);

        return list;
    }

    public List<Question> getSportQuestions() {
        List<Question> list = new ArrayList<>();
        List<String> options1 = List.of("Tennis", "Football", "Basket", "Hockey");
        Question question1 = new Question("Something sport....", options1, "Tennis");
        list.add(question1);

        List<String> options2 = List.of("Tennis", "Football", "Basket", "Hockey");
        Question question2 = new Question("Something sport....", options2, "Tennis");
        list.add(question2);

        return list;
    }

    public List<Question> getCategory(String category) {
        return listCategory.get(category);
    }


    public List<String> getListOfCategories() {
        return listCategory.keySet()
                .stream()
                .toList();
    }
}