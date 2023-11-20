package Client;

import javax.swing.*;

public class ClientQuizClash{
    private GameEngine gameEngine;

    public ClientQuizClash() {
        gameEngine = new GameEngine();
        gameEngine.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Quizkampen());
    }
}