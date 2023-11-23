package Server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSidePlayer extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int playerNumber;
    private List<String> answers = new ArrayList<>();

    public ServerSidePlayer(Socket socket, int playerNumber) {
        this.socket = socket;
        this.playerNumber = playerNumber;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME");
            //output.println("MESSAGE Waiting for player " + (playerNumber + 1) + " to enter alias");
            if (playerNumber <2) {
            output.println("Player " +(playerNumber + 1) +" start");
            } else
                output.println("Player " +(playerNumber + 1) +" wait for player 1 to finish");
        } catch (IOException e) {
            System.out.println("Connection lost.");
        }
    }

    @Override
    public void run() {
        try {
            //output.println("ENTER_ALIAS");
            //String alias = input.readLine();

            //output.println("MESSAGE Welcome, " + alias + "! Game starting...");
            output.println("Player 1 can start");

            while(answers.size() <2) {
                String message = input.readLine();
                System.out.println(message);
                answers.add(message);
            }

            System.out.println("player 1 answer List:");
            for (String i : answers) {
                System.out.println(i);
            }
            System.out.println("done");

            output.println("player 2 can start");

        } catch (IOException e) {
            System.out.println("Player disconnected.");
        }
    }
}