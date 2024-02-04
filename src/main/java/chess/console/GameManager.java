package chess.console;

import chess.console.pieces.Bishop;
import chess.console.pieces.Knight;
import chess.console.pieces.Piece;
import chess.console.printer.ConsolePrinter;
import chess.console.printer.Printer;
import jdk.internal.org.objectweb.asm.tree.analysis.BasicInterpreter;

import java.util.*;


public class GameManager {
    private final Board board = new Board();
    private final Printer printer = new ConsolePrinter();
    private Player whitePlayer = new Player(Color.WHITE);
    private final MoveLogger logger = new MoveLogger();
    private boolean whiteLost = false;
    private boolean blackLost = false;
    private boolean draw      = false;

    public void playChess() {
        printer.printBoard(board);

        /* Potentially keep player array and use modulo to chose player. will make loop shorter*/
        while (!isGameOver()) {
            // White to move
            whitePlayer.move(board, logger);

            // print board
            printer.printBoard(board);

            // check for ended game
            boolean gameEnded = hasGameEnded();

            // report if game ended

            // black to move

            // print board

            // check for ended game
        }

    }

    private boolean hasGameEnded() {
        // Test for checkmate
        Collection<String> kingSquares = board.getKingPositions();
        for (String kingSquare : kingSquares) {
            if (board.isCheckmate(kingSquare)) {
                Color color = board.get(kingSquare).getColor();
                setLostFlag(color, true);
                return true;
            }
        }

        // Test for 3-fold repetition
        if (logger.isThreeFoldRepetition() || logger.isFiftyMoveDraw() || isInsufficientMaterialDraw() || isStaleMate()) {
            draw = true;
            return true;
        }

        // Test for stalemate

        // TODO: draw by agreement
        // TODO: resignation

        return false;
    }

    /**
     * Determines if the game is a draw by insufficient material, according to USCF rules: the game is drawn if there is
     * no <b>forced</b> checkmate possible. This happens in 4 cases: (king), (king, knight), (king, bishop),
     * (king, knight, knight).
     */
    private boolean isInsufficientMaterialDraw() {
        Map<Class<? extends Piece>, Integer> whitePieces = new HashMap<>();
        Map<Class<? extends Piece>, Integer> blackPieces = new HashMap<>();
        for (String square : board) {
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
        if (color == Color.WHITE)
            { whiteLost = b; }
        else
            { blackLost = b; }
    }

    private boolean isGameOver() { return whiteLost || blackLost || draw; }


}
