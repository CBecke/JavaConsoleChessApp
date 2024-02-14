package chess.console;

public class Square {
    private final char file;
    private final int rank;

    public Square(char file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Converts a string  to a square. No safety checks included
     * @param square length 2 string consisting of a char followed by a number, e.g. "e5".
     */
    public Square(String square) {
        this.file = square.charAt(0);
        this.rank = Character.getNumericValue(square.charAt(1));
    }

    /**
     * Returns a character representing the file. Be Wary of implicit conversion!
     *
     * @return a character representing the file
     */
    public char getCharFile() {
        return file;
    }


    public int getRank() {
        return rank;
    }

    /**
     * Gets the square one would reach by moving fileShift to the side and rankShift up/down from square.
     * E.g. shiftSquare("a4", 4, -1) = "e3"
     */
    public Square shift(int directionFile, int directionRank) {
        return new Square((char)(file + directionFile), rank+directionRank);
    }

    /**
     * @return the absolute difference between file (taken by subtraction)
     */
    public static int absFileDiff(Square s1, Square s2) { return Math.abs(s1.getCharFile() - s2.getCharFile()); }
    /**
     * @return the absolute difference between file (taken by subtraction)
     */
    public static int absRankDiff(Square s1, Square s2) { return Math.abs(s1.getRank() - s2.getRank()); }

    @Override
    public String toString() {
        return "" + file + rank;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Square
                && this.file == ((Square) other).getCharFile() && this.rank == ((Square) other).getRank();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 97;
        result = result * prime + file;
        result = result * prime + rank;
        return result;
    }
}
