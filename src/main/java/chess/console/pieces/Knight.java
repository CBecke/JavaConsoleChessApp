package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {

    public Knight(Color color) { super(color); }

    @Override
    public boolean isLegalPieceMove(Board board, Square squareFrom, Square squareTo) {
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
    public Set<Square> getLegalPieceMoves(Board board, Square squareFrom) {
    }

    @Override
    public Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom) {
        Set<Square> validMoves = new HashSet<>();

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
