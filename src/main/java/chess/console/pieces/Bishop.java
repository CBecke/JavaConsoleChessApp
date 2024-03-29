package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;
import chess.console.Square;

import java.util.*;

public class Bishop extends Piece {
    public Bishop(Color color) { super(color); }

    @Override
    public boolean isPseudoLegalPieceMove(Board board, Square squareFrom, Square squareTo) {
        return MoveCalculator.isBishopMove(board, squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "B" : "b";
    }

    @Override
    public Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom) {
        int[][] moveDirections = new int[][] {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        return MoveCalculator.getPseudoLegalMovesInDirections(board, squareFrom, color, moveDirections);
    }

}
