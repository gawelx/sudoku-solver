package com.kodilla.sudoku;

import com.kodilla.sudoku.command.CommandDTO;
import com.kodilla.sudoku.command.CommandType;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReader {

    private final Scanner scanner = new Scanner(System.in);

    public Optional<CommandDTO> getInput(CommandType... commandTypes) {
        String input = scanner.nextLine().toLowerCase();
        Optional<CommandType> type = identifyCommandType(input, commandTypes);
        return type.map(commandType -> new CommandDTO(input, commandType));
    }

    private Optional<CommandType> identifyCommandType(String input, CommandType... commandTypes) {
        for (CommandType type : commandTypes) {
            if (Pattern.matches(type.getRegex(), input)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
