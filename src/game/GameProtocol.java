package game;

public class GameProtocol {

    private static final int WAITING = 0;
    private static final int GAME_STARTED = 1;

//    private static final int GAME_ENDED = 2;

    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Waiting for the other player";
            state = GAME_STARTED;
        } else if (state == GAME_STARTED) {
            // TODO:
        }
        return theOutput;
    }
}
