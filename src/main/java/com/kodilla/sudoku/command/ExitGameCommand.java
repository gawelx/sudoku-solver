package com.kodilla.sudoku.command;

import com.kodilla.sudoku.SudokuGameRunner;

public class ExitGameCommand implements Command {

    @Override
    public void execute(Object object) {
        ((SudokuGameRunner) object).setKeepPlaying(false);
    }
}
