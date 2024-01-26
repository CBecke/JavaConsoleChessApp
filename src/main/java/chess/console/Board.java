package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;
import chess.console.pieces.*;
import chess.console.pieces.pawn.BlackPawn;
import chess.console.pieces.pawn.Pawn;
import chess.console.pieces.pawn.WhitePawn;

import java.util.ArrayList;

public class Board {

    // board[7][0] is a1 and board[7][7] is a8
    Piece[][] board;

    public static int SIZE = 8;

    public Board() {
        board = new Piece[SIZE][SIZE];
    }

    public static char getFirstFile() { return 'a'; }
    public static char getLastFile() { return (char)(getFirstFile() + (Board.SIZE - 1)); }

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

    private static int getRank(String square) {
        return square.charAt(1) - '1'; // subtract '1' since board is 0-indexed
    }

    public boolean isWithinBoard(String square) {
        return square.length() == 2
                && 'a' <= square.charAt(0) && square.charAt(0) <= 'h'
                && '1' <= square.charAt(1) && square.charAt(1) <= '8';
    }

    public void move(String squareFrom, String squareTo) throws BoardOutOfBoundsException, IllegalMoveException {
        if (!isWithinBoard(squareTo)) { throw new BoardOutOfBoundsException(squareTo); }
        if (isEmpty(squareFrom)) { throw new IllegalMoveException("The square moved from is empty"); }

        Piece piece = get(squareFrom);
        if (!canGoTo(piece, squareTo)
                || isStillStandingMove(squareFrom, squareTo)
                || !piece.isValidMove(this, squareFrom, squareTo))
            { return; }

        if (isRookMove(piece)) { ((Rook)piece).disableCastling(); }
        if (isKingMove(piece)) { ((King)piece).disableCastling(); }
        movePiece(squareFrom, squareTo);

        // If this point is reached, the king could castle, so we can "manually" move the rook
        if (isCastles(piece, squareFrom, squareTo)) {
            doRookCastles(squareFrom, squareTo);
        }

        // Promotion
        if (isPawnMove(piece) && isEdgeRankMove(squareTo)) { put(new Queen(piece.getColor()), squareTo); }
    }

    private boolean isEdgeRankMove(String square) {
        int rank = getRank(square);
        return rank == 0 || rank == Board.SIZE - 1;
    }

    private boolean isPawnMove(Piece piece) { return piece instanceof Pawn; }

    private boolean isRookMove(Piece piece) { return piece instanceof Rook; }

    private boolean isKingMove(Piece piece) { return piece instanceof King; }

    private void doRookCastles(String squareFrom, String squareTo) {
        // set square where rook is coming from
        char cornerFile = squareTo.charAt(0) < squareFrom.charAt(0) ? getFirstFile() : getLastFile();
        String cornerSquare = "" + cornerFile + squareTo.charAt(1);

        // set destination square based on the king's move
        char fileTo = (char)(squareFrom.charAt(0) + ((squareFrom.charAt(0) < squareTo.charAt(0)) ? 1 : -1));
        char rankTo = squareFrom.charAt(1);
        String destinationSquare = "" + fileTo + rankTo;

        movePiece(cornerSquare, destinationSquare);
    }

    /**
     * Moves the piece at squareFrom to squareTo with NO safety checks.
     */
    private void movePiece(String squareFrom, String squareTo) {
        int fileFrom = getFile(squareFrom);
        int rankFrom = getRank(squareFrom);
        int fileTo = getFile(squareTo);
        int rankTo = getRank(squareTo);

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    private boolean isCastles(Piece piece, String squareFrom, String squareTo) {
        return piece instanceof King
                && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 2;
    }

    private boolean isStillStandingMove( String squareFrom, String squareTo){
        return squareFrom.equals(squareTo);
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
        ArrayList<String> path = getPath(squareFrom, squareTo);
        for (String square : path) {
            if (!isEmpty(square)) { return false; }
        }
        return true;
    }

    private boolean isEmpty(int file, int rank) {
        return board[rank][file] == null;
    }

    /**
     * Iterates over entire board to check if piece is attacked on squareTo.
     */
    public boolean isAttacked(Color color, String square) {
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                if (isEmpty(file, rank)) { continue; }
                Piece current = board[rank][file];
                String squareFrom = toSquare(file, rank);

                // If an opposite color piece can move to squareTo, then squareTo is attacked by that piece
                if (current.getColor() != color && current.isValidMove(this, squareFrom, square))
                    { return true; }
            }
        }
        return false;
    }

    private String toSquare(int file, int rank) {
        char letter = (char) (file + 'a');
        char number = (char)(rank+1+'0');
        return "" + letter + number;
    }

    public void clear() {
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                board[rank][file] = null;
            }
        }
    }

    /**
     * Checks if the squares between (and excluding both) squareFrom and squareTo are attacked.
     */
    public boolean isAttackedPath(Piece piece, String squareFrom, String squareTo) {
        ArrayList<String> path = getPath(squareFrom, squareTo);
        for (String square : path) {
            if (isAttacked(piece.getColor(), square)) { return true; }
        }
        return false;
    }

    /**
     * Gets the squares between (and excluding both) squareFrom and squareTo.
     * E.g. getPath("a1", "d4") = {"b2", "c3"}.
     */
    public ArrayList<String> getPath(String squareFrom, String squareTo) {
        int directionFile = Integer.compare(getFile(squareTo), getFile(squareFrom));
        int directionRank = Integer.compare(getRank(squareTo), getRank(squareFrom));
        // start from the square in the path next to squareFrom
        String current = toSquare(getFile(squareFrom) + directionFile
                                , getRank(squareFrom) + directionRank);

        ArrayList<String> path = new ArrayList<>();
        while (!current.equals(squareTo)) {
            path.add(current);
            int nextFile = getFile(current) + directionFile;
            int nextRank = getRank(current) + directionRank;
            current = toSquare(nextFile, nextRank);
        }

        return path;
    }

    public void setInitialPosition() throws BoardOutOfBoundsException {
        // remove previous pieces
        clear();

        // Set first rank [white pieces]
        put(new Rook(Color.WHITE), "a1");
        put(new Rook(Color.WHITE), "h1");
        put(new Knight(Color.WHITE), "b1");
        put(new Knight(Color.WHITE), "g1");
        put(new Bishop(Color.WHITE), "c1");
        put(new Bishop(Color.WHITE), "f1");
        put(new Queen(Color.WHITE), "d1");
        put(new King(Color.WHITE), "e1");

        // set last rank [black pieces]
        put(new Rook(Color.BLACK), "a8");
        put(new Rook(Color.BLACK), "h8");
        put(new Knight(Color.BLACK), "b8");
        put(new Knight(Color.BLACK), "g8");
        put(new Bishop(Color.BLACK), "c8");
        put(new Bishop(Color.BLACK), "f8");
        put(new Queen(Color.BLACK), "d8");
        put(new King(Color.BLACK), "e8");

        // set pawns
        for (char file = 'a'; file <= 'h'; file++) {
            put(new WhitePawn(), "" + file + '2');
            put(new BlackPawn(), "" + file + '7');
        }
    }

    public boolean isValidSquareFrom(Player player, String squareFrom) {
        return !isEmpty(squareFrom) && get(squareFrom).getColor() == player.getColor();
    }
}
