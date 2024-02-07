import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class PGNParser {
    String filePath;
    Collection<String> games = new LinkedList<>();

    public PGNParser(String path) {
        filePath = path;
        parseFile();
    }

    private void parseFile() {
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!isGameStart(line)) { continue; }
                StringBuilder gameConcatenator = new StringBuilder(line);
                String game = parseGame(reader, gameConcatenator);
                games.add(game);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param reader a Scanner which starts at a game ("1. [...]"). Assumes the game ends with an empty newline.
     */
    private String parseGame(Scanner reader, StringBuilder gameConcatenator) {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.isEmpty()) { break; }
            gameConcatenator.append(line).append(" ");
        }

        return gameConcatenator.toString();
    }

    private boolean isGameStart(String line) {
        return line.startsWith("1. ");
    }

    public void printGames() {
        for (String game : games) {
            System.out.println(game);
            System.out.println();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        PGNParser parser = new PGNParser("src/test/resources/PGNGames/SaintLouis2023.pgn");
        parser.printGames();
    }
}
