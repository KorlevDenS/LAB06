package server;

import common.AvailableCommands;
import server.commands.Command;
import server.commands.CommandObjects;
import common.ResultPattern;
import common.exceptions.InstructionFetchException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.CommandManagement;
import server.commands.ExecuteScript;
import server.interfaces.Operand;

import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Class {@code ScriptCommandManager} was created to manage
 * all the commands enumerated in {@link AvailableCommands}.
 * Object of this class performs main stages of execution of command.
 */
public class ScriptCommandManager implements CommandManagement<AvailableCommands, AvailableCommands, ObjectOutputStream> {

    /**
     * Title of the current instruction.
     */
    private final String instructionTitle;
    private ExecuteScript.ExecutionStringScanner scriptScanner;
    private ResultPattern commandResult;
    private long clientId;

    /**
     * Constructs {@code ScriptCommandManager} object.
     *
     * @param instructionTitle received instruction of the user's script.
     */
    public ScriptCommandManager(String instructionTitle, ExecuteScript.ExecutionStringScanner scriptScanner) {
        this.instructionTitle = instructionTitle;
        this.scriptScanner = scriptScanner;
    }

    public AvailableCommands instructionFetch() {
        for (AvailableCommands command : AvailableCommands.values()) {
            if (command.getRegex(instructionTitle).matches()) {
                return command;
            }
        }
        throw new InstructionFetchException("ExecuteScript object's command validation works incorrect.");
    }

    public String operandFetch() {
        Scanner scanner = new Scanner(instructionTitle);
        scanner.next();
        return scanner.nextLine().trim();
    }

    public void execution(AvailableCommands command, ObjectOutputStream sentToClient) throws InvalidDataFromFileException {
        String commandName = command.toString();
        CommandObjects currentCommandObj = CommandObjects.valueOf(commandName);
        Command currentCommand = currentCommandObj.getCommand();
        currentCommand.setClientId(clientId);
        currentCommand.turnOnScriptMode();
        currentCommand.setScriptScanner(scriptScanner);
        if (currentCommand instanceof Operand)
            ((Operand) currentCommand).installOperand(operandFetch());
        currentCommand.execute(sentToClient);
        this.commandResult = currentCommand.getReport();
    }

    public ResultPattern getCommandResult() {
        return this.commandResult;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

}
