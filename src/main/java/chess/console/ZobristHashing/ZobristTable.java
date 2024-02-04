package chess.console.ZobristHashing;

import chess.console.Board;
import chess.console.Color;
import chess.console.pieces.*;
import chess.console.pieces.pawn.Pawn;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;

public class ZobristTable implements ZobristHashing {
    private long hashValue;
    private final SecureRandom random;
    private long[][][] table;
    private long blackToMove;

    public ZobristTable(Board board, boolean isBlackToMove) {
        random = new SecureRandom();
        initZobrist();
        hashValue = generateHash(board, isBlackToMove);
    }

    private long generateHash(Board board, boolean isBlackToMove) {
        long h = (isBlackToMove) ? blackToMove : 0L;
        for (String square : board) {
            if (board.isEmpty(square)) { continue; }
            long currentHash = getTableHash(square, board.get(square));
            h = h ^ currentHash; // '^' is the XOR operator
        }
        return h;
    }

    private long getTableHash(String square, Piece piece) {
        int pieceValue = getPieceValue(piece);
        int rank = Board.getRank(square);
        int file = Board.getFile(square);
        return table[rank][file][pieceValue];
    }

    private int getPieceValue(Piece piece) {
        int pieceValue = Integer.MIN_VALUE;
        // While I realize this is ugly, I do not want to put pieceValue as an attribute of the Piece class because
        // conceptually it is not part of the piece, it is just a value needed by the zobrist table. Another option I
        // considered was a hashmap but upon implementing it, I found it obscured the value of a piece too much, despite
        // looking neater at first glance.. So I stuck with this.
        if (piece instanceof Pawn) { pieceValue = 0; }
        else if (piece instanceof Knight) { pieceValue = 1; }
        else if (piece instanceof Bishop) { pieceValue = 2; }
        else if (piece instanceof Queen) { pieceValue = 3; }
        else if (piece instanceof King) { pieceValue = ((King)piece).canCastle() ? 4 : 5; }
        else if (piece instanceof Rook) { pieceValue = ((Rook)piece).canCastle() ? 6 : 7; }

        if (piece.getColor() == Color.BLACK) { pieceValue += 8; }
        return pieceValue;
    }

    @Override
    public void initZobrist() {
        // based on: https://en.wikipedia.org/wiki/Zobrist_hashing#:~:text=As%20an%20example,hash.%5B1%5D
        // 6 white pieces + 6 black pieces + 2*2 pieces that can castle [black/white king + rook]
        int pieceCount = 16;
        table = new long[Board.SIZE][Board.SIZE][pieceCount];
        blackToMove = generateRandomKey();

        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                for (int piece = 0; piece < pieceCount; piece++) {
                    table[rank][file][piece] = generateRandomKey();
                }
            }
        }
    }


    @Override
    public long generateRandomKey() {
        /* since SecureRandom.nextLong() only has a random seed of 48 bits, using it alone does not cover the entire
         * long (64 bit) range. To make up for this, we call SecureRandom.nextLong() twice and concatenate the upper 32
         * bits from the first random value with the lower 32 bits from the second random value.
         */
        return (random.nextLong() << 32) | (random.nextLong() & 0xffffffffL);
    }

    @Override
    public void updateHash(Board board) {

    }

    @Override
    public long getHash() {
        return 0;
    }
}
