package com.kodilla.sudoku.command;

import com.kodilla.sudoku.SudokuGame;

class DropGameCommand implements Command {

    @Override
    public void execute(Object object) {
        ((SudokuGame) object).setDropGame(true);
    }
}
