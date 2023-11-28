package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerSidePlayer extends Thread {

    GameEngine gameEngine;
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    String playerMark;

    public ServerSidePlayer(Socket socket, GameEngine gameEngine, String playerMark) {
        try {
            this.socket = socket;
            this.gameEngine = gameEngine;
            this.playerMark = playerMark;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            out.println("WELCOME " + playerMark);
            out.println("MESSAGE Waiting for opponent to connect");


        } catch (IOException e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }


    public void run() {
        try {
                out.println("MESSAGE All players connected");

            while(gameEngine.getCurrentRound() < gameEngine.getTotalRounds()) {
                //System.out.println("Runda: " + gameEngine.getCurrentRound()+" "+playerMark);
                List<Question> questions = gameEngine.getQuestions();
                for (Question currentQuestion : questions) {
                    out.println("Answer: " + currentQuestion.getCorrectAnswer());
                    out.println("QUESTION " + currentQuestion.toString());
                    String command = in.readLine();
                    if (command.equals(currentQuestion.getCorrectAnswer())) {
                        out.println("CORRECT");
                        gameEngine.addScore(playerMark);
                    } else {
                        out.println("WRONG");
                    }
                }

                gameEngine.isDone(playerMark);
                while (!gameEngine.bothPlayerAreDone()) {

                        if (!gameEngine.isFinalRound()) {
                            gameEngine.nextRound();
                            System.out.println("runda" + gameEngine.getCurrentRound() + playerMark);
                        }
                    // Will also resolve the "locking" of the waiting players instructions (not showing the result)
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        out.println("MESSAGE Waiting for other player");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            out.println(gameEngine.hasWinner()
                    ? gameEngine.isWinner(playerMark)
                    ? gameEngine.getScore("VICTORY", playerMark)
                    : gameEngine.getScore("LOSE", playerMark) : gameEngine.getScore("TIE", playerMark));


        } catch (IOException e) {
            System.out.println("ServerSidePlayer disconnected: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }


}