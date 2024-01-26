package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;
import chess.console.pieces.Piece;

/**
 * Singleton class to combine and handle game components. Retrieve instance with GameManager.getInstance()
 */
public class GameManager {
    /// Singleton components
    private static GameManager instance = null;
    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager(); }
        return instance;
    }
    // Logic
    private final Board board = new Board();
    private Player whitePlayer;
    private boolean whiteLost = false;
    private boolean blackLost = false;
    private boolean draw      = false;


    public void playChess() throws BoardOutOfBoundsException {
        // Prepare board for game
        board.setInitialPosition();

        /* Potentially keep player array and use modulo to chose player. will make loop shorter*/
        while (!isGameOver()) {
            // White to move
            while (!whitePlayer.move(board));

            // print board

            // check for ended game

            // black to move

            // print board

            // check for ended game
        }
    }

    private boolean isGameOver() { return whiteLost || blackLost || draw; }


}
