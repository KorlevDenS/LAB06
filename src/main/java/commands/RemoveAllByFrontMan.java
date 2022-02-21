package commands;

import basic.objects.*;
import exceptions.IncorrectDataForObjectException;
import interfaces.RemovingIf;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class RemoveAllByFrontMan is used for creating command "remove_all_by_front_man" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * with {@link Person} frontMan is the same as in input.
 */
public class RemoveAllByFrontMan extends DataLoader implements RemovingIf {

    /** {@link Person} frontMan from input to remove all {@code MusicBand} objects
     * with the same one.*/
    private Person frontManToRemoveBy;
    /** The {@code ArrayList} with bands to remove.*/
    private final ArrayList<MusicBand> bandsToRemove = new ArrayList<>();

    /**
     * Constructs new RemoveAllByFrontMan object.
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
     */
    public RemoveAllByFrontMan(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_ALL_BY_FRONT_MAN)
            throw new IncorrectDataForObjectException("Class RemoveAllByFrontMan cannot perform this task");
    }

    public void analyseAndRemove() {
        frontManToRemoveBy = loadFrontMan();
        for (MusicBand band : Accumulator.appleMusic) {
            if (Objects.equals(band.getFrontMan(), frontManToRemoveBy)) {
                bandsToRemove.add(band);
            }
        }
        for (MusicBand b : bandsToRemove) {
            Accumulator.appleMusic.remove(b);
        }
    }

    public void execute() {
        analyseAndRemove();
        if (!bandsToRemove.isEmpty()) {
            if (frontManToRemoveBy == null)
                System.out.println("Удалению подверглись группы без фронтмена.");
            System.out.println("Было успешно удалено " + bandsToRemove.size() + " элементов.");
        } else System.out.println("Ни в одной группе в коллекции не нашлось такого фронтмена. Ничего не было удалено.");
        bandsToRemove.clear();
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Overrides {@link DataLoader#loadFrontManPassportID()}.
     * In this class Method does not check inputted id for uniqueness
     * because it is not used to add new {@code Person} anywhere.
     */
    @Override
    protected String loadFrontManPassportID() {
        System.out.println("Введите уникальный номер паспорта фронтмена группы.");
        System.out.println("Если он неизвестен - пропустите.");
        String frontManPassportId = scanner1.nextLine();
        if (frontManPassportId.equals("")) return null;
        while ((frontManPassportId.length() > 29)) {
            System.out.println("Длинна строки не должна превышать 29 символов, введите ее правильно.");
            frontManPassportId = scanner1.nextLine();
        }
        passports.add(frontManPassportId);
        return frontManPassportId;
    }
}
