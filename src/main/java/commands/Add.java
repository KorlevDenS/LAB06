package commands;

import basic.objects.*;
import interfaces.*;

/**
 * Class Add is used for creating command "add" objects,
 * that add {@code MusicBand} objects in the {@code HashSet}.
 */
public class Add extends DataLoader implements Adding {

    /**
     * Constructs new Add object.
     * @param command enum constant from {@link AvailableCommands}
     */
    public Add(AvailableCommands command) {
        super(command);
    }

    public void addElement() {
        MusicBand newBand = loadObjectFromData();
        Accumulator.appleMusic.add(newBand);
    }

    public void execute() {
        addElement();
        System.out.println("Новый элемент успешно добавлен в коллекцию.");
    }

    public String getDescription() {
        return this.description;
    }
}
