package game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class GameEngine {

    public QuizQuestions questions;
    public List<String> listOfCategories;
    private String selectedCategory;

    private int currentRound;
    private static final String PLAYER_MARK_A = "A";

    private static Properties properties = new Properties();
    static {
        try (InputStream input = GameEngine.class.getClassLoader().getResourceAsStream("game.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int QUESTIONS_PER_ROUND = Integer.parseInt(properties.getProperty("QUESTIONS_PER_ROUND"));
    private static final int TOTAL_ROUNDS = Integer.parseInt(properties.getProperty("TOTAL_ROUNDS"));

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
        if (playerMark.equals(PLAYER_MARK_A)) {
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
        if (currentRound < TOTAL_ROUNDS) {
            currentRound++;
            playerARoundScore = 0;
            playerBRoundScore = 0;
            selectRandomCategory();
        }
    }

    public void playAgain() {
        playerATotalScore = 0;
        playerBTotalScore = 0;
        currentRound = 0;
    }

    public boolean isGameFinished() {
        return currentRound >= TOTAL_ROUNDS;
    }

    public boolean isWinner(String playerMark) {
        return playerMark.equals(PLAYER_MARK_A)
                ? playerATotalScore > playerBTotalScore
                : playerBTotalScore > playerATotalScore;
    }

    public List<Question> getQuestions() {
        List<Question> randomQuestions = questions.getCategory(selectedCategory);
        Collections.shuffle(randomQuestions);
        return randomQuestions.stream()
                .limit(QUESTIONS_PER_ROUND)
                .toList();
    }

    private void selectRandomCategory() {
        Random random = new Random();
        selectedCategory = listOfCategories.get(random.nextInt(listOfCategories.size()));
    }

    public String getCategoryName() {
        return selectedCategory;
    }

    public String getTotalScore(String result, String playerMark) {
        return "%s Total Score: %s - %s".formatted(result,
                playerMark.equals(PLAYER_MARK_A) ? playerATotalScore : playerBTotalScore,
                playerMark.equals(PLAYER_MARK_A) ? playerBTotalScore : playerATotalScore);
    }

    public String getRoundScore(String result, String playerMark) {
        return "%s Round Score: %s - %s".formatted(result,
                playerMark.equals(PLAYER_MARK_A) ? playerARoundScore : playerBRoundScore,
                playerMark.equals(PLAYER_MARK_A) ? playerBRoundScore : playerARoundScore);
    }
}
