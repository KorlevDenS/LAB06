package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
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
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
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

    public void execute() {
        loadElement();
        if (isLoaded) {
            addElement();
            if (isAdded) {
                System.out.println("Новый элемент оказался больше всех имеющихся в коллекции.");
                System.out.println("Он успешно добавлен в неё.");
            } else {
                System.out.println("В коллекции есть элементы больше данного.");
                System.out.println("Элемент в неё не добавлен.");
            }
        } else System.out.println("Элемент в коллекцию не добавлен");
    }
}
