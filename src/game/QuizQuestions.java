package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizQuestions {

    private Map<String, List<Question>> listCategory = new HashMap<>();

    public QuizQuestions() {
        listCategory.put("Sport", getSportQuestions());
        listCategory.put("Space", getSpaceQuestions());
    }

    public List<Question> getSpaceQuestions() {
        List<Question> list = new ArrayList<>();

        createQuestion(List.of("Earth", "Saturn ", "Mars", "Jupiter"),
                "What is the largest planet in our solar system?",
                "Jupiter", list);

        createQuestion(List.of("Milky Way", "Sombrero", "Andromeda", "Triangulum"),
                "Which galaxy is home to our solar system?",
                "Milky Way", list);

        createQuestion(List.of("Sputnik 1", "Hubble", "Explorer 1", "Vanguard 1"),
                "What is the name of the first artificial satellite launched into space?",
                "Sputnik 1", list);

        createQuestion(List.of("Jupiter", "Mars", "Venus", "Earth"),
                "Which planet is known as the \"Red Planet\"?",
                "Mars", list);

        createQuestion(List.of("Proxima Centauri", "Alpha Centauri A", "Sirius", "Betelgeuse"),
                "What is the name of the closest star to Earth?",
                "Proxima Centauri", list);

        return list;
    }

    public List<Question> getSportQuestions() {
        List<Question> list = new ArrayList<>();

        createQuestion(List.of("United States", "Canada", "Brazil", "Australia"),
                "In which country did the sport of basketball originate?",
                "United States", list);

        createQuestion(List.of("Mike Tyson", "Muhammad Ali", "Floyd Mayweather", "Manny Pacquiao"),
                "Who is often referred to as \"The Greatest\" in the sport of boxing?",
                "Muhammad Ali", list);

        createQuestion(List.of("Beijing", "Rio de Janeiro", "London", "Sydney"),
                "Which city hosted the 2016 Summer Olympics?",
                "Rio de Janeiro", list);

        createQuestion(List.of("8", "10", "11", "12"),
                "In soccer, what is the maximum number of players a team can have on the field during a match?",
                "11", list);

        createQuestion(List.of("England", "South Africa", "Australia", "India"),
                "Which country is famous for its dominance in the sport of cricket?",
                "India", list);

        return list;
    }

    private void createQuestion(List<String> options, String question, String correctAnswer, List<Question> list) {
        Question question5 = new Question(question, options, correctAnswer);
        list.add(question5);
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