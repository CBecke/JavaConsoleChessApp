package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {
    private boolean canCastle = true;

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return (isHorizontalMove(squareFrom, squareTo)
                || isVerticalMove(squareFrom, squareTo))
                    && board.isClearPath(squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "R" : "r";
    }

    @Override
    public Collection<String> getValidMoves(Board board, String squareFrom) {
        int[][] moveDirections = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
    }


    private boolean isVerticalMove(String squareFrom, String squareTo) {
        return (squareFrom.charAt(0) == squareTo.charAt(0)) && (squareFrom.charAt(1) != squareTo.charAt(1));
    }

    private boolean isHorizontalMove(String squareFrom, String squareTo) {
        return (squareFrom.charAt(0) != squareTo.charAt(0)) && (squareFrom.charAt(1) == squareTo.charAt(1));
    }

    public boolean canCastle() { return canCastle; }

    public void disableCastling() { canCastle = false; }
}
