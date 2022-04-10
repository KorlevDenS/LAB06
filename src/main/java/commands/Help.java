package commands;

import exceptions.IncorrectDataForObjectException;

/**
 * Class {@code Help} is used for creating command "help" object,
 * that prints a manual with all commands and their descriptions.
 */
public class Help extends Command {

    /**
     * Constructs new {@code Help} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Help(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.HELP)
            throw new IncorrectDataForObjectException("Class Help cannot perform this task");
    }

    public void execute() {
        for (AvailableCommands command : AvailableCommands.values()) {
            System.out.println("Команда " + command.getTitle() + " - " + command.getDescription() + ".");
        }
    }

    public String getDescription() {
        return this.description;
    }
}
