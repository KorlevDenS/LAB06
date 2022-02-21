package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;

/**
 * Class Clear is used for creating command "clear" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}.
 */
public class Clear extends Command{

    /**
     * Constructs new Clear object.
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
     */
    public Clear(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.CLEAR)
            throw new IncorrectDataForObjectException("Class Clear cannot perform this task");
    }

    public void execute() {
        Accumulator.appleMusic.clear();
        System.out.println("Из коллекции были удалены все элементы.");
    }

    public String getDescription() {
        return this.description;
    }
}
