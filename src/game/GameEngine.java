package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private QuizQuestions questions;
    private List<String> listOfCategories;
    private String selectedCategory;

    private int playerAScore;
    private int playerBScore;

    private boolean playerADone;
    private boolean playerBDone;

    private ServerSidePlayer currentPlayer;

    public GameEngine() {
        questions = new QuizQuestions();
        listOfCategories = questions.getListOfCategories();
        this.playerAScore = 0;
        this.playerBScore = 0;
        selectRandomCategory();
    }

    public ServerSidePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(ServerSidePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public void addScore(String playerMark) {
        if (playerMark.equals("A")) playerAScore++;
        else playerBScore++;
    }

    public void isDone(String playerMark) {
        if (playerMark.equals("A"))
            playerADone = true;
        else
            playerBDone = true;
    }

    public boolean bothPlayerAreDone() {
        return (playerADone && playerBDone);
    }

    public boolean hasWinner() {
        return playerAScore != playerBScore;
    }

    public boolean isWinner(String playerMark) {
        return playerMark.equals("A")
                ? playerAScore > playerBScore
                : playerBScore > playerAScore;
    }



    // den här kate
    public List<Question> getQuestions() {
        if (selectedCategory != null) {
            List<Question> randomQuestions = questions.getCategory(selectedCategory);
            //String randomCategory = listOfCategories.get(new Random().nextInt(0, questions.getCategorySize()));
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

    public String getScore(String result, String playerMark) {
        return "%s Score: %s - %s".formatted(
                result,
                playerMark.equals("A") ? playerAScore : playerBScore,
                playerMark.equals("A") ? playerBScore : playerAScore);
    }
}
