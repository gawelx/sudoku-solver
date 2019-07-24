package com.kodilla.sudoku.command;

public class CommandDTO {

    private final String input;
    private final CommandType commandType;

    public CommandDTO(String input, CommandType commandType) {
        this.input = input;
        this.commandType = commandType;
    }

    public String getInput() {
        return input;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
