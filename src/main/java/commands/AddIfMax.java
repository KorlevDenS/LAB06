package commands;

import basic.objects.Accumulator;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;

import java.util.Collections;

/**
 * Class AddIfMax is used for creating command "add_if_max" object,
 * that add {@code MusicBand} object in the {@code HashSet} if the object
 * is larger than all elements in this {@code HashSet}.
 */
public class AddIfMax extends Add {

    private boolean isAdded;

    /**
     * Constructs new AddIfMax object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public AddIfMax(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.ADD_IF_MAX)
            throw new IncorrectDataForObjectException("Class AddIfMax cannot perform this task");
    }

    /**
     * Adds a new MusicBand object to the {@code HashSwt} if is lager then
     * all elements in the collection or if the collection {@code isEmpty()}.
     */
    @Override
    public void addElement() {
        if (Accumulator.appleMusic.isEmpty()) {
            Accumulator.appleMusic.add(newBand);
            isAdded = true;
        } else {
            if (newBand.compareTo(Collections.max(Accumulator.appleMusic)) > 0) {
                Accumulator.appleMusic.add(newBand);
                isAdded = true;
            } else {
                isAdded = false;
            }
        }
    }

    public ResultPattern execute() throws InvalidDataFromFileException {
        loadElement();
        addElement();
        if (isAdded) {
            report.getReports().add("Новый элемент оказался больше всех имеющихся в коллекции.");
            report.getReports().add("Он успешно добавлен в неё.");
        } else {
            report.getReports().add("В коллекции есть элементы больше данного.");
            report.getReports().add("Элемент в неё не добавлен.");
        }
        return report;
    }
}
