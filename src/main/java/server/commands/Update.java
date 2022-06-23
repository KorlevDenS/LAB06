package server.commands;

import common.TransportedData;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.interfaces.Operand;
import server.interfaces.RemovingIf;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class {@code Update} is used for creating command "update" object,
 * that allows user to update data of existing {@link MusicBand}
 * object by inputted id.
 */
public class Update extends Add implements Operand, RemovingIf {

    /**
     * Field for id to update {@code MusicBand} objects by.
     * Is always completed by {@link Update#installOperand(String)}.
     */
    private long idToUpdateBy;

    /**
     * Constructs new Update object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Update(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.UPDATE)
            throw new IncorrectDataForObjectException("Class Update cannot perform this task");
    }

    public void installOperand(String stringRepresentation) {
        idToUpdateBy = Long.parseLong(stringRepresentation);
    }

    private PreparedStatement makeUpdateStatement(Connection conn) throws SQLException {
        String updateString =
                "UPDATE music_bands SET " +
                        "band_name = ?, " +
                        "coordinate_x = ?, " +
                        "coordinate_y = ?, " +
                        "creation_date = ?, " +
                        "number_of_participants = ?, " +
                        "music_genre = ?, " +
                        "frontman_name = ?, " +
                        "frontman_birthday = ?, " +
                        "height = ?, " +
                        "weight = ?, " +
                        "passport_id = ? " +
                        "WHERE user_id = ? AND band_id = ?";
        PreparedStatement stat = conn.prepareStatement(updateString);
        stat.setString(1, newBand.getName());
        stat.setInt(2, newBand.getCoordinates().getX());
        stat.setDouble(3, newBand.getCoordinates().getY());
        stat.setString(4, newBand.getCreationDate());
        stat.setLong(5, newBand.getNumberOfParticipants());
        stat.setString(6, String.valueOf(newBand.getGenre()));
        if (newBand.getFrontMan() != null) {
            stat.setString(7, newBand.getFrontMan().getName());
            stat.setString(8, newBand.getFrontMan().getBirthday());
            stat.setLong(9, newBand.getFrontMan().getHeight());
            stat.setInt(10, newBand.getFrontMan().getWeight());
            stat.setString(11, newBand.getFrontMan().getPassportID());
        } else {
            stat.setString(7, null);
            stat.setString(8, null);
            stat.setLong(9, 0);
            stat.setInt(10, 0);
            stat.setString(11, null);
        }
        stat.setLong(12, getClientId());
        stat.setLong(13, idToUpdateBy);
        return stat;
    }

    public void analyseAndRemove() throws InvalidDataFromFileException {
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            PreparedStatement stat = makeUpdateStatement(conn);
            int updateCount = stat.executeUpdate();
            stat.close();
            conn.close();
            manager.sqlCollectionToMemory();
            if (updateCount > 0) report.getReports().add("Элемент с id " + idToUpdateBy + " успешно обновлён.");
            else report.getReports().add("Вашего Элемента с таким ID в не было найдено в коллекции.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка обновления в базе данных: " + e.getMessage());
            else report.getReports().add("Ошибка обновления в базе данных: " + e.getMessage());
        }
    }

    @Override
    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        loadElement();
        analyseAndRemove();
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }

}
