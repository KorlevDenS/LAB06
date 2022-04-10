package commands;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import exceptions.IncorrectDataForObjectException;

import java.util.HashSet;

/**
 * Class {@code PrintUniqueNumberOfParticipants} is used for creating
 * command "print_unique_number_of_participants" object,
 * that prints unique numbers of participants of {@code MusicBand} objects
 * of {@code HashSet}.
 */
public class PrintUniqueNumberOfParticipants extends Command {

    /**
     * Set of unique numbers of participants in {@code MusicBand} objects
     * of {@code HashSet}.
     */
    private HashSet<Long> uniqueNumbers;

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

    /**
     * Finds unique numbers of participants in {@code MusicBand} objects
     * of {@code HashSet} and puts it into {@link PrintUniqueNumberOfParticipants#uniqueNumbers}.
     */
    private void findUniqueNumbers() {
        uniqueNumbers = new HashSet<>();
        for (MusicBand band : Accumulator.appleMusic) {
            Long number = band.getNumberOfParticipants();
            uniqueNumbers.add(number);
        }
    }

    public void execute() {
        if (!Accumulator.appleMusic.isEmpty()) {
            findUniqueNumbers();
            System.out.println("Список уникальных значений количества участников в группах:");
            for (Long number : uniqueNumbers) {
                System.out.println(number);
            }
        } else {
            System.out.println("Уникальных значений нет, так как в коллекции еще нет элементов.");
        }
    }

    public String getDescription() {
        return this.description;
    }
}
