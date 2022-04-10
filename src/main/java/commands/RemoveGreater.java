package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;
import interfaces.RemovingIf;

import java.util.ArrayList;

/**
 * Class {@code RemoveGreater} is used for creating command "remove_greater" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * that are greater than inputted one.
 */
public class RemoveGreater extends Add implements RemovingIf {

    /**
     * The {@code ArrayList} with bands to remove.
     */
    private ArrayList<MusicBand> bandsToRemove;

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
        bandsToRemove = new ArrayList<>();
        for (MusicBand band : Accumulator.appleMusic) {
            if (newBand.compareTo(band) < 0) {
                bandsToRemove.add(band);
            }
        }
        for (MusicBand b : bandsToRemove) {
            Accumulator.appleMusic.remove(b);
        }
    }

    public void execute() throws InvalidDataFromFileException {
        loadElement();
        analyseAndRemove();
        if (!bandsToRemove.isEmpty())
            System.out.println("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        else System.out.println("Ни один из элементов не превышает данный. Ничего не было удалено.");
        bandsToRemove.clear();
    }

    public String getDescription() {
        return this.description;
    }
}
