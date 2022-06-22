import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class MainT {

    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = getConnection();
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT INTO users (login, password)" +
                "values('144', 'IJDJIDOJK')");
        stat.close();
        conn.close();
    }

    public static void runTest() throws SQLException, IOException {
        try {

            Connection conn = getConnection();
            Statement stat = conn.createStatement();

            ResultSet res = stat.executeQuery("SELECT * FROM music_bands");
            while (res.next()) {
                int user_id = res.getInt("user_id");
                int band_id = res.getInt("band_id");
                String band_name = res.getString("band_name");
                int coordinate_x = res.getInt("coordinate_x");
                double coordinate_y = res.getDouble("coordinate_y");
                String creation_date = res.getString("creation_date");
                int number_of_participants = res.getInt("number_of_participants");
                String music_genre = res.getString("music_genre");
                String frontman_name = res.getString("frontman_name");
                String frontman_birthday = res.getString("frontman_birthday");
                long height = res.getLong("height");
                int weight = res.getInt("weight");
                String passport_id = res.getString("passport_id");
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/helios_db.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);
        String url = props.getProperty("url_address");
        String username = props.getProperty("user");
        String password = props.getProperty("password");
        return DriverManager.getConnection("jdbc:h2:C:\\Users\\mvideo\\Desktop\\ProgLab07\\db\\stockExchange");
    }


}
