package server;

import common.basic.MusicBand;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DataBaseManager {

    public void sqlCollectionToMemory(){}

    public String musicBandToSQLString(MusicBand band, long clientId) throws SQLException {
        String frontman_name;
        String frontman_birthday;
        long height;
        int weight;
        String passport_id;
        String band_name = band.getName();
        int coordinate_x = band.getCoordinates().getX();
        double coordinate_y = band.getCoordinates().getY();
        String creation_date = band.getCreationDate();
        long number_of_participants = band.getNumberOfParticipants();
        String music_genre = band.getGenre().toString();
        if (band.getFrontMan() == null) {
            frontman_name = null;
            frontman_birthday = null;
            height = 0;
            weight = 0;
            passport_id = null;
        } else {
            frontman_name = band.getFrontMan().getName();
            frontman_birthday = band.getFrontMan().getBirthday();
            height = band.getFrontMan().getHeight();
            weight = band.getFrontMan().getWeight();
            passport_id = band.getFrontMan().getPassportID();
        }

        return "INSERT INTO music_bands (" +
                "user_id, " +
                "band_name, " +
                "coordinate_x, " +
                "coordinate_y, " +
                "creation_date, " +
                "number_of_participants, " +
                "music_genre, " +
                "frontman_name, " +
                "frontman_birthday, " +
                "height, " +
                "weight, " +
                "passport_id) VALUES (" +
                clientId + ", " +
                "'" + band_name + "', " +
                coordinate_x + ", " +
                coordinate_y + ", " +
                "'" + creation_date + "', " +
                number_of_participants + ", " +
                "'" + music_genre + "', " +
                "'" + frontman_name + "', " +
                "'" + frontman_birthday + "', " +
                height + ", " +
                weight + ", " +
                "'" + passport_id + "')";
    }

    public Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/helios_db.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", "org.h2.Driver");
            //System.setProperty("jdbc.drivers", drivers);
        String url = props.getProperty("url_address");
        String username = props.getProperty("user");
        String password = props.getProperty("password");
        //return DriverManager.getConnection(url, username, password);
        return DriverManager.getConnection("jdbc:h2:C:\\Users\\mvideo\\Desktop\\ProgLab07\\db\\stockExchange");
    }

}
