package com.kodilla.sudoku.command;

public class CommandFactory {

    public Command makeCommand(CommandDTO commandDTO) {
        switch (commandDTO.getCommandType()) {
            case ENTER_NUMBER:
                return new EnterNumberCommand(commandDTO.getInput());
            case SOLVE_SUDOKU:
                return new SolveCommand();
            case DROP_GAME:
                return new DropGameCommand();
            case ENTER_SHEET_SIZE:
                return new EnterSheetSizeCommand(commandDTO.getInput());
            case EXIT_GAME:
                return new ExitGameCommand();
        }
        throw new IllegalStateException("Unknown command type.");
    }
}
