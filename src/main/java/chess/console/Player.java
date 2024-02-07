package chess.console;

import chess.console.inputhandler.InputHandler;
import chess.console.printer.Printer;

public class Player {
    Color color;
    private final InputHandler inputHandler;
    private final Printer printer;

    public Player(Color color, InputHandler inputHandler, Printer printer) {
        this.color = color;
        this.inputHandler = inputHandler;
        this.printer = printer;
    }

    public void move(Board board, MoveLogger moveLogger) {
        Square squareFrom;
        Square squareTo;
        do {
            squareFrom = null;
            squareTo = null;

            printer.printMessage(color + " to move");
            // Get the square to move from
            while (squareFrom == null || !board.isValidSquareFrom(this, squareFrom)) {
                squareFrom = tryGetSquare("Enter square to move from: ");
            }

            // Get the square to move to
            while (squareTo == null || !board.isWithinBoard(squareTo)) {
                squareTo = tryGetSquare("Enter square to move to: ");
            }
        } while (!board.move(squareFrom, squareTo));
        moveLogger.log(board, squareFrom, squareTo);
    }

    /**
     * Prompts the user for a square and returns a [possibly invalid] square if input is length 2, and null otherwise.
     * @param promptMessage the message output to the user before taking input.
     * @Returns: a [possibly invalid] square if input is length 2, and null otherwise.
     */
    private Square tryGetSquare(String promptMessage) {
        printer.printMessage(promptMessage);
        String input = inputHandler.getUserInput();
        Square square = null;
        if (input.length() == 2)  { square = new Square(input); }
        return square;
    }

    public Color getColor() { return color; }
}
