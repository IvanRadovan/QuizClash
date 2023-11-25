package game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    public QuizQuestions questions;
    public List<String> listOfCategories;

    private int playerAScore;
    private int playerBScore;

    private boolean playerADone;
    private boolean playerBDone;

    public GameEngine() {
        questions = new QuizQuestions();
        listOfCategories = questions.getListOfCategories();
        this.playerAScore = 0;
        this.playerBScore = 0;
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

    public List<Question> getQuestions() {
       String randomCategory = listOfCategories.get(new Random().nextInt(0, questions.getCategorySize()));
       List<Question> randomQuestions = questions.getCategory(randomCategory);
       Collections.shuffle(randomQuestions);
       return randomQuestions.stream().limit(3).toList();
    }
}
