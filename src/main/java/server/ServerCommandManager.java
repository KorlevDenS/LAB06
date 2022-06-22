package server;

import server.commands.Command;
import server.commands.CommandObjects;
import common.InstructionPattern;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.CommandManagement;

import java.io.ObjectOutputStream;

public class ServerCommandManager implements CommandManagement<Command, Command, ObjectOutputStream> {

    private final InstructionPattern instructionPattern;

    public ServerCommandManager(InstructionPattern pattern) {
        this.instructionPattern = pattern;
    }

    public Command instructionFetch() {
        String instructionType = instructionPattern.getInstructionType();
        CommandObjects emptyCommand = CommandObjects.valueOf(instructionType);
        Command readyCommand = emptyCommand.getCommand();
        readyCommand.setDataBase(instructionPattern);
        readyCommand.setClientId(instructionPattern.getClientId());
        readyCommand.getReport().setInstructionTitle(readyCommand.getDataBase().getTitleRegex());
        return readyCommand;
    }

    public void execution(Command command, ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        command.execute(sendToClient);
    }
}
