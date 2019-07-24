package com.kodilla.sudoku.command;

public enum CommandType {
    ENTER_NUMBER(" *(\\d{1,2}) *, *(\\d{1,2}) *, *(\\d{1,2}) *"),
    SOLVE_SUDOKU("solve"),
    DROP_GAME("drop"),
    EXIT_GAME("exit"),
    ENTER_SHEET_SIZE(" *\\d{1,2} *");

    private final String regex;

    CommandType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
