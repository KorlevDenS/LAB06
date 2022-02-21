package commands;

import basic.objects.*;
import exceptions.IncorrectDataForObjectException;
import interfaces.Operand;
import interfaces.RemovingIf;

/**
 * Class {@code Update} is used for creating command "update" object,
 * that allows user to update data of existing {@link MusicBand}
 * object by inputted id.
 */
public class Update extends Add implements Operand, RemovingIf {

    /** Field for id to update {@code MusicBand} objects by.
     * Is always completed by {@link Update#installOperand(String)}.
     */
    private long idToUpdateBy;
    /** Becomes {@code true} after removing old data successfully.*/
    private boolean isRemoved = false;

    /**
     * Constructs new Update object.
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     * does not match this class.
     */
    public Update(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.UPDATE)
            throw new IncorrectDataForObjectException("Class Update cannot perform this task");
    }

    public void installOperand(String stringRepresentation) {
        idToUpdateBy = Long.parseLong(stringRepresentation);
    }

    public void analyseAndRemove() {
        for (MusicBand band : Accumulator.appleMusic) {
            if (band.getId() == idToUpdateBy) {
                Accumulator.appleMusic.remove(band);
                isRemoved = true;
                break;
            }
        }
    }

    @Override
    public void execute() {
        analyseAndRemove();
        if (isRemoved) {
            addElement();
            System.out.println("Элемент с ID = " + idToUpdateBy + " успешно обновлён.");
        } else System.out.println("Элемента с таким ID в не было найдено в коллекции.");
    }

    /** This version of {@link DataLoader#loadObjectFromData()} do not use
     * automatic id generation because it only updates existing object.
     *  @return {@link Person} object with updated fields.
     */
    public MusicBand loadObjectFromData() {
        String nameOfBand = loadBandName();
        Coordinates bandCoordinates = loadBandCoordinates();
        long numberOfParticipants = loadNumberOfParticipants();
        MusicGenre musicGenre = loadBandMusicGenre();
        Person frontMan = loadFrontMan();
        return new MusicBand(nameOfBand, bandCoordinates, numberOfParticipants,
                musicGenre, frontMan, idToUpdateBy);
    }
}
