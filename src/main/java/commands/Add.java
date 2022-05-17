package commands;

import basic.objects.*;
import common.ResultPattern;
import exceptions.InvalidDataFromFileException;
import interfaces.*;

/**
 * Class {@code Add} is used for creating command "add" objects,
 * that add {@code MusicBand} objects in the {@code HashSet}.
 */
public class Add extends Command implements Adding {

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
        } else newBand = dataBase.getMusicBand();
    }

    public void addElement() {
        Accumulator.appleMusic.add(newBand);
        report.getReports().add("Новый элемент успешно добавлен в коллекцию.");
    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        loadElement();
        addElement();
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
