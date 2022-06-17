package server.commands;

import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.RemovingIf;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class {@code RemoveGreater} is used for creating command "remove_greater" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * that are greater than inputted one.
 */
public class RemoveGreater extends Add implements RemovingIf {

    /**
     * The {@code ArrayList} with bands to remove.
     */
    private Set<MusicBand> bandsToRemove;

    /**
     * Constructs new RemoveGreater object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveGreater(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_GREATER)
            throw new IncorrectDataForObjectException("Class RemoveGreater cannot perform this task");
    }

    public void analyseAndRemove() {
        bandsToRemove = ServerStatusRegister.appleMusic.stream()
                .filter(s -> newBand.compareTo(s) < 0).collect(Collectors.toSet());
        bandsToRemove.forEach(band -> ServerStatusRegister.passports.remove(band.getFrontMan().getPassportID()));
        bandsToRemove.forEach(band -> ServerStatusRegister.uniqueIdList.remove(band.getId()));
        bandsToRemove.forEach(band -> ServerStatusRegister.appleMusic.remove(band));

    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        analyseAndRemove();
        if (!bandsToRemove.isEmpty())
            report.getReports().add("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        else report.getReports().add("Ни один из элементов не превышает данный. Ничего не было удалено.");
        bandsToRemove.clear();
        return report;
    }

}
