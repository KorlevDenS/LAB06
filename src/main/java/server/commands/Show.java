package server.commands;

import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;

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
        report = new ResultPattern();
        if (!ServerStatusRegister.appleMusic.isEmpty()) {
            report.getReports().add("Все элементы коллекции:");
            ServerStatusRegister.appleMusic.stream().map(MusicBand::toString).forEach(s -> report.getReports().add(s));
        } else {
            report.getReports().add("В коллекции ещё нет элементов.");
        }
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
