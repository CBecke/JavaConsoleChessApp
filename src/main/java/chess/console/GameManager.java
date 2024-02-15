package chess.console;

import chess.console.inputhandler.InputHandler;
import chess.console.pieces.Bishop;
import chess.console.pieces.Knight;
import chess.console.pieces.Piece;
import chess.console.printer.Printer;

import java.util.*;


public class GameManager {
    private final Board board = new Board();
    private final Printer printer;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final MoveLogger logger = new MoveLogger();
    private boolean whiteLost = false;
    private boolean blackLost = false;
    private boolean draw      = false;

    public GameManager(Printer printer, InputHandler inputHandler) {
        this.printer = printer;
        whitePlayer = new Player(Color.WHITE, inputHandler, printer);
        blackPlayer = new Player(Color.BLACK, inputHandler, printer);
    }

    public void playChess() {
        do {
            printer.printBoard(board);
            whitePlayer.move(board, logger);
            if (hasGameEnded()) { break; }

            printer.printFlippedBoard(board);
            blackPlayer.move(board, logger);
        } while ((!hasGameEnded()));

        printer.printResult(whiteLost, blackLost, draw);
    }

    private boolean hasGameEnded() {
        // Test for checkmate
        Collection<Square> kingSquares = board.getKingPositions();
        for (Square kingSquare : kingSquares) {
            if (board.isCheckmate(kingSquare)) {
                Color color = board.get(kingSquare).getColor();
                setLostFlag(color, true);
                return true;
            }
        }

        if (isDraw()) {
            draw = true;
            return true;
        }

        return false;
    }

    private boolean isDraw() {
        return logger.isThreeFoldRepetition()
                || logger.isFiftyMoveDraw()
                || isInsufficientMaterialDraw()
                || isStaleMate();
    }

    private boolean isStaleMate() {
        boolean whiteCanMove = false;
        boolean blackCanMove = false;
        for (Square square : board) {
            if (board.isEmpty(square)) { continue; }
            Piece piece = board.get(square);

            // Piece.canMove() can be quite expensive, so we try to avoid calling it by seeing if a piece of that color
            // already was able to move (meaning that color will not have a stalemate).
            Color color = piece.getColor();
            if ((color == Color.WHITE) ? whiteCanMove : blackCanMove) { continue; }

            if (piece.canMove(board, square)) {
                if (color == Color.WHITE)      { whiteCanMove = true; }
                else if (color == Color.BLACK) { blackCanMove = true; }
            }
        }

        return (!whiteCanMove || !blackCanMove);
    }

    /**
     * Determines if the game is a draw by insufficient material, according to USCF rules: the game is drawn if there is
     * no <b>forced</b> checkmate possible. This happens in 4 cases: (king), (king, knight), (king, bishop),
     * (king, knight, knight).
     */
    private boolean isInsufficientMaterialDraw() {
        Map<Class<? extends Piece>, Integer> whitePieces = new HashMap<>();
        Map<Class<? extends Piece>, Integer> blackPieces = new HashMap<>();
        for (Square square : board) {
            if (board.isEmpty(square)) { continue; }
            Piece piece = board.get(square);
            if (piece.getColor() == Color.WHITE)      { whitePieces.merge(piece.getClass(), 1, Integer::sum); }
            else if (piece.getColor() == Color.BLACK) { blackPieces.merge(piece.getClass(), 1, Integer::sum); }
        }

        return isInsufficientMaterial(whitePieces) || isInsufficientMaterial(blackPieces);
    }

    private boolean isInsufficientMaterial(Map<Class<? extends Piece>, Integer> pieceMap) {
        return pieceMap.keySet().size() == 2 // contains king and either knight or bishop
                && ((pieceMap.containsKey(Bishop.class)  // there is only 1 bishop
                        && pieceMap.get(Bishop.class) == 1)
                    || (pieceMap.containsKey(Knight.class)  // there is 1 or 2 knights
                        && pieceMap.get(Knight.class) <= 2));
    }


    private void setLostFlag(Color color, boolean b) {
        if (color == Color.WHITE)      { whiteLost = b; }
        else if (color == Color.BLACK) { blackLost = b; }
    }
}
