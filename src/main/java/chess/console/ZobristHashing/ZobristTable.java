package chess.console.ZobristHashing;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;
import chess.console.pieces.*;
import chess.console.pieces.pawn.Pawn;

import java.security.SecureRandom;

/**
 * Zobrist hashing to assign unique values to chess positions w.h.p., as described
 * <a href="https://www.chessprogramming.org/Zobrist_Hashing">here</a> and
 * <a href="https://en.wikipedia.org/wiki/Zobrist_hashing">here</a>.
 */
public class ZobristTable {
    private long hashValue;
    private final SecureRandom random;
    private long[][][] table;
    private long blackToMove;

    /**
     * Creates a Zobrist hash table and a hash value corresponding to the standard initial chess position.
     */
    public ZobristTable() {
        random = new SecureRandom();
        initZobrist();
        Board board = new Board();
        hashValue = generateHash(board, false);
    }


    private long generateHash(Board board, boolean isBlackToMove) {
        long h = (isBlackToMove) ? blackToMove : 0L;
        for (Square square : board) {
            if (board.isEmpty(square)) { continue; }
            long currentHash = getTableHash(square, board.get(square));
            h = h ^ currentHash; // '^' is the XOR operator
        }
        return h;
    }

    private long getTableHash(Square square, Piece piece) {
        int rank = square.getRank() - Board.firstRank;
        int file = square.getCharFile() - Board.firstFile;
        int pieceValue = getPieceValue(piece);
        return table[rank][file][pieceValue];
    }

    private int getPieceValue(Piece piece) {
        int pieceValue = Integer.MIN_VALUE;

        if (piece instanceof Pawn) { pieceValue = 0; }
        else if (piece instanceof Knight) { pieceValue = 1; }
        else if (piece instanceof Bishop) { pieceValue = 2; }
        else if (piece instanceof Queen) { pieceValue = 3; }
        else if (piece instanceof King) { pieceValue = ((King)piece).canCastle() ? 4 : 5; }
        else if (piece instanceof Rook) { pieceValue = ((Rook)piece).canCastle() ? 6 : 7; }

        if (piece.getColor() == Color.BLACK) { pieceValue += 8; }
        return pieceValue;
    }

    public void initZobrist() {
        // based on: https://en.wikipedia.org/wiki/Zobrist_hashing#:~:text=As%20an%20example,hash.%5B1%5D
        // 6 white pieces + 6 black pieces + 2*2 pieces that can castle [black/white king + rook]
        int pieceCount = 16;
        table = new long[Board.size][Board.size][pieceCount];
        blackToMove = generateRandomKey();

        for (int rank = 0; rank < Board.size; rank++) {
            for (int file = 0; file < Board.size; file++) {
                for (int piece = 0; piece < pieceCount; piece++) {
                    table[rank][file][piece] = generateRandomKey();
                }
            }
        }
    }

    public long generateRandomKey() {
        /* since SecureRandom.nextLong() only has a random seed of 48 bits, using it alone does not cover the entire
         * (64 bit) long range. To make up for this, we call SecureRandom.nextLong() twice and concatenate the upper 32
         * bits from the first random value with the lower 32 bits from the second random value.
         */
        return (random.nextLong() << 32) | (random.nextLong() & 0xffffffffL);
    }


    /**
     * Updates the zobrist board hash value based on the most recent move. The method is called AFTER the piece has
     * moved.
     */
    public void updateHash(Board board, Square squareFrom, Square squareTo) {
        hashValue = hashValue ^ blackToMove; // take into account that the turn has changed
        if (board.wasCapture()) {
            Piece capturedPiece = board.getLastCaptured();
            hashValue = hashValue ^ getTableHash(squareTo, capturedPiece); // "remove" the captured piece from hash
        }

        // When this is called, the moving piece has already moved to squareTo
        Piece current = board.get(squareTo);
        hashValue = hashValue ^ getTableHash(squareFrom, current); // "remove" the piece from its previous square
        hashValue = hashValue ^ getTableHash(squareTo, current);   // "put" the piece on its new square
    }

    public long getHash() {
        return hashValue;
    }
}
