package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final int PORT = 9999;
    private static List<ServerSidePlayer> players = new ArrayList<>();
    private static int connectedPlayers = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket();
        System.out.println("Game is running...");
        try {
            while (true) {
//                ServerSideGame game = new ServerSideGame();
//
//                ServerSidePlayer firstPlayer = new ServerSidePlayer();
//                ServerSidePlayer secondPlayer = new ServerSidePlayer();



            }
        } finally {
            listener.close();
        }
    }
}

