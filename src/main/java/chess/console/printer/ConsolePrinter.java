package chess.console.printer;

import chess.console.Board;
import chess.console.pieces.Piece;

public class ConsolePrinter implements Printer {
    @Override
    public void printBoard(Board board) {

        int fileNameWidth = 3;
        int columnWidth = 6;
        int rowWidth = 49;
        // print top edge of board
        printRow(rowWidth, fileNameWidth);

        for (char rank = (char)('1' + Board.SIZE - 1); rank >= '1'; rank--) { // print rank in reverse order from underlying implementation
            System.out.println();
            System.out.print(rank + "  |"); // 'a' = 97, '1' = 49
            for (char file = 'a'; file < 'a' + Board.SIZE; file++) {
                printPiece(board.get("" + file + rank));
                System.out.print('|');
            }
            System.out.println();
            printRow(rowWidth, fileNameWidth);
        }

        System.out.println();
        printFiles(columnWidth, fileNameWidth);
        System.out.println();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printResult(boolean whiteLost, boolean blackLost, boolean draw) {
        if (draw) {
            System.out.println( "The game ended in a draw.");
            return;
        }

        String winner = whiteLost ? "black" : "white";
        System.out.println("Congratulations to " + winner + ", you have won!");
    }

    private void printFiles(int columnWidth, int fileNameWidth) {
        System.out.print(" ".repeat(fileNameWidth + columnWidth/2));
        for (char file = 'a'; file <= 'h'; file++) {
            System.out.print(file + " ".repeat(columnWidth - 1));
        }
    }

    private void printPiece(Piece piece) {
        if (piece == null)
            { System.out.print("     "); }
        else
            { System.out.print("  " + piece + "  "); }

    }

    public void printRow(int width, int fileNameWidth) {
        System.out.print(" ".repeat(fileNameWidth));
        System.out.print("-".repeat(width));
    }
}
