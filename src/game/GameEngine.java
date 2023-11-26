package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    public QuizQuestions questions;
    public List<String> listOfCategories;

    private int playerAScore;
    private int playerBScore;
    private String selectedCategory;

    private boolean playerADone;
    private boolean playerBDone;

    public GameEngine() {
        questions = new QuizQuestions();
        listOfCategories = questions.getListOfCategories();
        this.playerAScore = 0;
        this.playerBScore = 0;
        selectRandomCategory();
    }

    public void addScore(String playerMark) {
        if (playerMark.equals("A")) {
            this.playerAScore++;
        } else {
            this.playerBScore++;
        }
    }

    public void isDone(String playerMark) {
        if (playerMark.equals("A")) {
            playerADone = true;
        } else {
            playerBDone = true;
        }
    }

    public boolean bothPlayerAreDone() {
        return (playerADone && playerBDone);
    }

    public boolean hasWinner() {
        return this.playerAScore != this.playerBScore;
    }

    public boolean isWinner(String playerMark) {
        if (playerMark.equals("A")) {
            return this.playerAScore > this.playerBScore;
        }
        return this.playerBScore > this.playerAScore;
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
    private void selectRandomCategory(){
        if(!listOfCategories.isEmpty()) {
            Random random = new Random();
            selectedCategory = listOfCategories.get(random.nextInt(listOfCategories.size()));
        } else {
            selectedCategory = null;
        }
    }

    public String getScore(String result, String playerMark) {
        if (playerMark.equals("A")) {
            return "%s Score: %s - %s".formatted(result, playerAScore, playerBScore);
        }
        return "%s Score: %s - %s".formatted(result, playerBScore, playerAScore);
    }
}
