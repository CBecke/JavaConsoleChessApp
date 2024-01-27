package chess.console;

import chess.console.inputhandler.ConsoleInputHandler;
import chess.console.inputhandler.InputHandler;

public class Player {
    Color color;
    InputHandler inputHandler = new ConsoleInputHandler();

    public Player(Color color) {
        this.color = color;
    }

    public void move(Board board) {
        String squareFrom = "";
        String squareTo = "";
        do {
            // Get the square to move from
            System.out.println(color + " to move");
            while (!board.isValidSquareFrom(this, squareFrom)) {
                System.out.print("Enter square to move from: ");
                squareFrom = inputHandler.getUserInput();
            }

            // Get the square to move to
            while (!board.isWithinBoard(squareTo)) {
                System.out.print("Enter square to move to: ");
                squareTo = inputHandler.getUserInput();
            }
        } while (!board.move(squareFrom, squareTo));
    }

    public Color getColor() { return color; }
}
