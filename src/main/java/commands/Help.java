package commands;

import exceptions.IncorrectDataForObjectException;

import java.util.Arrays;

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
        Arrays.stream(AvailableCommands.values())
                .forEach(s -> System.out.println("Команда " + s.getTitle() + " - " + s.getDescription() + "."));
    }

    public String getDescription() {
        return this.description;
    }
}
