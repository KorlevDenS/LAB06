package commands;

import basic.objects.Accumulator;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;

import java.util.Collections;

/**
 * Class AddIfMin is used for creating command "add_if_min" object,
 * that add {@code MusicBand} object in the {@code HashSet} if the object
 * is smaller than all elements in this {@code HashSet}.
 */
public class AddIfMin extends Add {

    /**
     * becomes {@code true} if {@link AddIfMin#addElement()} adds new
     * element to the collection.
     */
    private boolean isAdded;

    /**
     * Constructs new AddIfMin object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public AddIfMin(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.ADD_IF_MIN)
            throw new IncorrectDataForObjectException("Class AddIfMin cannot perform this task");
    }

    /**
     * Adds a new MusicBand object to the {@code HashSwt} if is smaller than
     * all elements in the collection or if the collection {@code isEmpty()}.
     */
    @Override
    public void addElement() {
        if (Accumulator.appleMusic.isEmpty()) {
            Accumulator.appleMusic.add(newBand);
            isAdded = true;
        } else {
            if (newBand.compareTo(Collections.min(Accumulator.appleMusic)) < 0) {
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
            report.getReports().add("Новый элемент оказался меньше всех имеющихся в коллекции.");
            report.getReports().add("Он успешно добавлен в неё.");
        } else {
            report.getReports().add("В коллекции есть элементы меньше данного.");
            report.getReports().add("Элемент в неё не добавлен.");
        }
        return report;
    }
}
