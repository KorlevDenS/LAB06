package server.commands;

import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class Clear is used for creating command "clear" object,
 * that removes all {@code MusicBand} objects from the {@code HashSet}.
 */
public class Clear extends Command {

    /**
     * Constructs new Clear object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Clear(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.CLEAR)
            throw new IncorrectDataForObjectException("Class Clear cannot perform this task");
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM music_bands WHERE user_id = ?");
            stat.setLong(1, getClientId());
            stat.executeUpdate();
            stat.close();
            conn.close();
            manager.sqlCollectionToMemory();
            report.getReports().add("Из коллекции были удалены все ваши элементы.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка удаления из базы данных: " + e.getMessage());
            else report.getReports().add("Ошибка удаления из базы данных: " + e.getMessage());
        }
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
