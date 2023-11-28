package game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {


    public static void main(String[] args) {


        try (ServerSocket listener = new ServerSocket(9999)) {
            System.out.println("Serving is running and waiting for connections...");

            while (true) {

                GameEngine gameEngine = new GameEngine();

                ServerSidePlayer serverSidePlayerA = new ServerSidePlayer(listener.accept());
                ServerSidePlayer serverSidePlayerB = new ServerSidePlayer(listener.accept());

                Match match = new Match(serverSidePlayerA, serverSidePlayerB, gameEngine);
                match.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
