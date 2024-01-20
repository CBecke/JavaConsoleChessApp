package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;

public class Board {

    // board[7][0] is a1
    Knight[][] board;

    public Board() {
        board = new Knight[8][8];
    }

    public void put(Knight knight, String square) throws BoardOutOfBoundsException {
        if (!isWithinBoard(square)) { throw new BoardOutOfBoundsException(square); }

        int file = getFile(square);
        int rank = getRank(square);

        board[rank][file] = knight;
    }

    private static int getFile(String square) {
        return square.charAt(0) - 'a';
    }

    private static int getRank(String squareFrom) {
        return squareFrom.charAt(1) - '1'; // subtract '1' since board is 0-indexed
    }

    private boolean isWithinBoard(String square) {
        return square.length() == 2
                && 'a' <= square.charAt(0) && square.charAt(0) <= 'h'
                && '1' <= square.charAt(1) && square.charAt(1) <= '8';
    }

    public void move(Knight knight, String squareFrom, String squareTo) throws BoardOutOfBoundsException, IllegalMoveException {
        if (!isWithinBoard(squareTo)) { throw new BoardOutOfBoundsException(squareTo); }
        if (isEmpty(squareFrom)) { throw new IllegalMoveException("The square moved from is empty"); }
        if (!canGoTo(knight, squareTo)) { return; };

        int fileFrom = getFile(squareFrom);
        int rankFrom = getRank(squareFrom);
        int fileTo   = getFile(squareTo);
        int rankTo   = getRank(squareTo);

        if (!knight.isValidMove(squareFrom, squareTo)) { return; }

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    private boolean canGoTo(Knight knight, String squareTo) {
        return isEmpty(squareTo) || isOppositeColor(knight, squareTo);
    }

    private boolean isOppositeColor(Knight knight, String square) {
        int file = getFile(square);
        int rank = getRank(square);
        return knight.getColor() != board[rank][file].getColor();
    }

    public boolean isEmpty(String square) {
        int file = getFile(square);
        int rank = getRank(square);
        return board[rank][file] == null;
    }


    public Knight get(String square) {
        int file = getFile(square);
        int rank = getRank(square); // subtract 1 because board is 0-indexed
        return board[rank][file];
    }
}
