package commands;

import basic.objects.*;
import exceptions.InvalidDataFromFileException;
import interfaces.*;

/**
 * Class {@code Add} is used for creating command "add" objects,
 * that add {@code MusicBand} objects in the {@code HashSet}.
 */
public class Add extends DataLoader implements Adding {

    /**
     * Constructs new Add object.
     *
     * @param command enum constant from {@link AvailableCommands}
     */
    public Add(AvailableCommands command) {
        super(command);
    }

    /**
     * A field to keep successfully loaded {@code MusicBand}.
     */
    protected MusicBand newBand;

    public void loadElement() throws InvalidDataFromFileException {
        if (Accumulator.readingTheScript) {
            ScriptDataLoader loader = new ScriptDataLoader();
            newBand = loader.loadObjectFromData();
        } else newBand = loadObjectFromData();
    }

    public void addElement() {
        Accumulator.appleMusic.add(newBand);
        System.out.println("Новый элемент успешно добавлен в коллекцию.");
    }

    public void execute() throws InvalidDataFromFileException {
        loadElement();
        addElement();
    }

    public String getDescription() {
        return this.description;
    }
}
