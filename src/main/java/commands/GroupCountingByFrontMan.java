package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import exceptions.IncorrectDataForObjectException;

import java.util.HashSet;

/**
 * Class {@code GroupCountingByFrontMan} is used for creating
 * "group_counting_by_front_man" command objects, that
 * divides the {@code HashSet} with {@code MusicBand} objects
 * into groups: with frontMan and without.
 */
public class GroupCountingByFrontMan extends Command {

    /** {@code Hashset} for {@link MusicBand} objects with {@code frontMan} is not null.*/
    private HashSet<MusicBand> bandsWithFrontMan;
    /** {@code Hashset} for {@link MusicBand} objects with {@code frontMan} = null.*/
    private HashSet<MusicBand> bandsWithNoFrontMan;

    /**
     * Constructs new GroupCountingByFrontMan object.
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
     */
    public GroupCountingByFrontMan(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.GROUP_COUNTING_BY_FRONT_MAN)
            throw new IncorrectDataForObjectException("Class GroupCountingByFrontMan cannot perform this task");
    }

    /**
     * Divides the {@code HashSet} with {@code MusicBand} objects
     * into groups: with frontMan and without.
     */
    private void groupByFrontMan() {
        bandsWithFrontMan = new HashSet<>();
        bandsWithNoFrontMan = new HashSet<>();
        for (MusicBand band : Accumulator.appleMusic) {
            if (band.getFrontMan() == null) {
                bandsWithNoFrontMan.add(band);
            } else {
                bandsWithFrontMan.add(band);
            }
        }
    }

    public void execute() {
        if (!Accumulator.appleMusic.isEmpty()) {
            groupByFrontMan();
            System.out.println("Групп с фронтменом: " + bandsWithFrontMan.size());
            System.out.println("Групп без фронтмена: " + bandsWithNoFrontMan.size());
        } else {
            System.out.println("Групп с фронтменом: 0; Групп без фронтмена: 0.");
            System.out.println("В коллекции ещё нет элементов.");
        }
    }

    public String getDescription() {
        return this.description;
    }
}
