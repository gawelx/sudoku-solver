package com.kodilla.sudoku;

import com.kodilla.sudoku.command.CommandDTO;
import com.kodilla.sudoku.command.CommandFactory;
import com.kodilla.sudoku.command.CommandType;

import java.util.Optional;

public class SudokuGame {

    private final InputReader inputReader;
    private final CommandFactory commandFactory;

    private SudokuSheet sudokuSheet;
    private boolean solved = false;
    private boolean dropGame = false;

    public SudokuGame(int sheetSize, final InputReader inputReader, final CommandFactory commandFactory) {
        this.sudokuSheet = new SudokuSheet(sheetSize);
        this.inputReader = inputReader;
        this.commandFactory = commandFactory;
    }

    public void play() {
        while (!dropGame) {
            System.out.println("Sudoku sheet:");
            System.out.println(sudokuSheet);
            System.out.println("Available commands (case insensitive):");
            System.out.println("   *) <row no>, <column no>, <number> - sets the number in the pointed field;");
            System.out.println("   *) solve - solves current sudoku sheet;");
            System.out.println("   *) drop - drops this game.");
            System.out.println("Enter the command:");
            Optional<CommandDTO> input = inputReader.getInput(
                    CommandType.ENTER_NUMBER,
                    CommandType.SOLVE_SUDOKU,
                    CommandType.DROP_GAME
            );
            System.out.print("Command status: ");
            if (input.isPresent()) {
                commandFactory.makeCommand(input.get()).execute(this);
            } else {
                System.out.println("Unknown command.");
            }
        }
        if (solved) {
            System.out.println("Solution:");
            System.out.println(sudokuSheet);
        }
    }

    public SudokuSheet getSudokuSheet() {
        return sudokuSheet;
    }

    public void setSudokuSheet(SudokuSheet sudokuSheet) {
        this.sudokuSheet = sudokuSheet;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
        if (solved) {
            System.out.println("Sudoku sheet has been solved.");
        } else {
            System.out.println("This sudoku sheet is unsolvable.");
        }
    }

    public void setDropGame(boolean dropGame) {
        this.dropGame = dropGame;
        if (!solved) {
            System.out.println("The game has been dropped.");
        }
    }

}
