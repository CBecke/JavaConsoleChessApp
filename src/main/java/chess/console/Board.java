package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;

public class Board {

    // board[7][0] is a1
    Knight[][] board;

    public Board() {
        board = new Knight[8][8];
    }

    public void put(Knight knight, String square) throws BoardOutOfBoundsException {
        if (!isWithinBoard(square)) { throw new BoardOutOfBoundsException("Given square '" + square + "' is not in range a0-h8"); }

        int file = square.charAt(0) - 'a';
        int rank = square.charAt(1) - '1'; // subtract '1' since board is 0-indexed

        board[rank][file] = knight;
    }

    private boolean isWithinBoard(String square) {
        return 'a' <= square.charAt(0) && square.charAt(0) <= 'h';
    }

    public void move(String squareFrom, String squareTo) {
        int fileFrom = squareFrom.charAt(0) - 'a';
        int rankFrom = squareFrom.charAt(1) - '1'; // subtract '1' since board is 0-indexed
        int fileTo   = squareTo.charAt(0) - 'a';
        int rankTo   = squareTo.charAt(1) - '1';   // subtract '1' since board is 0-indexed

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    public Knight get(String square) {
        int file = square.charAt(0) - 'a';
        int rank = square.charAt(1) - '1'; // subtract 1 because board is 0-indexed
        return board[rank][file];
    }
}
