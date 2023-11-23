package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final int PORT = 9999;
    protected static List<ServerSidePlayer> players = new ArrayList<>();
    private static int connectedPlayers = 0;

    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("GameServer is running...");

            while (true) {

                    ServerSidePlayer player1 = new ServerSidePlayer(listener.accept(), connectedPlayers);
                    players.add(player1);
                    player1.start();
                    connectedPlayers++;
                    System.out.println("Spelare 1 ansluten");

                    ServerSidePlayer player2 = new ServerSidePlayer(listener.accept(), connectedPlayers);
                    players.add(player2);
                    player2.start();
                    connectedPlayers++;
                    System.out.println("Spelare 2 ansluten");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

