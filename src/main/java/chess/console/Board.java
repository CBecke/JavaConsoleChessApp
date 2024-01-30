package chess.console;

import chess.console.pieces.*;
import chess.console.pieces.pawn.BlackPawn;
import chess.console.pieces.pawn.Pawn;
import chess.console.pieces.pawn.WhitePawn;

import java.util.*;

public class Board implements Iterable<String> {

    // board[7][0] is a1 and board[7][7] is a8
    Piece[][] board;

    public static int SIZE = 8;

    public Board() {
        board = new Piece[SIZE][SIZE];
    }

    public static char getFirstFile() { return 'a'; }
    public static char getLastFile() { return (char)(getFirstFile() + (Board.SIZE - 1)); }

    public void put(Piece piece, String square) {
        int file = getFile(square);
        int rank = getRank(square);
        board[rank][file] = piece;
    }

    private static int getFile(String square) {
        return square.charAt(0) - getFirstFile();
    }

    private static int getRank(String square) {
        return square.charAt(1) - '1'; // subtract '1' since board is 0-indexed
    }

    public boolean isWithinBoard(String square) {
        return square.length() == 2
                && 'a' <= square.charAt(0) && square.charAt(0) <= 'a' + Board.SIZE
                && '1' <= square.charAt(1) && square.charAt(1) <= '1' + Board.SIZE;
    }

    // TODO: check that the move does not put your king in check
    public boolean move(String squareFrom, String squareTo) {
        Piece piece = get(squareFrom);
        if (!canGoTo(piece, squareTo)
                || isStillStandingMove(squareFrom, squareTo)
                || !piece.isValidMove(this, squareFrom, squareTo))
            { return false; }

        if (isRook(piece)) { ((Rook)piece).disableCastling(); }
        if (isKing(piece)) { ((King)piece).disableCastling(); }
        movePiece(squareFrom, squareTo);

        // If this point is reached, the king could castle, so we can "manually" move the rook
        if (isCastles(piece, squareFrom, squareTo)) {
            doRookCastles(squareFrom, squareTo);
        }

        // Promotion
        if (isPawn(piece) && isEdgeRankMove(squareTo)) { put(new Queen(piece.getColor()), squareTo); }

        return true;
    }

    private boolean isEdgeRankMove(String square) {
        int rank = getRank(square);
        return rank == 0 || rank == Board.SIZE - 1;
    }

    private boolean isPawn(Piece piece) { return piece instanceof Pawn; }

    private boolean isRook(Piece piece) { return piece instanceof Rook; }

    private boolean isKing(Piece piece) { return piece instanceof King; }

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

    private boolean isStillStandingMove(String squareFrom, String squareTo){
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
        Collection<String> path = getPath(squareFrom, squareTo);
        for (String square : path) {
            if (!isEmpty(square)) { return false; }
        }
        return true;
    }

    /**
     * Iterates over entire board to check if piece is attacked on squareTo.
     */
    public boolean isAttacked(Color color, String square) {
        for (String squareFrom : this) {
            if (isEmpty(squareFrom)) { continue; }
            Piece current = get(squareFrom);

            // If an opposite color piece can move to squareTo, then squareTo is attacked by that piece
            if (current.getColor() != color && current.isValidMove(this, squareFrom, square))
            { return true; }
        }

        return false;
    }

    public String toSquare(int file, int rank) {
        char letter = (char) (file + getFirstFile());
        char number = (char)(rank+1+'0');
        return "" + letter + number;
    }

    public void clear() {
        for (String square : this) {
            put(null, square);
        }
    }

    /**
     * Checks if the squares between (and excluding both) squareFrom and squareTo are attacked.
     */
    public boolean isAttackedPath(Piece piece, String squareFrom, String squareTo) {
        Collection<String> path = getPath(squareFrom, squareTo);
        for (String square : path) {
            if (isAttacked(piece.getColor(), square)) { return true; }
        }
        return false;
    }

    /**
     * Gets the squares between (and excluding both) squareFrom and squareTo. Only works for straight diagonal,
     * horizontal, or vertical paths. E.g. getPath("a1", "d4") = {"b2", "c3"}.
     */
    public Collection<String> getPath(String squareFrom, String squareTo) {
        int directionFile = Integer.compare(getFile(squareTo), getFile(squareFrom));
        int directionRank = Integer.compare(getRank(squareTo), getRank(squareFrom));
        // start from the square in the path next to squareFrom
        String current = shiftSquare(squareFrom, directionFile, directionRank);

        Collection<String> path = new LinkedList<>();
        while (!current.equals(squareTo)) {
            path.add(current);
            int nextFile = getFile(current) + directionFile;
            int nextRank = getRank(current) + directionRank;
            current = toSquare(nextFile, nextRank);
        }
        return path;
    }

