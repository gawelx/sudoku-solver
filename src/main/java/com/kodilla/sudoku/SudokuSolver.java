package com.kodilla.sudoku;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class SudokuSolver {

    private SudokuSheet currentSudokuSheet;
    private List<SudokuSheetSnapshot> backtrack = new LinkedList<>();

    public SudokuSolver(SudokuSheet currentSudokuSheet){
        this.currentSudokuSheet = currentSudokuSheet;
    }

    public Optional<SudokuSheet> solve() {
        while (true) {
            AtomicBoolean changeMade = new AtomicBoolean(false);

            try {
                currentSudokuSheet.getFieldStream()
                        .filter(field -> !field.hasNumber())
                        .forEach(field -> changeMade.compareAndSet(false, examineField(field)));
            } catch (SudokuUnsolvableException e) {
                return Optional.empty();
            }

            if (currentSudokuSheet.isSolved()) {
                return Optional.of(currentSudokuSheet);
            }

            if (!changeMade.get()) {
                Field bestField = currentSudokuSheet.getFieldWithLeastPossibleNumbersCount();
                makeSnapshot(bestField);
            }
        }
    }

    private boolean examineField(Field field) throws SudokuUnsolvableException {
        if (!field.hasPossibleNumbers()) {
            if (backtrack.isEmpty()) {
                throw new SudokuUnsolvableException();
            } else {
                takeAnotherNumberFromSnapshot();
            }
            return true;
        }

        if (field.getPossibleNumbers().size() == 1) {
            return currentSudokuSheet.setNumberInField(
                    field.getRow(),
                    field.getColumn(),
                    field.getPossibleNumbers().get(0)
            );
        }

        for (int number : field.getPossibleNumbers()) {
            if (isOnlyPossibleNumber(field.getRow(), field.getColumn(), field.getSection(), number)) {
                return currentSudokuSheet.setNumberInField(
                        field.getRow(),
                        field.getColumn(),
                        number
                );
            }
        }

        return false;
    }

    private boolean isOnlyPossibleNumber(int row, int column, int section, int number) {
        return currentSudokuSheet.isOnlyPossibilityInRow(row, number) ||
                currentSudokuSheet.isOnlyPossibilityInColumn(column, number) ||
                currentSudokuSheet.isOnlyPossibilityInSection(section, number);
    }

    private void makeSnapshot(Field field) {
        int number = field.getPossibleNumbers().get(0);
        backtrack.add(0, new SudokuSheetSnapshot(currentSudokuSheet, field, number));
        currentSudokuSheet = currentSudokuSheet.clone();
        currentSudokuSheet.setNumberInField(field.getRow(), field.getColumn(), number);
    }

    private void takeAnotherNumberFromSnapshot() {
        SudokuSheetSnapshot snapshot = backtrack.get(0);
        Field field = snapshot.getField();
        if (field.getPossibleNumbers().size() > 2) {
            field.removeFromPossibleNumbers(snapshot.getNumber());
            snapshot.setNumber(field.getPossibleNumbers().get(0));
            currentSudokuSheet = snapshot.getSudokuSheet().clone();
        } else {
            backtrack.remove(0);
            currentSudokuSheet = snapshot.getSudokuSheet();
            currentSudokuSheet.setNumberInField(field.getRow(), field.getColumn(), field.getPossibleNumbers().get(1));
            backtrack.remove(0);
        }
    }

}
