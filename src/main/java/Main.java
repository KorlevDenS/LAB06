import common.basic.Coordinates;
import common.basic.MusicBand;
import common.basic.MusicGenre;
import common.basic.Person;
import server.DataBaseManager;
import server.PasswordEncryptor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class Main {

    static Scanner commandScanner = new Scanner(System.in);

    public static final String DB_URL = "jdbc:h2:C:\\Users\\mvideo\\Desktop\\ProgLab07\\db\\stockExchange";
    public static final String DB_Driver = "org.h2.Driver";

    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {

        MusicBand band = new MusicBand("group1", new Coordinates(12, 12), 122, MusicGenre.BRIT_POP, null);

        DataBaseManager manager = new DataBaseManager();
        Connection conn = manager.getConnection();
        PreparedStatement stat = manager.musicBandToSQLString(conn, band, 144);
        stat.executeUpdate();
        stat.close();
        conn.close();


    }

}
