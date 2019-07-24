package com.kodilla.sudoku;

import com.kodilla.sudoku.command.CommandDTO;
import com.kodilla.sudoku.command.CommandFactory;
import com.kodilla.sudoku.command.CommandType;

import java.util.Optional;

public class SudokuGameRunner {

    private final InputReader inputReader = new InputReader();
    private final CommandFactory commandFactory = new CommandFactory();

    private boolean keepPlaying = true;

    public static void main(String[] args) {
        SudokuGameRunner gameRunner = new SudokuGameRunner();
        while (gameRunner.keepPlaying()) {
            System.out.println("\nSUDOKU SOLVER");
            System.out.println("Enter the size of sudoku sheet to solve (possible values are 4, 9, 16, 25) " +
                    "or type 'exit' to quit the game:");
            Optional<CommandDTO> input = gameRunner.getInputReader().getInput(
                    CommandType.ENTER_SHEET_SIZE,
                    CommandType.EXIT_GAME
            );
            System.out.print("Command status: ");
            if (input.isPresent()) {
                gameRunner.getCommandFactory().makeCommand(input.get()).execute(gameRunner);
            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    public InputReader getInputReader() {
        return inputReader;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    private boolean keepPlaying() {
        return keepPlaying;
    }

    public void setKeepPlaying(boolean keepPlaying) {
        this.keepPlaying = keepPlaying;
        System.out.println("Sudoku solver is closing.");
    }

}
