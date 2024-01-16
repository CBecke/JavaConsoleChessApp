package chess.console;

public class Board {

    // board[7][0] is a1
    Knight[][] board;

    public Board() {
        board = new Knight[8][8];
    }

    public void put(Knight knight, String position) {
        int file = position.charAt(0) - 'a';
        int rank = position.charAt(1) - '1'; // subtract '1' since board is 0-indexed

        board[rank][file] = knight;
    }

    public void move(String posFrom, String posTo) {
        int fileFrom = posFrom.charAt(0) - 'a';
        int rankFrom = posFrom.charAt(1) - '1'; // subtract '1' since board is 0-indexed
        int fileTo   = posTo.charAt(0) - 'a';
        int rankTo   = posTo.charAt(1) - '1';   // subtract '1' since board is 0-indexed

        board[rankTo][fileTo] = board[rankFrom][fileFrom];
        board[rankFrom][fileFrom] = null;
    }

    public Knight get(String position) {
        int file = position.charAt(0) - 'a';
        int rank = position.charAt(1) - '1'; // subtract 1 because board is 0-indexed
        return board[rank][file];
    }
}
