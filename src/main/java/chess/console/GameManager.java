package chess.console;

import chess.console.printer.ConsolePrinter;
import chess.console.printer.Printer;
import chess.console.exceptions.BoardOutOfBoundsException;

import java.util.List;

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
    private Printer printer = new ConsolePrinter();
    private Player whitePlayer = new Player(Color.WHITE);
    private boolean whiteLost = false;
    private boolean blackLost = false;
    private boolean draw      = false;


    public void playChess() throws BoardOutOfBoundsException {
        // Prepare board for game
        board.setInitialPosition();
        printer.printBoard(board);

        /* Potentially keep player array and use modulo to chose player. will make loop shorter*/
        while (!isGameOver()) {
            // White to move
            whitePlayer.move(board);

            // print board
            printer.printBoard(board);

            // check for ended game
            boolean gameEnded = testIfGameEnded(board);

            // black to move

            // print board

            // check for ended game
        }
    }

    private boolean testIfGameEnded(Board board) {
        // Test for checkmate
        List<String> kingSquares = board.getKingPositions();
        for (String kingSquare : kingSquares) {
            Color color = board.get(kingSquare).getColor();
            if (board.isAttacked(color, kingSquare)) {
                for (String square : board.getValidMoves(kingSquare)) {

                }
            }
        }

        // Test for 3-fold repetition

        // Test for 50 move draw

        // Test for insufficient material

        // Test for no possible moves
        return false;
    }

    private boolean isGameOver() { return whiteLost || blackLost || draw; }


}
