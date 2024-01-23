package chess.console;

/**
 * Singleton class to combine and handle game components. Retrieve instance with GameManager.getInstance()
 */
public class GameManager {
    private static GameManager instance = null;
    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager(); }
        return instance;
    }
}