    public void setInitialPosition() {
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
        return isWithinBoard(squareFrom) && !isEmpty(squareFrom) && get(squareFrom).getColor() == player.getColor();
    }

    public Collection<String> getKingPositions() {
        Collection<String> kingSquares = new LinkedList<>();
        for (String square : this) {
            if (isKing(get(square))) { kingSquares.add(square); }
        }

        return kingSquares;
    }

    public Collection<String> getValidMoves(String squareFrom) {
        Piece piece = get(squareFrom);
        if (piece == null) { return new LinkedList<>(); }
        return piece.getValidMoves(this, squareFrom);
    }

    /**
     * Gets the square one would reach by moving fileShift to the side and rankShift up/down from square.
     * E.g. shiftSquare("a4", 4, -1) = "e3"
     */
    public String shiftSquare(String square, int fileShift, int rankShift) {
        return toSquare(getFile(square) + fileShift
                     , getRank(square) + rankShift);
    }

    /**
     * Returns true if a move exists such that the given square is no longer attacked, and false otherwise. The moving
     * piece must have the same color as the piece on the square to be defended.
     * @param square: assumed not to be empty.
     */
    public boolean canBeDefended(String square) {
        // get pieces (through their squares) that attack the given square
        Collection<String> attackerSquares = getAttackers(square);

        // get squares which can block the attack (potentially by capturing the attacking piece)
        Collection<String> squaresToDefend = getSquaresToDefend(attackerSquares, square);

        // test if the attack can be stopped by moving one of your pieces. If ANY one move which reaches one of the
        // blocking squares does not prevent the given square from being attacked that should be sufficient proof that
        // the square cannot be defended - it implies that at least 2 pieces are attacking the king in the current position.
        Color color = get(square).getColor();

        for (String squareFrom : this) {
            if (isEmpty(squareFrom)) { continue; }

            Piece current = get(square);
            for (String squareTo : squaresToDefend) {
                if (current.isValidMove(this, squareFrom, squareTo)) {
                    // pretend making the move. If the king is still under attack then the king cannot be defended.
                    movePiece(squareFrom, squareTo);
                    boolean canBeDefended = !isAttacked(color, square);

                    // reset the "fake" move
                    movePiece(squareTo, squareFrom);
                    return canBeDefended;
                }
            }
        }

        return false;
    }

    /**
     * Gets the squares which either have pieces attacking the given square,
     * or which are between the attacker and the square and can block the attack.
     * @return: A HashSet of squares that can block an attack, including capturing the attacker.
     */
    private Collection<String> getSquaresToDefend(Collection<String> attackerSquares, String square) {
        Collection<String> squaresToDefend = new HashSet<>(attackerSquares);
        for (String attackerSquare : attackerSquares) {
            Piece attacker = get(attackerSquare);
            if (attacker instanceof Knight) { continue; }
            squaresToDefend.addAll(getPath(attackerSquare, square));
        }

        return squaresToDefend;
    }

    /**
     * Gets the squares of pieces that attack the given square.
     */
    private Collection<String> getAttackers(String square) {
        Collection<String> attackingSquares = new LinkedList<>();
        for (String currentSquare : this) {
            if (canAttack(currentSquare, square)) { attackingSquares.add(currentSquare); }
        }

        return attackingSquares;
    }

    private boolean canAttack(String squareFrom, String squareTo) {
        Piece piece = get(squareFrom);
        return !isEmpty(squareFrom)
                && (!isEmpty(squareTo)
                    || piece.getColor() != get(squareTo).getColor())
                && get(squareFrom).isValidMove(this, squareFrom, squareTo);
    }

    public boolean isCheckmate(String kingSquare) {
        Color color = get(kingSquare).getColor();
        return isAttacked(color, kingSquare)
                && !canBeDefended(kingSquare)
                && getValidMoves(kingSquare).isEmpty();
    }


    /**
     * An iterator for the (String) squares of the board
     */
    @Override
    public Iterator<String> iterator() {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<String> {
        private char currentFile = 'a';
        private int currentRank = 1;
        @Override
        public boolean hasNext() {
            return currentFile <= 'h' && currentRank <= Board.SIZE;
        }

        @Override
        public String next() {
            String square = "" + currentFile + currentRank;
            if (currentFile == 'h') {
                currentFile = 'a';
                currentRank++;
            } else {
                currentFile++;
            }
            return square;
        }
    }
}
