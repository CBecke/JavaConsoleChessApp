package chess.console;

public class Knight {
    public static enum Color {
        WHITE,
        BLACK
    }

    private final Color color;

    public Knight(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isValidMove(String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return (fileDiff == 1 && rankDiff == 2)
                || (fileDiff == 2 && rankDiff == 1);
    }
}
