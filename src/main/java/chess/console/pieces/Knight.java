package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) { super(color); }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return (fileDiff == 1 && rankDiff == 2)
                || (fileDiff == 2 && rankDiff == 1);
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "N" : "n";
    }

    @Override
    public Collection<String> getValidMoves(Board board, String squareFrom) {
        List<String> validMoves = new LinkedList<>();

        String squareTo;
        int[][] moveShiftPermutations = {{-1,2}, {-1,-2}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {2,1}, {2,-1}};

        for (int[] move : moveShiftPermutations) {
            squareTo = board.shiftSquare(squareFrom, move[0], move[1]);
            if (canMoveTo(board, squareTo)) { validMoves.add(squareTo); }
        }

        return validMoves;
    }

    private boolean canMoveTo(Board board, String squareTo) {
        return board.isWithinBoard(squareTo)
                && (board.isEmpty(squareTo)
                    || board.get(squareTo).getColor() != color);
    }
}
