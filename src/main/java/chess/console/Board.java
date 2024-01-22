package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;
import chess.console.pieces.Piece;

public class Board {

    // board[7][0] is a1 and board[7][7] is a8
    Piece[][] board;

    public static int SIZE = 8;

    public Board() {
        board = new Piece[SIZE][SIZE];
    }

    // potential bug: putting a piece on non-empty square
    public void put(Piece piece, String square) throws BoardOutOfBoundsException {
        if (!isWithinBoard(square)) {
            throw new BoardOutOfBoundsException(square);
        }

        int file = getFile(square);
        int rank = getRank(square);

        board[rank][file] = piece;
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

    public void move(Piece piece, String squareFrom, String squareTo) throws BoardOutOfBoundsException, IllegalMoveException {
        if (!isWithinBoard(squareTo)) { throw new BoardOutOfBoundsException(squareTo); }
        if (isEmpty(squareFrom)) { throw new IllegalMoveException("The square moved from is empty"); }
        if (!canGoTo(piece, squareTo)) { return; }

        int fileFrom = getFile(squareFrom);
        int rankFrom = getRank(squareFrom);
        int fileTo = getFile(squareTo);
        int rankTo = getRank(squareTo);

        if (isStillStandingMove(fileTo-fileFrom, rankTo-rankFrom)) { return; }

        if (!piece.isValidMove(this, squareFrom, squareTo)) {
            return;
        }

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    private boolean isStillStandingMove( int fileDiff, int rankDiff){
        return (fileDiff == 0) && (rankDiff == 0);
    }

    private boolean canGoTo(Piece piece, String squareTo) {
        return isEmpty(squareTo) || isOppositeColor(piece, squareTo);
    }

    private boolean isOppositeColor(Piece piece, String square) {
        int file = getFile(square);
        int rank = getRank(square);
        return piece.getColor() != board[rank][file].getColor();
    }

    public boolean isEmpty(String square) {
        int file = getFile(square);
        int rank = getRank(square);
        return board[rank][file] == null;
    }


    public Piece get(String square) {
        int file = getFile(square);
        int rank = getRank(square);
        return board[rank][file];
    }

    public boolean isClearPath(String squareFrom, String squareTo) {
        int startFile = getFile(squareFrom);
        int startRank = getRank(squareFrom);
        int endFile = getFile(squareTo);
        int endRank = getRank(squareTo);
        int directionFile = (endFile - startFile > 0) ? 1 : -1;
        int directionRank = (endRank - startRank > 0) ? 1 : -1;

        // To count only squares between squareFrom and squareTo [excluding squareFrom and squareTo] use do-while loop
        endFile -= directionFile;
        endRank -= directionRank;
        while (startFile != endFile && startRank != endRank) {
            startFile += directionFile;
            startRank += directionRank;
            if (!isEmpty(startFile, startRank)) {
                return false;
            }
        }

        return true;
    }

    private boolean isEmpty(int file, int rank) {
        return board[rank][file] == null;
    }

    public void clear() {
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                board[rank][file] = null;
            }
        }
    }

    /**
     * Iterates over entire board to check if piece is attacked on squareTo.
     */
    public boolean isAttacked(Piece piece, String squareTo) {
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                if (isEmpty(file, rank)) { continue; }

                Piece current = board[rank][file];
                String squareFrom = toSquare(file, rank);
                if (current != piece && current.isValidMove(this, squareFrom, squareTo))
                    { return true; }
            }
        }
        return false;
    }

    private String toSquare(int file, int rank) {
        char letter = (char) (file + 'a');
        char number = (char) (rank+1);
        return "" + letter + number;
    }
}
