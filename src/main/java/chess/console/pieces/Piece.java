package chess.console.pieces;

public class Piece {
    public static enum Color {
        WHITE,
        BLACK
    }

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
