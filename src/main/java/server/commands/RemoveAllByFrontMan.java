package server.commands;

import common.TransportedData;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.basic.Person;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.RemovingIf;

import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class RemoveAllByFrontMan is used for creating command "remove_all_by_front_man" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * with {@link Person} frontMan is the same as in input.
 */
public class RemoveAllByFrontMan extends Command implements RemovingIf {

    /**
     * {@link Person} frontMan from input to remove all {@code MusicBand} objects
     * with the same one.
     */
    private Person frontManToRemoveBy;
    /**
     * The {@code ArrayList} with bands to remove.
     */
    private Set<MusicBand> bandsToRemove;

    /**
     * Constructs new RemoveAllByFrontMan object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveAllByFrontMan(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_ALL_BY_FRONT_MAN)
            throw new IncorrectDataForObjectException("Class RemoveAllByFrontMan cannot perform this task");
    }

    /**
     * Loads {@link Person} object from script or from {@code System.in}
     * to remove by. Method can stop the execution of the script if catches
     * a mistake of reading it.
     */
    public void loadFrontManFromData() throws InvalidDataFromFileException {
        if (isReadingTheScript()) {
            ScriptDataLoader loader = new ScriptDataLoader();
            frontManToRemoveBy = loader.loadFrontManFromData(false, scriptScanner);
        } else frontManToRemoveBy = dataBase.getFrontMan();
    }

    public void analyseAndRemove() {
        bandsToRemove = ServerStatusRegister.appleMusic.stream()
                .filter(s -> Objects.equals(s.getFrontMan(), frontManToRemoveBy)).collect(Collectors.toSet());
        bandsToRemove.forEach(band -> ServerStatusRegister.appleMusic.remove(band));
        bandsToRemove.forEach(band -> ServerStatusRegister.passports.remove(band.getFrontMan().getPassportID()));
    }

    public void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadFrontManFromData();
        analyseAndRemove();
        if (!bandsToRemove.isEmpty()) {
            if (frontManToRemoveBy == null)
                report.getReports().add("Удалению подверглись группы без фронтмена.");
            report.getReports().add("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        } else
            report.getReports().add("Ни в одной группе в коллекции не нашлось такого фронтмена. Ничего не было удалено.");

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

}
