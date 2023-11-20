package game;

import java.io.IOException;
import java.net.ServerSocket;

public class GameServer {
    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(9999)) {
            System.out.println("GameServer is running...");

            while (true) {

                ServerSidePlayer playerX = new ServerSidePlayer(listener.accept());
                ServerSidePlayer playerO = new ServerSidePlayer(listener.accept());

                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                playerX.start();
                playerO.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

