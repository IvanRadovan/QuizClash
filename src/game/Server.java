package game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket listener = new ServerSocket(9999)) {
            System.out.println("Serving is running and waiting for connections...");

            while (true) {
                Match match = new Match(
                        new ServerSidePlayer(listener.accept(), "A"),
                        new ServerSidePlayer(listener.accept(), "B"));
                match.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
