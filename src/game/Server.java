package game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {


    public static void main(String[] args) {


        try (ServerSocket listener = new ServerSocket(9999)) {
            System.out.println("Serving is running and waiting for connections...");

            while (true) {

                GameEngine gameEngine = new GameEngine();

                ServerSidePlayer serverSidePlayerA = new ServerSidePlayer(listener.accept(), gameEngine, "A");
                ServerSidePlayer serverSidePlayerB = new ServerSidePlayer(listener.accept(), gameEngine, "B");

                serverSidePlayerA.setOpponent(serverSidePlayerB);
                serverSidePlayerB.setOpponent(serverSidePlayerA);
                gameEngine.setCurrentPlayer(serverSidePlayerA);

                serverSidePlayerA.start();
                serverSidePlayerB.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
