package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;
import interfaces.RemovingIf;

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
        bandsToRemove = Accumulator.appleMusic.stream()
                .filter(s -> newBand.compareTo(s) < 0).collect(Collectors.toSet());
        bandsToRemove.forEach(band -> Accumulator.appleMusic.remove(band));
    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        loadElement();
        analyseAndRemove();
        if (!bandsToRemove.isEmpty())
            report.getReports().add("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        else report.getReports().add("Ни один из элементов не превышает данный. Ничего не было удалено.");
        bandsToRemove.clear();
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
