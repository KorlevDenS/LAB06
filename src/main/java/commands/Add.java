package commands;

import basic.objects.*;
import exceptions.InvalidDataFromFileException;
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

    protected MusicBand newBand;

    protected boolean isLoaded;

    public void loadElement(){
        if (Accumulator.readingTheScript) {
            try {
                ScriptDataLoader loader = new ScriptDataLoader();
                newBand = loader.loadObjectFromData();
            } catch (InvalidDataFromFileException ex) {
                System.out.println("В скрипте обнаружена ошибка.");
                isLoaded = false;
                Accumulator.readingTheScript = false;
                return;
            }
        } else newBand = loadObjectFromData();
        isLoaded = true;
    }

    public void addElement() {
        if (isLoaded) {
            Accumulator.appleMusic.add(newBand);
            System.out.println("Новый элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Элемент в коллекцию не добавлен");
        }
    }

    public void execute() {
        loadElement();
        addElement();
    }

    public String getDescription() {
        return this.description;
    }
}
