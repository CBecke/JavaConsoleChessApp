package chess.console;

import chess.console.pieces.*;
import chess.console.pieces.pawn.BlackPawn;
import chess.console.pieces.pawn.Pawn;
import chess.console.pieces.pawn.WhitePawn;

import java.util.*;

public class Board implements Iterable<Square> {

    // board[7][0] is a1 and board[7][7] is a8
    Piece[][] board;

    public static int size = 8;
    public static char firstFile = 'a';
    public static char lastFile = 'h';
    public static int firstRank = 1;
    public static int lastRank = 8;
    private boolean moveWasCapture = false;
    private Piece lastCaptured;

    public Board() {
        board = new Piece[size][size];
        setInitialPosition();
    }

    private void put(Piece piece, Square square) {
        int file = fileToInt(square.getCharFile());
        int rank = square.getRank() - firstRank;
        board[rank][file] = piece;
    }

    private int fileToInt(char file) {
        return file - firstFile;
    }

    public boolean isWithinBoard(Square square) {
        return firstFile <= square.getCharFile() && square.getCharFile() <= lastFile
                && firstRank <= square.getRank() && square.getRank() <= lastRank;
    }

    public boolean move(Square squareFrom, Square squareTo) {
        Piece piece = get(squareFrom);
        if (!isLegalMove(squareFrom, squareTo)) { return false; }

        if (isRook(piece)) { ((Rook)piece).disableCastling(); }
        if (isKing(piece)) { ((King)piece).disableCastling(); }


        moveWasCapture = !isEmpty(squareTo);
        if (moveWasCapture) { lastCaptured = get(squareTo); }

        movePiece(squareFrom, squareTo);

        // If this point is reached, the king could castle, so we can "manually" move the rook
        if (isCastles(squareFrom, squareTo)) { doRookCastles(squareFrom, squareTo); }

        // Promotion
        if (isPawn(piece) && isEdgeRankMove(squareTo)) { put(new Queen(piece.getColor()), squareTo); }

        return true;
    }

    public boolean isLegalMove(Square squareFrom, Square squareTo) {
        return !isEmpty(squareFrom)
                && get(squareFrom).isPseudoLegalMove(this, squareFrom, squareTo)
                && !putsOwnKingInCheck(squareFrom, squareTo);
    }

    /**
     * Tests if moving the piece from squareFrom to squareTo puts the king of the same color in check.
     */
    public boolean putsOwnKingInCheck(Square squareFrom, Square squareTo) {
        Piece mover = get(squareFrom);
        Piece target = get(squareTo);
        Color moverColor = mover.getColor();
        Square kingSquare = getKingSquare(moverColor);

        // the king is the moving piece
        if (kingSquare.equals(squareFrom)) { kingSquare = squareTo; }

        // make "artificial" move
        put(mover, squareTo);
        put(null, squareFrom);

        boolean isInCheck = isAttacked(moverColor, kingSquare);

        // reset position
        put(mover, squareFrom);
        put(target, squareTo);

        return isInCheck;
    }

    private boolean isEdgeRankMove(Square square) {
        int rank = square.getRank();
        return rank == firstRank || rank == lastRank;
    }

    private boolean isPawn(Piece piece) { return piece instanceof Pawn; }

    private boolean isRook(Piece piece) { return piece instanceof Rook; }

    private boolean isKing(Piece piece) { return piece instanceof King; }

    private void doRookCastles(Square squareFrom, Square squareTo) {
        // set square where rook is coming from
        char cornerFile = squareTo.getCharFile() < squareFrom.getCharFile() ? firstFile : lastFile;
        Square cornerSquare = new Square(cornerFile, squareTo.getRank());

        // set destination square based on the king's move
        char fileTo = (char)(squareFrom.getCharFile() + ((squareFrom.getCharFile() < squareTo.getCharFile()) ? 1 : -1));
        int rankTo = squareFrom.getRank();
        Square destinationSquare = new Square(fileTo, rankTo);

        movePiece(cornerSquare, destinationSquare);
    }

