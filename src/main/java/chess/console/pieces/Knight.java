package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) { super(color); }

    @Override
    public boolean isValidMove(Board board, Square squareFrom, Square squareTo) {
        int fileDiff = Square.absFileDiff(squareFrom, squareTo);
        int rankDiff = Square.absRankDiff(squareFrom, squareTo);
        return (fileDiff == 1 && rankDiff == 2)
                || (fileDiff == 2 && rankDiff == 1);
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "N" : "n";
    }

    @Override
    public Collection<Square> getValidMoves(Board board, Square squareFrom) {
        Collection<Square> validMoves = new LinkedList<>();

        Square squareTo;
        int[][] moveShiftPermutations = {{-1,2}, {-1,-2}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {2,1}, {2,-1}};

        for (int[] move : moveShiftPermutations) {
            squareTo = squareFrom.shift(move[0], move[1]);
            if (canMoveTo(board, squareTo)) { validMoves.add(squareTo); }
        }

        return validMoves;
    }

    private boolean canMoveTo(Board board, Square squareTo) {
        return board.isWithinBoard(squareTo)
                && (board.isEmpty(squareTo)
                    || board.get(squareTo).getColor() != color);
    }
}
