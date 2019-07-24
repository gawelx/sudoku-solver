package com.kodilla.sudoku;

public class SudokuSheetSnapshot {

    private final SudokuSheet sudokuSheet;
    private final Field field;
    private int number;

    public SudokuSheetSnapshot(SudokuSheet sudokuSheet, Field field, int number) {
        this.sudokuSheet = sudokuSheet;
        this.field = field;
        this.number = number;
    }

    public SudokuSheet getSudokuSheet() {
        return sudokuSheet;
    }

    public Field getField() {
        return field;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
