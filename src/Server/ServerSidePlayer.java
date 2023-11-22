package Server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSidePlayer extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int playerNumber;

    public ServerSidePlayer(Socket socket, int playerNumber) {
        this.socket = socket;
        this.playerNumber = playerNumber;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME");
            output.println("MESSAGE Waiting for player " + (playerNumber + 1) + " to enter alias");
        } catch (IOException e) {
            System.out.println("Connection lost.");
        }
    }

    @Override
    public void run() {
        try {
            output.println("ENTER_ALIAS");
            String alias = input.readLine();

            output.println("MESSAGE Welcome, " + alias + "! Game starting...");

            while(true) {
                String message = input.readLine();
                System.out.println(message);
            }


        } catch (IOException e) {
            System.out.println("Player disconnected.");
        }
    }
}