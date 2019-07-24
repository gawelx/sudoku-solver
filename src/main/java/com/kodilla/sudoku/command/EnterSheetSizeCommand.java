package com.kodilla.sudoku.command;

import com.kodilla.sudoku.SudokuGame;
import com.kodilla.sudoku.SudokuGameRunner;

import java.util.Arrays;

class EnterSheetSizeCommand implements Command {

    private final int sheetSize;

    EnterSheetSizeCommand(String input) {
        this.sheetSize = Integer.valueOf(input.trim());
    }

    @Override
    public void execute(Object object) {
        if (Arrays.asList(4, 9, 16, 25).contains(sheetSize)) {
            SudokuGameRunner gameRunner = (SudokuGameRunner) object;
            SudokuGame game = new SudokuGame(sheetSize, gameRunner.getInputReader(), gameRunner.getCommandFactory());
            game.play();
        } else {
            System.out.println("Wrong sheet size. The sheet size must be one of the following: 4, 9, 16, 25.");
        }
    }
}
