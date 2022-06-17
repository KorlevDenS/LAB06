package server;

import common.AvailableCommands;
import server.commands.Command;
import server.commands.CommandObjects;
import common.ResultPattern;
import common.exceptions.InstructionFetchException;
import common.exceptions.InvalidDataFromFileException;
import common.CommandManagement;
import server.commands.ExecuteScript;
import server.interfaces.Operand;

import java.util.Scanner;

/**
 * Class {@code ScriptCommandManager} was created to manage
 * all the commands enumerated in {@link AvailableCommands}.
 * Object of this class performs main stages of execution of command.
 */
public class ScriptCommandManager implements CommandManagement<AvailableCommands, ResultPattern, AvailableCommands> {

    /**
     * Title of the current instruction.
     */
    private final String instructionTitle;
    private ExecuteScript.ExecutionStringScanner scriptScanner;

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
        return scanner.next();
    }

    public ResultPattern execution(AvailableCommands command) throws InvalidDataFromFileException {
        String commandName = command.toString();
        CommandObjects currentCommandObj = CommandObjects.valueOf(commandName);
        Command currentCommand = currentCommandObj.getCommand();
        currentCommand.turnOnScriptMode();
        currentCommand.setScriptScanner(scriptScanner);
        if (currentCommand instanceof Operand)
            ((Operand) currentCommand).installOperand(operandFetch());
        return currentCommand.execute();
    }

}
