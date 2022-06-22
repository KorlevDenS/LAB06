package server.commands;

import common.TransportedData;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import server.interfaces.Operand;
import server.interfaces.RemovingIf;

import java.io.ObjectOutputStream;

/**
 * Class {@code RemoveById} is used for creating command "remove_by_id" object,
 * that removes {@code MusicBand} object from the {@code HashSet}
 * with the same id as id from input.
 */
public class RemoveByID extends Command implements Operand, RemovingIf {

    /**
     * Field for id to remove {@code MusicBand} objects by.
     * Is always completed by {@link RemoveByID#installOperand(String)}.
     */
    private long idToRemoveBy;

    /**
     * Constructs new RemoveById object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveByID(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_BY_ID)
            throw new IncorrectDataForObjectException("Class RemoveById cannot perform this task");
    }

    public void analyseAndRemove() {
        MusicBand bandToRemove = ServerStatusRegister.appleMusic.stream()
                .filter(s -> s.getId().equals(idToRemoveBy)).findFirst().orElse(null);
        if (bandToRemove != null) {
            if (bandToRemove.getFrontMan() != null)
                ServerStatusRegister.passports.remove(bandToRemove.getFrontMan().getPassportID());
            ServerStatusRegister.appleMusic.remove(bandToRemove);
            report.getReports().add("Элемент с ID = " + idToRemoveBy + " успешно удалён.");
        } else report.getReports().add("Элемента с таким ID в не было найдено в коллекции.");
    }

    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        analyseAndRemove();

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

    public void installOperand(String stringRepresentation) {
        idToRemoveBy = Long.parseLong(stringRepresentation);
    }
}
