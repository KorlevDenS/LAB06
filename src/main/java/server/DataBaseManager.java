package server;

import common.basic.Coordinates;
import common.basic.MusicBand;
import common.basic.MusicGenre;
import common.basic.Person;
import common.exceptions.InvalidDataFromFileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

public class DataBaseManager {

    public void sqlCollectionToMemory() throws SQLException, IOException, InvalidDataFromFileException {
        HashSet<MusicBand> sqlSet = new HashSet<>();
        Connection conn = getConnection();
        Statement stat = conn.createStatement();
        ResultSet res = stat.executeQuery("SELECT * FROM music_bands");
        while (res.next()) {
            MusicBand band = new MusicBand();
            Coordinates coordinates = new Coordinates();
            Person frontMan = new Person();
            band.setClientId(res.getLong("user_id"));
            band.setId(res.getLong("band_id"));
            band.setName(res.getString("band_name"));
            coordinates.setX(res.getInt("coordinate_x"));
            coordinates.setY(res.getDouble("coordinate_y"));
            band.setCoordinates(coordinates);
            band.setCreationDate(res.getString("creation_date"));
            band.setNumberOfParticipants(res.getInt("number_of_participants"));
            band.setGenre(MusicGenre.valueOf(res.getString("music_genre")));
            frontMan.setName(res.getString("frontman_name"));
            if (frontMan.getName() == null) {
                band.setFrontMan(null);
                sqlSet.add(band);
            } else {
                frontMan.setBirthday(res.getString("frontman_birthday"));
                frontMan.setHeight(res.getLong("height"));
                frontMan.setWeight(res.getInt("weight"));
                frontMan.setPassportID(res.getString("passport_id"));
                band.setFrontMan(frontMan);
                sqlSet.add(band);
            }
        }
        res.close();
        stat.close();
        conn.close();
        ServerStatusRegister.appleMusic = sqlSet;
    }

    public PreparedStatement musicBandToSQLString(Connection conn, MusicBand band, long clientId) throws SQLException {
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

        String prepSql =
                "INSERT INTO music_bands (" +
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
                "passport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stat = conn.prepareStatement(prepSql);
        stat.setLong(1, clientId);
        stat.setString(2, band_name);
        stat.setInt(3, coordinate_x);
        stat.setDouble(4, coordinate_y);
        stat.setString(5,creation_date);
        stat.setLong(6, number_of_participants);
        stat.setString(7, music_genre);
        stat.setString(8, frontman_name);
        stat.setString(9, frontman_birthday);
        stat.setLong(10, height);
        stat.setInt(11, weight);
        stat.setString(12, passport_id);

        return stat;
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
