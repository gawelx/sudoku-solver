package com.kodilla.sudoku;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {

    private final static int EMPTY = -1;
    private final static int UNSET = -1;

    private static int maxValue = UNSET;
    private static List<Integer> values;

    private final int row;
    private final int column;
    private final int section;
    private final List<Integer> possibleNumbers;

    private int number;

    public Field(final int row, final int column, final int section) {
        if (maxValue == UNSET){
            throw new IllegalStateException("The maximum value for number must be set before creating field object.");
        }
        this.row = row;
        this.column = column;
        this.section = section;
        this.number = EMPTY;
        this.possibleNumbers = new LinkedList<>(values);
    }

    public Field(Field field) {
        this.row = field.row;
        this.column = field.column;
        this.section = field.section;
        this.number = field.number;
        this.possibleNumbers = new LinkedList<>(field.possibleNumbers);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSection() {
        return section;
    }

    public static void setMaximumValue(int maxValue) {
        if (Math.sqrt(maxValue) % 1 != 0) {
            throw new IllegalStateException("The maximum value can take the following values: 4, 9, 16, 25.");
        }
        Field.maxValue = maxValue;
        Field.values = IntStream
                .rangeClosed(1, maxValue)
                .boxed()
                .collect(Collectors.toList());
    }

    public void setNumber(int number) {
        this.number = number;
        this.possibleNumbers.clear();
    }

    public Optional<Integer> getNumber() {
        return hasNumber() ? Optional.of(number): Optional.empty();
    }

    public List<Integer> getPossibleNumbers() {
        return new LinkedList<>(possibleNumbers);
    }

    public boolean hasNumber() {
        return number > EMPTY;
    }

    public boolean hasPossibleNumbers() {
        return !possibleNumbers.isEmpty();
    }

    public void removeFromPossibleNumbers(int number) {
        possibleNumbers.remove(Integer.valueOf(number));
    }

    public boolean canTakeNumber(int number) {
        return possibleNumbers.contains(number);
    }

}
