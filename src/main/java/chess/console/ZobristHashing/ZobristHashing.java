package chess.console.ZobristHashing;

import chess.console.Board;

/**
 * Zobrist hashing to assign unique values to chess positions w.h.p., as described
 * <a href="https://www.chessprogramming.org/Zobrist_Hashing">here</a> and
 * <a href="https://en.wikipedia.org/wiki/Zobrist_hashing">here</a>.
 */
public interface ZobristHashing {
    public void initZobrist();

    public long generateRandomKey();



    public void updateHash(Board board);

    public long getHash();
}
