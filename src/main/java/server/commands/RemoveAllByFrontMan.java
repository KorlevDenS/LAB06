package server.commands;

import common.TransportedData;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import common.basic.Person;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.RemovingIf;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class RemoveAllByFrontMan is used for creating command "remove_all_by_front_man" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}
 * with {@link Person} frontMan is the same as in input.
 */
public class RemoveAllByFrontMan extends Command implements RemovingIf {

    /**
     * {@link Person} frontMan from input to remove all {@code MusicBand} objects
     * with the same one.
     */
    private Person frontManToRemoveBy;

    /**
     * Constructs new RemoveAllByFrontMan object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public RemoveAllByFrontMan(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REMOVE_ALL_BY_FRONT_MAN)
            throw new IncorrectDataForObjectException("Class RemoveAllByFrontMan cannot perform this task");
    }

    /**
     * Loads {@link Person} object from script or from {@code System.in}
     * to remove by. Method can stop the execution of the script if catches
     * a mistake of reading it.
     */
    public void loadFrontManFromData() throws InvalidDataFromFileException {
        if (isReadingTheScript()) {
            ScriptDataLoader loader = new ScriptDataLoader();
            frontManToRemoveBy = loader.loadFrontManFromData(false, scriptScanner);
        } else frontManToRemoveBy = dataBase.getFrontMan();
    }

    public void analyseAndRemove() throws InvalidDataFromFileException {
        int deleteCount;
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            if (frontManToRemoveBy == null) {
                PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ? " +
                        "AND frontman_name IS NULL");
                stat.setLong(1, getClientId());
                stat.executeUpdate();
                stat.close();
                conn.close();
                manager.sqlCollectionToMemory();
                report.getReports().add("Были удалены все ваши группы без фронтмена.");
                return;
            }
            if (frontManToRemoveBy.getPassportID() != null) {
                PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ? " +
                        "AND passport_id = ?");
                stat.setLong(1, getClientId());
                stat.setString(2, frontManToRemoveBy.getPassportID());
                deleteCount = stat.executeUpdate();
                stat.close();
            } else {
                PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ? " +
                        "AND passport_id IS NULL");
                stat.setLong(1, getClientId());
                deleteCount = stat.executeUpdate();
                stat.close();
            }
            conn.close();
            manager.sqlCollectionToMemory();
            if (deleteCount == 0) {
                report.getReports().add("Ни в одной вашей группе в коллекции не нашлось такого фронтмена. Ничего не было удалено.");
            } else {
                report.getReports().add("Была успешно удалена группа с фронтменом имеющим номер паспорта: "
                        + frontManToRemoveBy.getPassportID());
            }
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка удаления из базы данных: " + e.getMessage());
            else report.getReports().add("Ошибка удаления из базы данных: " + e.getMessage());
        }
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadFrontManFromData();
        analyseAndRemove();
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

}
