package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;

public class Main {
    public static void main(String[] args) throws BoardOutOfBoundsException {
        GameManager gameManager = GameManager.getInstance();
        gameManager.playChess();
    }
}