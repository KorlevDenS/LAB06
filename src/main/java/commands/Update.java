package commands;

import basic.objects.*;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;
import interfaces.Operand;
import interfaces.RemovingIf;

/**
 * Class {@code Update} is used for creating command "update" object,
 * that allows user to update data of existing {@link MusicBand}
 * object by inputted id.
 */
public class Update extends Add implements Operand, RemovingIf {

    /**
     * Field for id to update {@code MusicBand} objects by.
     * Is always completed by {@link Update#installOperand(String)}.
     */
    private long idToUpdateBy;
    /**
     * Becomes {@code true} after removing old data successfully.
     */
    private boolean isRemoved;

    /**
     * Constructs new Update object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Update(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.UPDATE)
            throw new IncorrectDataForObjectException("Class Update cannot perform this task");
    }

    public void installOperand(String stringRepresentation) {
        idToUpdateBy = Long.parseLong(stringRepresentation);
    }

    public void analyseAndRemove() {
        isRemoved = false;
        MusicBand bandToRemove = Accumulator.appleMusic.stream()
                .filter(s -> s.getId().equals(idToUpdateBy)).findFirst().orElse(null);
        if (bandToRemove != null) {
            Accumulator.appleMusic.remove(bandToRemove);
            isRemoved = true;
        }
    }

    @Override
    public ResultPattern execute() throws InvalidDataFromFileException {
        installOperand(dataBase.getOperand());
        loadElement();
        newBand.setId(idToUpdateBy);
        analyseAndRemove();
        if (isRemoved) {
            addElement();
            report.getReports().add("Элемент с ID = " + idToUpdateBy + " успешно обновлён.");
        } else report.getReports().add("Элемента с таким ID в не было найдено в коллекции.");
        return report;
    }

}
