package chess.console;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to encapsulate shared move functionality
 */
public class MoveCalculator {

    // Prevent instantiation of the utility class with private visibility
    private MoveCalculator() {};

    public static Collection<String> getValidMovesInDirection(Board board, String squareFrom, int fileDirection, int rankDirection) {
        Collection<String> validMoves = new LinkedList<>();
        int fileShift = fileDirection;
        int rankShift = rankDirection;
        boolean hasCaptured = false;
        Color thisPieceColor = board.get(squareFrom).getColor();

        String currentSquare = board.shiftSquare(squareFrom, fileShift, rankShift);
        while (board.isWithinBoard(currentSquare) && (board.isEmpty(currentSquare) || !hasCaptured)) {
            validMoves.add(currentSquare);

            // The bishop can capture the first opposite color piece in its path, assuming the path was clear
            if (!board.isEmpty(currentSquare) && board.get(currentSquare).getColor() != thisPieceColor)
            { hasCaptured = true; }

            fileShift += fileDirection;
            rankShift += rankDirection;
            currentSquare = board.shiftSquare(squareFrom, fileShift, rankShift);
        }

        return validMoves;
    }

    /**
     * Computes the valid squares for the piece on squareFrom on the board to move to.
     * @param board: the chess board.
     * @param squareFrom: the square on board where the piece is currently.
     * @param moveDirections: an array of pairs where the first element is the file direction and the second is the rank direction.
     * @return the possible squares (as strings, e.g. "e4") to move to.
     */
    public static Collection<String> getValidMovesInDirections(Board board, String squareFrom, int[][] moveDirections) {
        Collection<String> validMoves = new LinkedList<>();

        for (int[] direction : moveDirections) {
            validMoves.addAll(MoveCalculator.getValidMovesInDirection(board, squareFrom, direction[0], direction[1]));
        }

        return validMoves;
    }

}
