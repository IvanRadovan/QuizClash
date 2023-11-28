package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerSidePlayer extends Thread {

    BufferedReader in;
    PrintWriter out;
    Socket socket;

    public ServerSidePlayer(Socket socket) {
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);


        } catch (IOException e) {
            System.out.println("Player disconnected: " + e.getMessage());
        }
    }

}
