package server.commands;

import server.interfaces.Adding;
import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.InvalidDataFromFileException;

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
        if (isReadingTheScript()) {
            ScriptDataLoader loader = new ScriptDataLoader();
            newBand = loader.loadObjectFromData(scriptScanner);
        } else newBand = dataBase.getMusicBand();
    }

    public void addElement() {
        ServerStatusRegister.appleMusic.add(newBand);
        ServerStatusRegister.uniqueIdList.add(newBand.getId());
        if (newBand.getFrontMan() != null)
            ServerStatusRegister.passports.add(newBand.getFrontMan().getPassportID());
        report.getReports().add("Новый элемент успешно добавлен в коллекцию.");
    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        addElement();
        return report;
    }
}
