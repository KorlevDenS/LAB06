package Server;

import commands.AvailableCommands;
import commands.Command;
import commands.CommandObjects;
import common.InstructionPattern;
import common.ResultPattern;
import exceptions.InvalidDataFromFileException;

import java.util.ArrayList;

public class ServerCommandManager {

    private final InstructionPattern instructionPattern;

    public ServerCommandManager(InstructionPattern pattern) {
        this.instructionPattern = pattern;
    }

    public Command makeInstruction() {
        String instructionType = instructionPattern.getInstructionType();
        CommandObjects emptyCommand = CommandObjects.valueOf(instructionType);
        Command readyCommand = emptyCommand.getCommand();
        readyCommand.setDataBase(instructionPattern);

        return readyCommand;
    }

    public ResultPattern execution() throws InvalidDataFromFileException {
        Command currentCommand = makeInstruction();
        return currentCommand.execute();
    }
}
