package commands;

import basic.objects.Accumulator;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;

/**
 * Class Clear is used for creating command "clear" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}.
 */
public class Clear extends Command {

    /**
     * Constructs new Clear object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Clear(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.CLEAR)
            throw new IncorrectDataForObjectException("Class Clear cannot perform this task");
    }

    public ResultPattern execute() {
        Accumulator.appleMusic.clear();
        Accumulator.passports.clear();
        Accumulator.uniqueIdList.clear();
        report.getReports().add("Из коллекции были удалены все элементы.");
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
