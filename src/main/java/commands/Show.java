package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;

/**
 * Class {@code Show} is used for showing all current elements of
 * {@link MusicBand} objects {@code HashSet} in {@code String} interpretation.
 */
public class Show extends Command {

    /**
     * Constructs new {@code Show} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Show(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.SHOW)
            throw new IncorrectDataForObjectException("Class Show cannot perform this task");
    }

    public ResultPattern execute() {
        if (!Accumulator.appleMusic.isEmpty()) {
            report.getReports().add("Все элементы коллекции:");
            Accumulator.appleMusic.stream().map(MusicBand::toString).forEach(s -> report.getReports().add(s));
        } else {
            report.getReports().add("В коллекции ещё нет элементов.");
        }
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
