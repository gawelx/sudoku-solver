package com.kodilla.sudoku;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuSheet implements Cloneable {

    private int sheetSize;
    private int unknownNumbersCount;
    private List<List<Field>> sheet;

    public SudokuSheet(int sheetSize) {
        double sheetSizeSqrt = Math.sqrt(sheetSize);
        if (sheetSizeSqrt % 1 != 0) {
            throw new IllegalStateException("The sheet size can take the following values: 4, 9, 16, 25.");
        }
        this.sheetSize = sheetSize;
        int sectionSize = (int) Math.round(sheetSizeSqrt);
        this.unknownNumbersCount = sheetSize * sheetSize;
        this.sheet = new ArrayList<>();
        Field.setMaximumValue(sheetSize);
        for (int row = 0; row < sheetSize; row++) {
            List<Field> rowList = new ArrayList<>();
            for (int column = 0; column < sheetSize; column++) {
                int section = row / sectionSize + (column / sectionSize) * sectionSize;
                rowList.add(new Field(row, column, section));
            }
            this.sheet.add(rowList);
        }
    }

    public Stream<Field> getFieldStream() {
        return sheet.stream()
                .flatMap(Collection::stream);
    }

    public int getSheetSize() {
        return sheetSize;
    }

    public Field getFieldWithLeastPossibleNumbersCount() {
        Optional<Field> optionalField = sheet.stream()
                .flatMap(Collection::stream)
                .filter(Field::hasPossibleNumbers)
                .reduce((f1, f2) -> f1.getPossibleNumbers().size() > f2.getPossibleNumbers().size() ? f2 : f1);
        if (optionalField.isPresent()) {
            return optionalField.get();
        }
        throw new IllegalStateException("There are no fields with unset number.");
    }

    public boolean isSolved() {
        return unknownNumbersCount == 0;
    }

    public boolean setNumberInField(int row, int column, int number) {
        Field field = sheet.get(row).get(column);
        if (field.hasNumber()) {
            System.out.println("This field already contains the number.");
            return false;
        }

        if (!field.canTakeNumber(number)) {
            System.out.println("This field can't take the number of " + number + ".");
            return false;
        }

        field.setNumber(number);
        removePossibleNumberFromRow(row, number);
        removePossibleNumberFromColumn(column, number);
        removePossibleNumberFromSection(field.getSection(), number);
        unknownNumbersCount--;
        return true;
    }

    private void removePossibleNumberFromRow(int row, int number) {
        sheet.get(row).stream()
                .filter(field -> field.canTakeNumber(number))
                .forEach(field -> field.removeFromPossibleNumbers(number));
    }

    private void removePossibleNumberFromColumn(int column, int number) {
        getFieldStream()
                .filter(field -> field.getColumn() == column)
                .filter(field -> field.canTakeNumber(number))
                .forEach(field -> field.removeFromPossibleNumbers(number));
    }

    private void removePossibleNumberFromSection(int section, int number) {
        getFieldStream()
                .filter(field -> field.getSection() == section)
                .filter(field -> field.canTakeNumber(number))
                .forEach(field -> field.removeFromPossibleNumbers(number));
    }

    public boolean isOnlyPossibilityInRow(int row, int number) {
        return sheet.get(row).stream()
                .filter(field -> field.canTakeNumber(number))
                .count() == 1;
    }

    public boolean isOnlyPossibilityInColumn(int column, int number) {
        return getFieldStream()
                .filter(field -> field.getColumn() == column)
                .filter(field -> field.canTakeNumber(number))
                .count() == 1;
    }

    public boolean isOnlyPossibilityInSection(int section, int number) {
        return getFieldStream()
                .filter(field -> field.getSection() == section)
                .filter(field -> field.canTakeNumber(number))
                .count() == 1;
    }

    @Override
    public String toString() {
        int valueWidth = sheetSize > 9 ? 2 : 1;
        String format = " %" + valueWidth + "d ";
        String empty = new String(new char[valueWidth + 2]).replace("\0", " ");

        int dividerLength = (3 + valueWidth) * sheetSize + 1;
        String divider = new String(new char[dividerLength]).replace("\0", "-");

        StringBuilder sheetString = new StringBuilder(divider).append("\n");
        for (List<Field> row : sheet) {
            String rowString = row.stream()
                    .map(Field::getNumber)
                    .map(optional -> optional.isPresent() ? String.format(format, optional.get()) : empty)
                    .collect(Collectors.joining("|"));
            sheetString.append("|").append(rowString).append("|").append("\n");
            sheetString.append(divider).append("\n");
        }

        return sheetString.toString();
    }

    @Override
    public SudokuSheet clone() {
        try {
            SudokuSheet clonedSudokuSheet = (SudokuSheet) super.clone();
            clonedSudokuSheet.sheet = new LinkedList<>();
            for (List<Field> row : sheet) {
                LinkedList<Field> clonedRow = new LinkedList<>();
                for (Field field : row) {
                    clonedRow.add(new Field(field));
                }
                clonedSudokuSheet.sheet.add(clonedRow);
            }
            return clonedSudokuSheet;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
