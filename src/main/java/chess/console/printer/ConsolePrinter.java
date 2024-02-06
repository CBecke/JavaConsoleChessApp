package chess.console.printer;

import chess.console.Board;
import chess.console.pieces.Piece;

public class ConsolePrinter implements Printer {
    int fileNameWidth = 3;
    int columnWidth = 6;
    int rowWidth = 49;

    @Override
    public void printBoard(Board board) {
        printBoard(board, 'a', 1, (char)('a'+Board.SIZE-1), 8);
        System.out.println();
        printFiles(columnWidth, fileNameWidth, 'a', (char)('a'+Board.SIZE-1));
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

    @Override
    public void printFlippedBoard(Board board) {
        printBoard(board, (char)('a'+Board.SIZE-1), 8, 'a', 1);
        System.out.println();
        printFiles(columnWidth, fileNameWidth, (char)('a'+Board.SIZE-1), 'a');
        System.out.println();
    }

    /**
     * Prints the board such that (firstFile, firstRank) is the bottom left square, and (lastFile,lastRank) is the top
     * right square.
     * @param firstFile: left file
     * @param lastFile: right file
     * @param firstRank: bottom rank
     * @param lastRank: top rank
     */
    private void printBoard(Board board, char firstFile, int firstRank, char lastFile, int lastRank) {
        // print top edge of board
        printRow(rowWidth, fileNameWidth);

        // Reverse rank to match underlying board implementation
        int temp = firstRank;
        firstRank = lastRank;
        lastRank = temp;

        int rankDirection = (lastRank > firstRank) ? 1 : -1;
        int fileDirection = (lastFile > firstFile) ? 1 : -1;
        for (int currentRank = firstRank; currentRank != (lastRank+rankDirection); currentRank += rankDirection) {
            System.out.println();
            System.out.print(currentRank + "  |");
            for (char currentFile = firstFile; currentFile != (lastFile+fileDirection); currentFile = (char)(currentFile + fileDirection)) {
                printPiece(board.get("" + currentFile + currentRank));
                System.out.print('|');
            }
            System.out.println();
            printRow(rowWidth, fileNameWidth);
        }
    }

    private void printFiles(int columnWidth, int fileNameWidth, char firstFile, char lastFile) {
        System.out.print(" ".repeat(fileNameWidth + columnWidth/2));
        int fileDirection = (lastFile > firstFile) ? 1 : -1;
        for (char file = firstFile; file != (char)(lastFile+fileDirection); file = (char)(file+fileDirection)) {
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
