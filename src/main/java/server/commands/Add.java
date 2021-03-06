package server.commands;

import common.TransportedData;
import server.DataBaseManager;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.interfaces.Adding;
import common.basic.MusicBand;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.InvalidDataFromFileException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class {@code Add} is used for creating command "add" objects,
 * that add {@code MusicBand} objects in the {@code HashSet}.
 */
public class Add extends Command implements Adding {

    /**
     * Constructs new Add object.
     *
     * @param command enum constant from {@link AvailableCommands}
     */
    public Add(AvailableCommands command) {
        super(command);
    }

    /**
     * A field to keep successfully loaded {@code MusicBand}.
     */
    protected MusicBand newBand;

    public void loadElement() throws InvalidDataFromFileException {
        if (isReadingTheScript()) {
            ScriptDataLoader loader = new ScriptDataLoader();
            newBand = loader.loadObjectFromData(scriptScanner);
        } else newBand = dataBase.getMusicBand();
    }

    public void addElement() throws InvalidDataFromFileException {
        try {
            DataBaseManager manager = new DataBaseManager();
            Connection conn = manager.getConnection();
            PreparedStatement stat = manager.musicBandToSQLString(conn, newBand, getClientId());
            stat.executeUpdate();
            stat.close();
            conn.close();
            manager.sqlCollectionToMemory();
            report.getReports().add("Новый элемент успешно добавлен в коллекцию.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка добавления в базу данных: " + e.getMessage());
            else report.getReports().add("Ошибка добавления в базу данных: " + e.getMessage());
        }
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        loadElement();
        addElement();

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
