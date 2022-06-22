package server;

import common.InstructionPattern;
import common.exceptions.InvalidDataFromFileException;

import java.io.ObjectOutputStream;

public class CommandThreadHandler extends Thread {

    private final ObjectOutputStream sendToClient;
    private final InstructionPattern instructionPattern;

    public CommandThreadHandler(ObjectOutputStream out, InstructionPattern instruction) {
        this.sendToClient = out;
        this.instructionPattern = instruction;
    }

    public void run() {
        try {
            ServerCommandManager commandManager = new ServerCommandManager(instructionPattern);
            commandManager.execution(commandManager.instructionFetch(), sendToClient);

        } catch (InvalidDataFromFileException e) {
            System.out.println("Команда " + instructionPattern.getInstructionType() + " выполнена, клиент не доступен" +
                    " для получения результата.");
        }
    }

}
