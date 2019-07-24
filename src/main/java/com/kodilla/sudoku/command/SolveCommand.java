package com.kodilla.sudoku.command;

import com.kodilla.sudoku.SudokuGame;
import com.kodilla.sudoku.SudokuSheet;
import com.kodilla.sudoku.SudokuSolver;

import java.util.Optional;

class SolveCommand implements Command {

    @Override
    public void execute(Object object) {
        SudokuGame game = (SudokuGame) object;
        SudokuSolver solver = new SudokuSolver(game.getSudokuSheet());
        Optional<SudokuSheet> solvedSudokuSheet = solver.solve();
        solvedSudokuSheet.ifPresent(game::setSudokuSheet);
        game.setSolved(solvedSudokuSheet.isPresent());
        game.setDropGame(true);
    }
}
