package chess.console;

import chess.console.inputhandler.ConsoleInputHandler;
import chess.console.inputhandler.InputHandler;

public class Player {
    Color color;
    InputHandler inputHandler = new ConsoleInputHandler();

    public Player(Color color) {
        this.color = color;
    }

    public boolean move(Board board) {
        // Get the square to move from
        String squareFrom = "";
        System.out.println(color + " to move");
        while (!board.isValidSquareFrom(this, squareFrom)) {
            System.out.println("Enter square to move from: ");
            squareFrom = inputHandler.getUserInput();
        }

        // Get the square to move to
        String squareTo = "";
        while (!board.isWithinBoard(squareTo)) {
            System.out.println("Enter square to move to: ");
            squareTo = inputHandler.getUserInput();
        }

        // test if move is valid on the board
        return board.move(squareFrom, squareTo);
    }

    public Color getColor() { return color; }
}
