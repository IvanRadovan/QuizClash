package Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameProperties {
    public Properties properties;

    public GameProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/Properties/properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRounds() {
        return Integer.parseInt(properties.getProperty("rounds", "2"));
    }

    public int getQuestionsPerRound() {
        return Integer.parseInt(properties.getProperty("questions_per_round", "2"));
    }
}