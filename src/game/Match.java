package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Match {

    ServerSidePlayer playerA;
    ServerSidePlayer playerB;

    GameEngine gameEngine;
    BufferedReader in;
    String playerMark;

    BufferedReader fromClient;

    public Match(ServerSidePlayer playerA, ServerSidePlayer playerB, GameEngine gameEngine, ServerSocket socket) {
        try {

            this.playerA = playerA;
            this.playerB = playerB;
            this.gameEngine = gameEngine;

            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.accept().getInputStream()));

            playerA.out.println("WELCOME ");
            playerA.out.println("MESSAGE Waiting for opponent to connect");

            playerB.out.println("WELCOME ");
            playerB.out.println("MESSAGE Waiting for opponent to connect");


        } catch (IOException e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }


    public void run() {
        try {
            playerA.out.println("MESSAGE All players connected");

            playerB.out.println("MESSAGE All players connected");


            while (gameEngine.getCurrentRound() < gameEngine.getTotalRounds()) {


                //player A

                //System.out.println("Runda: " + gameEngine.getCurrentRound()+" "+playerMark);
                List<Question> questions = gameEngine.getQuestions();
                for (Question currentQuestion : questions) {
                    playerA.out.println("Answer: " + currentQuestion.getCorrectAnswer());
                    playerA.out.println("QUESTION " + currentQuestion.toString());
                    String command = fromClient.readLine();
                    if (command.equals(currentQuestion.getCorrectAnswer())) {
                        playerA.out.println("CORRECT");
                        gameEngine.addScore(playerMark);
                    } else {
                        playerA.out.println("WRONG");
                    }
                }

                //player B

                for (Question currentQuestion : questions) {
                    playerA.out.println("Answer: " + currentQuestion.getCorrectAnswer());
                    playerA.out.println("QUESTION " + currentQuestion.toString());
                    String command = fromClient.readLine();
                    if (command.equals(currentQuestion.getCorrectAnswer())) {
                        playerA.out.println("CORRECT");
                        gameEngine.addScore(playerMark);
                    } else {
                        playerA.out.println("WRONG");
                    }
                }

                // Will also resolve the "locking" of the waiting players instructions (not showing the result)
                    /*try {
                        //TimeUnit.SECONDS.sleep(1);
                        Thread.sleep(100);
                        playerA.out.println("MESSAGE Waiting for other player");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/
            }
            while (gameEngine.bothPlayerAreDone()) {
                if (!gameEngine.isFinalRound()) {
                    gameEngine.nextRound();
                    System.out.println("runda" + gameEngine.getCurrentRound() + playerMark);
                    gameEngine.setPlayerADone(false);
                    gameEngine.setPlayerBDone(false);
                }
                break;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        playerA.out.println(gameEngine.hasWinner()
                ? gameEngine.isWinner(playerMark)
                ? gameEngine.getScore("VICTORY", playerMark)
                : gameEngine.getScore("LOSE", playerMark) : gameEngine.getScore("TIE", playerMark));

        playerB.out.println(gameEngine.hasWinner()
                ? gameEngine.isWinner(playerMark)
                ? gameEngine.getScore("VICTORY", playerMark)
                : gameEngine.getScore("LOSE", playerMark) : gameEngine.getScore("TIE", playerMark));


    }
}
