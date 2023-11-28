package game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {


    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(9999)) {
            System.out.println("Server is running and waiting for connections...");

            boolean gameRunning = true;

            while (gameRunning) {
                GameEngine gameEngine = new GameEngine();

                ServerSidePlayer serverSidePlayerA = new ServerSidePlayer(listener.accept(), gameEngine, "A");
                ServerSidePlayer serverSidePlayerB = new ServerSidePlayer(listener.accept(), gameEngine, "B");

                serverSidePlayerA.start();
                serverSidePlayerB.start();


                try {
                    serverSidePlayerA.join();
                    serverSidePlayerB.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                if (serverSidePlayerA.isGameOver() && serverSidePlayerB.isGameOver()) {
                    gameRunning = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
