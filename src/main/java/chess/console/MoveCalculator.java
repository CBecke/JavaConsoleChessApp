package chess.console;

import java.util.Collection;
import java.util.HashSet;

/**
 * Utility class to encapsulate shared move functionality
 */
public class MoveCalculator {

    // Prevent instantiation of the utility class with private visibility
    private MoveCalculator() {}

    public static Collection<Square> getValidMovesInDirection(Board board, Square squareFrom, int fileDirection, int rankDirection) {
        Collection<Square> validMoves = new HashSet<>();
        int fileShift = fileDirection;
        int rankShift = rankDirection;
        boolean hasCaptured = false;
        Color thisPieceColor = board.get(squareFrom).getColor();

        Square currentSquare = squareFrom.shift(fileShift, rankShift);
        while (board.isWithinBoard(currentSquare) && (board.isEmpty(currentSquare) || !hasCaptured)) {
            validMoves.add(currentSquare);

            // The piece can capture the first opposite color piece in its path, assuming the path was clear
            if (!board.isEmpty(currentSquare) && board.get(currentSquare).getColor() != thisPieceColor)
                { hasCaptured = true; }

            fileShift += fileDirection;
            rankShift += rankDirection;
            currentSquare = squareFrom.shift(fileShift, rankShift);
        }

        return validMoves;
    }

    /**
     * Computes the valid squares for the piece on squareFrom on the board to move to.
     * @param board: the chess board.
     * @param squareFrom: the square on board where the piece is currently.
     * @param moveDirections: an array of pairs where the first element is the file direction and the second is the rank direction.
     * @return the possible squares to move to.
     */
    public static Collection<Square> getValidMovesInDirections(Board board, Square squareFrom, int[][] moveDirections) {
        Collection<Square> validMoves = new HashSet<>();

        for (int[] direction : moveDirections) {
            validMoves.addAll(MoveCalculator.getValidMovesInDirection(board, squareFrom, direction[0], direction[1]));
        }

        return validMoves;
    }

    public static boolean isHorizontalMove(Square squareFrom, Square squareTo) {
        return (squareFrom.getCharFile() != squareTo.getCharFile()) && (squareFrom.getRank() == squareTo.getRank());
    }

    public static boolean isVerticalMove(Square squareFrom, Square squareTo) {
        return (squareFrom.getCharFile() == squareTo.getCharFile()) && (squareFrom.getRank() != squareTo.getRank());
    }


    public static boolean isBishopMove(Board board, Square squareFrom, Square squareTo) {
        int fileDiff = Square.absFileDiff(squareFrom, squareTo);
        int rankDiff = Square.absRankDiff(squareFrom, squareTo);
        return isDiagonalMove(fileDiff, rankDiff)
                && board.isClearPath(squareFrom, squareTo);
    }

    private static boolean isDiagonalMove(int fileDiff, int rankDiff) { return fileDiff == rankDiff; }

    public static boolean isRookMove(Board board, Square squareFrom, Square squareTo) {
        return (MoveCalculator.isHorizontalMove(squareFrom, squareTo)
                || MoveCalculator.isVerticalMove(squareFrom, squareTo))
                && board.isClearPath(squareFrom, squareTo);
    }
}
