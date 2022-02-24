package commands;

import interfaces.CommandManagement;
import interfaces.Operand;
import java.util.Scanner;

/**
 * Class {@code CommandManager} was created to manage
 * all the commands enumerated in {@link AvailableCommands}.
 * Object of this class performs main stages of execution of command.
 */
public class CommandManager implements CommandManagement {

    /** Title of the current instruction.*/
    private String instructionTitle;

    /**
     * Constructs {@code CommandManager} object.
     * @param instructionTitle received instruction of the user.
     */
    public CommandManager(String instructionTitle) {
        this.instructionTitle = instructionTitle;
    }

    public AvailableCommands instructionFetch() {
        for (AvailableCommands command : AvailableCommands.values()) {
            if (command.getRegex(instructionTitle).matches()) {
                return command;
            }
        }
        System.out.println("Команда не существует или введена некорректно");
        System.out.println("Введите одну из доступных команд из списка:");
        for (AvailableCommands command : AvailableCommands.values()) {
            System.out.println(command.getTitle());
        }
        Scanner scanner = new Scanner(System.in);
        instructionTitle = scanner.nextLine();
        if (instructionTitle.equals("exit"))
            System.exit(0);
        return instructionFetch();
    }

    public String operandFetch(){
        Scanner scanner = new Scanner(instructionTitle);
        scanner.next();
        return scanner.next();
    }

    public void execution(AvailableCommands command) {
        String commandName = command.toString();
        CommandObjects currentCommandObj = CommandObjects.valueOf(commandName);
        Command currentCommand = currentCommandObj.getCommand();
        if (currentCommand instanceof Operand)
            ((Operand) currentCommand).installOperand(operandFetch());
        currentCommand.execute();
    }

}
