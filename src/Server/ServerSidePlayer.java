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
    private ServerSidePlayer opponent;

    //private int playerNumber;


    public ServerSidePlayer(Socket socket, ServerSidePlayer opponent) {
        this.socket = socket;
        this.opponent = opponent;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME");
            output.println("MESSAGE Waiting for the other player to connect.");
        } catch (IOException e) {
            System.out.println("Connection lost.");
        }
    }

    public ServerSidePlayer getOpponent() {
        return opponent;
    }

    public void setOpponent(ServerSidePlayer opponent) {
        this.opponent = opponent;
    }

    @Override
    public void run() {
        try {
            // The thread is only started after everyone connects.
            output.println("MESSAGE All players connected");


            // Repeatedly get commands from the client and process them.
            while (true) {
                String command = input.readLine();
                if (command.startsWith("MOVE")) {
//                    int location = Integer.parseInt(command.substring(5));
//                    if (game.legalMove(location, this)) {
//                        output.println("VALID_MOVE");
//                        output.println(game.hasWinner() ? "VICTORY"
//                                : game.boardFilledUp() ? "TIE"
//                                : "");
//                    } else {
//                        output.println("MESSAGE ?");
//                    }
                } else if (command.startsWith("QUIT")) {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Player disconnected: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }
}