package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSidePlayer extends Thread {

    BufferedReader in;
    PrintWriter out;
    Socket socket;
    String playerMark;

    public ServerSidePlayer(Socket socket, String playerMark) {
        try {
            this.socket = socket;
            this.playerMark = playerMark;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }

    public String getPlayerMark() {
        return playerMark;
    }
}