    /**
     * Moves the piece at squareFrom to squareTo with NO safety checks.
     */
    private void movePiece(Square squareFrom, Square squareTo) {
        int fileFrom = fileToInt(squareFrom.getCharFile());
        int rankFrom = squareFrom.getRank() - firstRank;
        int fileTo = fileToInt(squareTo.getCharFile());
        int rankTo = squareTo.getRank() - firstRank;

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    public boolean isCastles(Square squareFrom, Square squareTo) {
        return get(squareFrom) instanceof King
                && Square.absFileDiff(squareFrom, squareTo) == 2;
    }

    public boolean isEmpty(Square square) {
        return get(square) == null;
    }


    public Piece get(Square square) {
        int file = square.getCharFile() - firstFile;
        int rank = square.getRank() - firstRank;
        return board[rank][file];
    }

    public boolean isClearPath(Square squareFrom, Square squareTo) {
        Collection<Square> path = getPath(squareFrom, squareTo);
        for (Square square : path) {
            if (!isEmpty(square)) { return false; }
        }
        return true;
    }

    /**
     * Determines if the given square is attacked by the opposite color of the given one. That is, if the given color is
     * white, the method returns true if a black piece can (pseudo-legally) move to square
     * @param color the color which may be attacked on square
     * @param square the square which is potentially targetted by an attack
     * @return true if the opposite color to the given one has a piece that can move to square, and false otherwise.
     */
    public boolean isAttacked(Color color, Square square) {
        Color oppositeColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        for (Square squareFrom : this) {
            Piece potentialAttacker = get(squareFrom);
            if (!isEmpty(squareFrom)
                    && potentialAttacker.getColor() == oppositeColor
                    && potentialAttacker.isPseudoLegalMove(this, squareFrom, square)) // pseudo-legal is sufficient
                { return true; }
        }
        /*
        // TODO: DELETE
        Pawn pawn = (color == Color.WHITE) ? new BlackPawn() : new WhitePawn(); // pawn of opposite color of given color
        Piece[] pieceTypes = new Piece[]
                {pawn, new Queen(oppositeColor), new Rook(oppositeColor), new Bishop(oppositeColor), new Knight(oppositeColor), new King(oppositeColor)};

        for (Piece piece : pieceTypes) {
            // intentionally iterate over PSEUDO-legal moves; this is sufficient.
            for (Square pseudoMoveSquare : piece.getPseudoLegalPieceMoves(this, square)) {
                Piece potentialAttacker = get(pseudoMoveSquare);
                if (!isEmpty(pseudoMoveSquare)
                        && potentialAttacker.getColor() == oppositeColor
                        && get(pseudoMoveSquare).getClass() == piece.getClass())
                    { return true; }
            }
        }
        */

        return false;
    }

    public void clear() {
        for (Square square : this) {
            put(null, square);
        }
    }

    /**
     * Checks if the squares between (and excluding both) squareFrom and squareTo are attacked.
     */
    public boolean isAttackedPath(Piece piece, Square squareFrom, Square squareTo) {
        Collection<Square> path = getPath(squareFrom, squareTo);
        for (Square square : path) {
            if (isAttacked(piece.getColor(), square)) { return true; }
        }
        return false;
    }

    /**
     * Gets the squares between (and excluding both) squareFrom and squareTo. Only works for straight diagonal,
     * horizontal, or vertical paths. E.g. getPath("a1", "d4") = {"b2", "c3"}.
     */
    public Collection<Square> getPath(Square squareFrom, Square squareTo) {
        int directionFile = Integer.compare(squareTo.getCharFile(), squareFrom.getCharFile());
        int directionRank = Integer.compare(squareTo.getRank(), squareFrom.getRank());
        // start from the square in the path next to squareFrom
        Square current = squareFrom.shift(directionFile, directionRank);

        Collection<Square> path = new LinkedList<>();
        while (!current.equals(squareTo)) {
            path.add(current);
            current = current.shift(directionFile, directionRank);
        }
        return path;
    }

    public void setInitialPosition() {
        // remove previous pieces
        clear();

        // Set first rank [white pieces]
        put(new Rook(Color.WHITE), new Square("a1"));
        put(new Rook(Color.WHITE), new Square("h1"));
        put(new Knight(Color.WHITE), new Square("b1"));
        put(new Knight(Color.WHITE), new Square("g1"));
        put(new Bishop(Color.WHITE), new Square("c1"));
        put(new Bishop(Color.WHITE), new Square("f1"));
        put(new Queen(Color.WHITE), new Square("d1"));
        put(new King(Color.WHITE), new Square("e1"));

        // set last rank [black pieces]
        put(new Rook(Color.BLACK), new Square("a8"));
        put(new Rook(Color.BLACK), new Square("h8"));
        put(new Knight(Color.BLACK), new Square("b8"));
        put(new Knight(Color.BLACK), new Square("g8"));
        put(new Bishop(Color.BLACK), new Square("c8"));
        put(new Bishop(Color.BLACK), new Square("f8"));
        put(new Queen(Color.BLACK), new Square("d8"));
        put(new King(Color.BLACK), new Square("e8"));

        // set pawns
        for (char file = 'a'; file <= 'h'; file++) {
            put(new WhitePawn(), new Square(file, 2));
            put(new BlackPawn(), new Square(file, 7));
        }
    }

    public boolean isValidSquareFrom(Player player, Square squareFrom) {
        return isWithinBoard(squareFrom)
                && !isEmpty(squareFrom)
                && get(squareFrom).getColor() == player.getColor();
    }

    public Collection<Square> getKingPositions() {
        Collection<Square> kingSquares = new HashSet<>();
        for (Square square : this) {
            if (isKing(get(square))) { kingSquares.add(square); }
        }
        return kingSquares;
    }

    /**
     * Returns true if a move exists such that the given square is no longer attacked, and false otherwise. The moving
     * piece must have the same color as the piece on the square to be defended.
     * @param square assumed not to be empty.
     */
    public boolean canBeDefended(Square square) {
        // get pieces (through their squares) that attack the given square
        Collection<Square> attackerSquares = getAttackers(square);

        // get squares which can block the attack (potentially by capturing the attacking piece)
        Collection<Square> squaresToDefend = getSquaresToDefend(attackerSquares, square);

        // test if the attack can be stopped by moving one of your pieces. If ANY one move which reaches one of the
        // blocking squares does not prevent the given square from being attacked that should be sufficient proof that
        // the square cannot be defended - it implies that at least 2 pieces are attacking the king in the current position.
        Color color = get(square).getColor();

        for (Square squareFrom : this) {
            if (isEmpty(squareFrom)) { continue; }

            for (Square squareTo : squaresToDefend) {
                if (isLegalMove(squareFrom, squareTo)) {
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
    private Collection<Square> getSquaresToDefend(Collection<Square> attackerSquares, Square square) {
        Collection<Square> squaresToDefend = new HashSet<>(attackerSquares);
        for (Square attackerSquare : attackerSquares) {
            Piece attacker = get(attackerSquare);
            if (attacker instanceof Knight) { continue; }
            squaresToDefend.addAll(getPath(attackerSquare, square));
        }

        return squaresToDefend;
    }

    /**
     * Gets the squares of pieces that attack the given square.
     */
    private Collection<Square> getAttackers(Square square) {
        Collection<Square> attackingSquares = new HashSet<>();
        for (Square currentSquare : this) {
            if (canAttack(currentSquare, square)) { attackingSquares.add(currentSquare); }
        }

        return attackingSquares;
    }

    private boolean canAttack(Square squareFrom, Square squareTo) {
        Piece piece = get(squareFrom);
        return !isEmpty(squareFrom)
                && (!isEmpty(squareTo)
                    || piece.getColor() != get(squareTo).getColor())
                && isLegalMove(squareFrom, squareTo);
    }

    public boolean isCheckmate(Square kingSquare) {
        Color color = get(kingSquare).getColor();

        return isAttacked(color, kingSquare)
                && !canBeDefended(kingSquare)
                && !hasKingMove(kingSquare);
    }

    public boolean hasKingMove(Square kingSquare) {
        King king = (King)get(kingSquare);
        for (Square pseudoMoveSquare : king.getPseudoLegalPieceMoves(this, kingSquare)) {
            if (!isAttacked(king.getColor(), pseudoMoveSquare))
                { return true; }
        }

        return false;
    }

    /**
     * An iterator for the squares of the board
     */
    @Override
    public Iterator<Square> iterator() {
        return new BoardIterator();
    }

    public boolean isEdgeRank(Square square) {
        int rank = square.getRank();
        return rank == firstRank || rank == lastRank;
    }

    public boolean wasCapture() {
        return moveWasCapture;
    }

    /**
     * returns the square of the king which has opposite color of the input color
     */
    public Square getKingSquare(Color color) {
        Collection<Square> kingSquares = getKingPositions();
        Iterator<Square> iterator = kingSquares.iterator();
        Square kingSquare = iterator.next();
        return (get(kingSquare).getColor() == color) ? kingSquare : iterator.next();
    }

    public Piece getLastCaptured() {
        return lastCaptured;
    }

    private class BoardIterator implements Iterator<Square> {
        private char currentFile = firstFile;
        private int currentRank = firstRank;
        @Override
        public boolean hasNext() {
            return currentFile <= lastFile && currentRank <= lastRank;
        }

        @Override
        public Square next() {
            Square square = new Square(currentFile, currentRank);
            if (currentFile == lastFile) {
                currentFile = firstFile;
                currentRank++;
            } else {
                currentFile++;
            }
            return square;
        }
    }
}
