package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

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
    public List<String> getValidMoves(Board board, String squareFrom) {
        // get right moves
        List<String> validMoves = new LinkedList<>(getValidMovesInDirection(board, squareFrom, 1, 0));
        // get left moves
        validMoves.addAll(getValidMovesInDirection(board, squareFrom, -1, 0));
        // get up moves
        validMoves.addAll(getValidMovesInDirection(board, squareFrom, 0, 1));
        // get down moves
        validMoves.addAll(getValidMovesInDirection(board, squareFrom, 0, -1));

        return validMoves;
    }

    private List<String> getValidMovesInDirection(Board board, String squareFrom, int fileDirection, int rankDirection) {
        List<String> validMoves = new LinkedList<>();
        int fileShift = fileDirection;
        int rankShift = rankDirection;
        String currentSquare = board.shiftSquare(squareFrom, fileShift, rankShift);

        boolean hasCaptured = false;
        while (board.isWithinBoard(currentSquare) && (board.isEmpty(currentSquare) || !hasCaptured)) {
            validMoves.add(currentSquare);

            // The bishop can capture the first opposite color piece in its path, assuming the path was clear
            if (!board.isEmpty(currentSquare) && board.get(currentSquare).getColor() != getColor())
            { hasCaptured = true; }

            fileShift += fileDirection;
            rankShift += rankDirection;
            currentSquare = board.shiftSquare(squareFrom, fileShift, rankShift);
        }

        return validMoves;
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
