package game;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Match extends Thread {

    ServerSidePlayer playerA;
    ServerSidePlayer playerB;
    GameEngine gameEngine;

    public Match(ServerSidePlayer playerA, ServerSidePlayer playerB) {
        try {
            this.playerA = playerA;
            this.playerB = playerB;
            this.gameEngine = new GameEngine();

            playerA.out.println("WELCOME " + playerA.getPlayerMark());
            playerB.out.println("WELCOME " + playerB.getPlayerMark());
        } catch (Exception e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }


    public void run() {
        while (true) {
            while (!gameEngine.isGameFinished()) {
                sendCurrentRound(playerA);
                sendCurrentRound(playerB);


                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                List<Question> questions = gameEngine.getQuestions();

                sendRoundExecutor(playerA, questions);
                sendRoundExecutor(playerB, questions);

                sendRoundScore(playerA);
                sendRoundScore(playerB);

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                gameEngine.nextRound();
            }
            sendTotalScore(playerA);
            sendTotalScore(playerB);

            sendPlayAgain(playerA);
            sendPlayAgain(playerB);

            if (!playAgain(playerA) || !playAgain(playerB)) {
                playerA.out.println("QUIT");
                playerB.out.println("QUIT");
                break;
            }
            gameEngine.playAgain();

        }

    }

    private void sendRoundExecutor(ServerSidePlayer player, List<Question> questions) {
        for (Question currentQuestion : questions) {
            player.out.println("QUESTION %s#%s".formatted(gameEngine.getCategoryName(), currentQuestion));
            String command;
            try {
                command = player.in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(currentQuestion.getCorrectAnswer())) {
                player.out.println("CORRECT " + command);
                gameEngine.addScore(player.getPlayerMark());
            } else {
                player.out.println("WRONG " + command);
            }
        }
        player.out.println("WAITING ");
    }

    private void sendRoundScore(ServerSidePlayer player) {
        player.out.println(gameEngine.getRoundScore("MESSAGE ", player.getPlayerMark()));
    }

    private void sendTotalScore(ServerSidePlayer player) {
        player.out.println(gameEngine.hasWinner() ? gameEngine.isWinner(player.playerMark)
                ? gameEngine.getTotalScore("VICTORY ", player.playerMark)
                : gameEngine.getTotalScore("LOSE ", player.playerMark)
                : gameEngine.getTotalScore("TIE ", player.playerMark));
    }

    private void sendCurrentRound(ServerSidePlayer player) {
        player.out.println("MESSAGE Round: " + (gameEngine.getCurrentRound() + 1));
    }
    private void sendPlayAgain(ServerSidePlayer player) {
        player.out.println("PLAY_AGAIN " + player.playerMark);
    }

    private boolean playAgain(ServerSidePlayer player) {
        String messageReceived;
        try {
            messageReceived = player.in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return !messageReceived.equals("FINISH");
    }

}
