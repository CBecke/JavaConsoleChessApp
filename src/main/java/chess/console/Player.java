package chess.console;

import chess.console.inputhandler.ConsoleInputHandler;
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
            squareFrom = new Square("invalid");
            squareTo = new Square("invalid");
            // Get the square to move from
            printer.printMessage(color + " to move");

            // Get the square to move from
            while (!board.isValidSquareFrom(this, squareFrom)) {
                printer.printMessage("Enter square to move from: ");
                squareFrom = new Square(inputHandler.getUserInput());
            }

            // Get the square to move to
            while (!board.isWithinBoard(squareTo)) {
                printer.printMessage("Enter square to move to: ");
                squareTo = new Square(inputHandler.getUserInput());
            }
        } while (!board.move(squareFrom, squareTo));
        moveLogger.log(board, squareFrom, squareTo);
    }

    public Color getColor() { return color; }
}
