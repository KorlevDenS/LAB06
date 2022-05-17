package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class {@code GroupCountingByFrontMan} is used for creating
 * "group_counting_by_front_man" command objects, that
 * divides the {@code HashSet} with {@code MusicBand} objects
 * into groups: with frontMan and without.
 */
public class GroupCountingByFrontMan extends Command {

    /**
     * {@code Set} for {@link MusicBand} objects with {@code frontMan} is not null.
     */
    private List<MusicBand> bandsWithFrontMan;
    /**
     * {@code Set} for {@link MusicBand} objects with {@code frontMan} = null.
     */
    private List<MusicBand> bandsWithNoFrontMan;

    /**
     * Constructs new GroupCountingByFrontMan object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
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
        Map<Boolean, List<MusicBand>> map = Accumulator.appleMusic.stream().collect(Collectors
                .partitioningBy((MusicBand s) -> s.getFrontMan() == null));
        bandsWithNoFrontMan = map.get(true);
        bandsWithFrontMan = map.get(false);
    }

    public ResultPattern execute() {
        if (!Accumulator.appleMusic.isEmpty()) {
            groupByFrontMan();
            report.getReports().add("Групп с фронтменом: " + bandsWithFrontMan.size());
            report.getReports().add("Групп без фронтмена: " + bandsWithNoFrontMan.size());
        } else {
            report.getReports().add("Групп с фронтменом: 0; Групп без фронтмена: 0.");
            report.getReports().add("В коллекции ещё нет элементов.");
        }
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
