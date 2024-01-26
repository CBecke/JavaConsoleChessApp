package chess.console.inputhandler;

import chess.console.inputhandler.InputHandler;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private Scanner scanner;

    public ConsoleInputHandler() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }
}
