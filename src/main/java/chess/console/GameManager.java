package chess.console;

import chess.console.printer.ConsolePrinter;
import chess.console.printer.Printer;

import java.util.Collection;


public class GameManager {
    private final Board board = new Board();
    private final Printer printer = new ConsolePrinter();
    private Player whitePlayer = new Player(Color.WHITE);
    private final MoveLogger logger = new MoveLogger();
    private boolean whiteLost = false;
    private boolean blackLost = false;
    private boolean draw      = false;

    public void playChess() {
        printer.printBoard(board);

        /* Potentially keep player array and use modulo to chose player. will make loop shorter*/
        while (!isGameOver()) {
            // White to move
            whitePlayer.move(board, logger);

            // print board
            printer.printBoard(board);

            // check for ended game
            boolean gameEnded = hasGameEnded(board);

            // report if game ended

            // black to move

            // print board

            // check for ended game
        }

    }

    private boolean hasGameEnded(Board board) {
        // Test for checkmate
        Collection<String> kingSquares = board.getKingPositions();
        for (String kingSquare : kingSquares) {
            if (board.isCheckmate(kingSquare)) {
                Color color = board.get(kingSquare).getColor();
                setLostFlag(color, true);
                return true;
            }
        }

        // Test for 3-fold repetition
        if (logger.isThreeFoldRepetition() || logger.isFiftyMoveDraw() || isInsufficientMaterialDraw(board)) {
            draw = true;
            return true;
        }

        // Test for insufficient material


        // Test for stalemate

        // TODO: draw by agreement
        // TODO: resignation

        return false;
    }


    private void setLostFlag(Color color, boolean b) {
        if (color == Color.WHITE)
            { whiteLost = b; }
        else
            { blackLost = b; }
    }

    private boolean isGameOver() { return whiteLost || blackLost || draw; }


}
