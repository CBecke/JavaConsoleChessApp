package chess.console.printer;

import chess.console.Board;

public interface Printer {
    void printBoard(Board board);

    void printMessage(String message);

    void printResult(boolean whiteLost, boolean blackLost, boolean draw);
}
