package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;
import chess.console.Square;

import java.util.Collection;

public class Rook extends Piece {
    private boolean canCastle = true;

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isValidPieceMove(Board board, Square squareFrom, Square squareTo) {
        return MoveCalculator.isRookMove(board, squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "R" : "r";
    }

    @Override
    public Collection<Square> getValidPieceMoves(Board board, Square squareFrom) {
        int[][] moveDirections = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
    }

    public boolean canCastle() { return canCastle; }

    public void disableCastling() { canCastle = false; }
}
