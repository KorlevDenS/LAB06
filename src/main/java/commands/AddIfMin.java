package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
import java.util.Collections;

/**
 * Class AddIfMin is used for creating command "add_if_min" object,
 * that add {@code MusicBand} object in the {@code HashSet} if the object
 * is smaller than all elements in this {@code HashSet}.
 */
public class AddIfMin extends Add {

    /** becomes {@code true} if {@link AddIfMin#addElement()} adds new
     * element to the collection.*/
    private boolean isAdded;

    /**
     * Constructs new AddIfMin object.
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
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

    public void execute() {
        loadElement();
        if (isLoaded) {
            addElement();
            if (isAdded) {
                System.out.println("Новый элемент оказался меньше всех имеющихся в коллекции.");
                System.out.println("Он успешно добавлен в неё.");
            } else {
                System.out.println("В коллекции есть элементы меньше данного.");
                System.out.println("Элемент в неё не добавлен.");
            }
        } else System.out.println("Элемент в коллекцию не добавлен");
    }
}
