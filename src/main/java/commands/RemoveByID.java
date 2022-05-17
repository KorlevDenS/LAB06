package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import interfaces.Operand;
import interfaces.RemovingIf;

/**
 * Class {@code RemoveById} is used for creating command "remove_by_id" object,
 * that removes {@code MusicBand} object from the {@code HashSet}
 * with the same id as id from input.
 */
public class RemoveByID extends Command implements Operand, RemovingIf {

    /**
     * Field for id to remove {@code MusicBand} objects by.
     * Is always completed by {@link RemoveByID#installOperand(String)}.
     */
    private long idToRemoveBy;

    /**
     * Constructs new RemoveById object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveByID(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_BY_ID)
            throw new IncorrectDataForObjectException("Class RemoveById cannot perform this task");
    }

    public void analyseAndRemove() {
        MusicBand bandToRemove = Accumulator.appleMusic.stream()
                .filter(s -> s.getId().equals(idToRemoveBy)).findFirst().orElse(null);
        if (bandToRemove != null) {
            Accumulator.appleMusic.remove(bandToRemove);
            report.getReports().add("Элемент с ID = " + idToRemoveBy + " успешно удалён.");
        } else report.getReports().add("Элемента с таким ID в не было найдено в коллекции.");
    }

    public ResultPattern execute() {
        installOperand(dataBase.getOperand());
        analyseAndRemove();
        return report;
    }

    public void installOperand(String stringRepresentation) {
        idToRemoveBy = Long.parseLong(stringRepresentation);
    }

    public String getDescription() {
        return this.description;
    }
}
