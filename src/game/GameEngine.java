package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    public QuizQuestions questions;
    public List<String> listOfCategories;
    private String selectedCategory;

    private int currentRound;
    private final int totalRounds = 3;

    private int playerATotalScore;
    private int playerBTotalScore;

    private int playerARoundScore;
    private int playerBRoundScore;

    public GameEngine() {
        questions = new QuizQuestions();
        listOfCategories = questions.getListOfCategories();
        selectRandomCategory();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void addScore(String playerMark) {
        if (playerMark.equals("A")) {
            playerARoundScore++;
            playerATotalScore++;
        } else {
            playerBTotalScore++;
            playerBRoundScore++;
        }
    }

    public boolean hasWinner() {
        return playerATotalScore != playerBTotalScore;
    }

    public void nextRound() {
        if (currentRound < totalRounds) {
            currentRound++;
            playerARoundScore = 0;
            playerBRoundScore = 0;
            selectRandomCategory();
        }
    }

    public boolean isGameFinished() {
        return currentRound >= totalRounds;
    }

    public boolean isWinner(String playerMark) {
        return playerMark.equals("A")
                ? playerATotalScore > playerBTotalScore
                : playerBTotalScore > playerATotalScore;
    }

    // den här kate
    public List<Question> getQuestions() {
        if (selectedCategory != null) {
            List<Question> randomQuestions = questions.getCategory(selectedCategory);
            Collections.shuffle(randomQuestions);
            return randomQuestions.stream().limit(3).toList();
        }
        return new ArrayList<>();
    }

    //Ny metod för att välja en random från början
    //Den väljer bara en kategori om den int
    private void selectRandomCategory() {
        if (!listOfCategories.isEmpty()) {
            Random random = new Random();
            selectedCategory = listOfCategories.get(random.nextInt(listOfCategories.size()));
        } else {
            selectedCategory = null;
        }
    }

    public String getCategoryName() {
        return selectedCategory;
    }

    public String getTotalScore(String result, String playerMark) {
        return "%s Score: %s - %s".formatted(
                result,
                playerMark.equals("A") ? playerATotalScore : playerBTotalScore,
                playerMark.equals("A") ? playerBTotalScore : playerATotalScore);
    }

    public String getRoundScore(String result, String playerMark) {
        return "%s Score: %s - %s".formatted(
                result,
                playerMark.equals("A") ? playerARoundScore : playerBRoundScore,
                playerMark.equals("A") ? playerBRoundScore : playerARoundScore);
    }
}
