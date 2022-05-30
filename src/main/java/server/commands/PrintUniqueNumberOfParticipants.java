package server.commands;

import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;

/**
 * Class {@code PrintUniqueNumberOfParticipants} is used for creating
 * command "print_unique_number_of_participants" object,
 * that prints unique numbers of participants of {@code MusicBand} objects
 * of {@code HashSet}.
 */
public class PrintUniqueNumberOfParticipants extends Command {

    /**
     * Constructs new {@code PrintUniqueNumberOfParticipants} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public PrintUniqueNumberOfParticipants(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.PRINT_UNIQUE_NUMBER_OF_PARTICIPANTS) {
            throw new IncorrectDataForObjectException("Class PrintUniqueNumberOfParticipants cannot perform this task");
        }
    }

    public ResultPattern execute() {
        report = new ResultPattern();
        if (!ServerStatusRegister.appleMusic.isEmpty()) {
            report.getReports().add("Список уникальных значений количества участников в группах:");
            ServerStatusRegister.appleMusic.stream().map(MusicBand::getNumberOfParticipants)
                    .distinct().forEach(s -> report.getReports().add(s.toString()));
        } else {
            report.getReports().add("Уникальных значений нет, так как в коллекции еще нет элементов.");
        }
        return report;
    }

    public String getDescription() {
        return this.description;
    }
}
