package server;

import server.commands.Command;
import server.commands.CommandObjects;
import common.CommandManagement;
import common.InstructionPattern;
import common.ResultPattern;
import common.exceptions.InvalidDataFromFileException;

public class ServerCommandManager implements CommandManagement<Command, ResultPattern, Command> {

    private final InstructionPattern instructionPattern;

    public ServerCommandManager(InstructionPattern pattern) {
        this.instructionPattern = pattern;
    }

    public Command instructionFetch() {
        String instructionType = instructionPattern.getInstructionType();
        CommandObjects emptyCommand = CommandObjects.valueOf(instructionType);
        Command readyCommand = emptyCommand.getCommand();
        readyCommand.setDataBase(instructionPattern);
        readyCommand.getReport().setInstructionTitle(readyCommand.getDataBase().getTitleRegex());
        return readyCommand;
    }

    public ResultPattern execution(Command command) throws InvalidDataFromFileException {
        return command.execute();
    }
}
