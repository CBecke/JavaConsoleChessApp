package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.pieces.Piece;
import chess.console.pieces.Rook;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece{
    private boolean canCastle = true;

    public King(Color color) { super(color); }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return !board.isAttacked(color, squareTo)
                && ((fileDiff <= 1 && rankDiff <= 1) || isValidCastles(board, squareFrom, squareTo)) ;
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "K" : "k";
    }

    @Override
    public Collection<String> getValidMoves(Board board, String squareFrom) {
        Collection<String> validMoves = new LinkedList<>();
        // iterate over neighboring squares (including diagonally neighboring)
        for (int rankShift = -1; rankShift <= 1; rankShift++) {
            for (int fileShift = -1; fileShift < 1; fileShift++) {
                String currentSquare = board.shiftSquare(squareFrom, fileShift, rankShift);
                if (!currentSquare.equals(squareFrom)
                        && board.isWithinBoard(currentSquare)
                        && !board.isAttacked(color, currentSquare)
                        && (board.isEmpty(currentSquare)
                            || board.get(currentSquare).getColor() != color)) {
                    validMoves.add(currentSquare);
                }
            }
        }
        return validMoves;
    }

    private boolean isValidCastles(Board board, String squareFrom, String squareTo) {
        char cornerFile = squareTo.charAt(0) < squareFrom.charAt(0) ? Board.getFirstFile() : Board.getLastFile();
        String cornerSquare = "" + cornerFile + squareTo.charAt(1);
        Piece cornerPiece = board.get(cornerSquare);
        return canCastle()
                && squareFrom.charAt(1) == squareTo.charAt(1)
                && cornerPiece instanceof Rook
                && ((Rook)cornerPiece).canCastle()
                && board.isClearPath(squareFrom, cornerSquare)
                && !board.isAttackedPath(this, squareFrom, cornerSquare); // placed at the end because it is most computationally expensive
    }


    public boolean canCastle() { return canCastle;}

    public void disableCastling() { canCastle = false; }
}
