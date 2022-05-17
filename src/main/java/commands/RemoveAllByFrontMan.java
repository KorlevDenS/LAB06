package commands;

import basic.objects.*;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;
import interfaces.RemovingIf;

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
        if (Accumulator.readingTheScript) {
            ScriptDataLoader loader = new ScriptDataLoader();
            frontManToRemoveBy = loader.loadFrontManFromData(false);
        } else frontManToRemoveBy = dataBase.getFrontMan();
    }

    public void analyseAndRemove() {
        bandsToRemove = Accumulator.appleMusic.stream()
                .filter(s -> Objects.equals(s.getFrontMan(),frontManToRemoveBy)).collect(Collectors.toSet());
        bandsToRemove.forEach(band -> Accumulator.appleMusic.remove(band));
    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        loadFrontManFromData();
        analyseAndRemove();
        if (!bandsToRemove.isEmpty()) {
            if (frontManToRemoveBy == null)
                report.getReports().add("Удалению подверглись группы без фронтмена.");
            report.getReports().add("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        } else
            report.getReports().add("Ни в одной группе в коллекции не нашлось такого фронтмена. Ничего не было удалено.");
        return report;
    }

    public String getDescription() {
        return this.description;
    }

}
