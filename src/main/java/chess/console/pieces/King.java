package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.HashSet;

public class King extends Piece{
    private boolean canCastle = true;

    public King(Color color) { super(color); }

    @Override
    public boolean isValidPieceMove(Board board, Square squareFrom, Square squareTo) {
        int fileDiff = Square.absFileDiff(squareFrom, squareTo);
        int rankDiff = Square.absRankDiff(squareFrom, squareTo);
        return !board.isAttacked(color, squareTo)
                && ((fileDiff <= 1 && rankDiff <= 1) || isValidCastles(board, squareFrom, squareTo)) ;
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "K" : "k";
    }

    @Override
    public Collection<Square> getValidPieceMoves(Board board, Square squareFrom) {
        Collection<Square> validMoves = new HashSet<>();
        // iterate over neighboring squares (including diagonally neighboring)
        for (int rankShift = -1; rankShift <= 1; rankShift++) {
            for (int fileShift = -1; fileShift < 1; fileShift++) {
                Square currentSquare = squareFrom.shift(fileShift, rankShift);
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

    private boolean isValidCastles(Board board, Square squareFrom, Square squareTo) {
        char cornerFile = squareTo.getCharFile() < squareFrom.getCharFile() ? Board.firstFile : Board.lastFile;
        Square cornerSquare = new Square(cornerFile, squareTo.getRank());
        Piece cornerPiece = board.get(cornerSquare);
        return canCastle()
                && squareFrom.getRank() == squareTo.getRank()
                && cornerPiece instanceof Rook
                && ((Rook)cornerPiece).canCastle()
                && board.isClearPath(squareFrom, cornerSquare)
                && !board.isAttackedPath(this, squareFrom, cornerSquare); // placed at the end because it is most computationally expensive
    }


    public boolean canCastle() { return canCastle;}

    public void disableCastling() { canCastle = false; }
}
