package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;
import chess.console.Square;

import java.util.Collection;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValidPieceMove(Board board, Square squareFrom, Square squareTo) {
        return MoveCalculator.isBishopMove(board,squareFrom, squareTo)
                || MoveCalculator.isRookMove(board, squareFrom, squareTo);
    }

    @Override
    public String toString() { return color == Color.WHITE ? "Q" : "q"; }

    @Override
    public Collection<Square> getValidPieceMoves(Board board, Square squareFrom) {
        int[][] moveDirections = new int[][] {{1,1}, {1,-1}, {-1,1}, {-1,-1}, // diagonal
                                              {1,0}, {-1,0}, {0,1}, {0,-1}};  // horizontal and vertical
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
    }

}
