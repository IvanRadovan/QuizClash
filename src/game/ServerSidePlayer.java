package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

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
            out.println("WELCOME");
            out.println("MESSAGE Waiting for opponent to connect");


        } catch (IOException e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }


    public void run() {
        try {
            out.println("MESSAGE All players connected");
            List<Question> questions = gameEngine.getQuestions();
            for (Question currentQuestion : questions) {
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
            while (!gameEngine.bothPlayerAreDone()) {}
            out.println(gameEngine.hasWinner() ? "VICTORY" : "TIE");


        } catch (IOException e) {
            System.out.println("ServerSidePlayer died: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }


}
