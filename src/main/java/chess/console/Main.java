package chess.console;

import chess.console.inputhandler.ConsoleInputHandler;
import chess.console.inputhandler.InputHandler;
import chess.console.printer.ConsolePrinter;
import chess.console.printer.Printer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Printer printer = new ConsolePrinter();
        InputHandler inputHandler = new ConsoleInputHandler();
        GameManager gameManager = new GameManager(printer, inputHandler);
        gameManager.playChess();

    }
}
