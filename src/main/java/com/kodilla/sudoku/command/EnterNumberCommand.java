package com.kodilla.sudoku.command;

import com.kodilla.sudoku.SudokuGame;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class EnterNumberCommand implements Command {

    private final int row;
    private final int column;
    private final int number;

    EnterNumberCommand(String input) {
        List<Integer> numbers = Stream.of(input.split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        this.row = numbers.get(0);
        this.column = numbers.get(1);
        this.number = numbers.get(2);
    }

    @Override
    public void execute(Object object) {
        SudokuGame game = (SudokuGame) object;
        int sheetSize = game.getSudokuSheet().getSheetSize();
        List<Integer> allowedValues = IntStream
                .rangeClosed(1, sheetSize)
                .boxed()
                .collect(Collectors.toList());
        if (allowedValues.containsAll(Arrays.asList(row, column, number))) {
            if (game.getSudokuSheet().setNumberInField(row - 1, column - 1, number)) {
                System.out.println("The number has been set.");
            }
        } else {
            System.out.println("One or more values are wrong. Values of row, column and number must be between 1 and " + sheetSize + " (both inclusive).");
        }
    }
}
