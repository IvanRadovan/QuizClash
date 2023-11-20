package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final int PORT = 9999;
    private static List<ServerSidePlayer> players = new ArrayList<>();
    private static int connectedPlayers = 0;

    public static void main(String[] args) {
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("GameServer is running...");

            while (true) {
                if (connectedPlayers < 2) {
                    ServerSidePlayer player = new ServerSidePlayer(listener.accept(), connectedPlayers);
                    players.add(player);
                    player.start();
                    connectedPlayers++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

