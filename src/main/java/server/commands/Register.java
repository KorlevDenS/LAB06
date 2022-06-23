package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;
import server.DataBaseManager;
import server.PasswordEncryptor;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.interfaces.Operand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Register extends Command implements Operand {

    protected String login;

    protected String password;

    public Register(AvailableCommands command) {
        super(command);
    }

    public void installOperand(String stringRepresentation) {
        String[] parts = stringRepresentation.split(" ");
        login = parts[0].trim();
        password = parts[1].trim();
    }

    protected boolean loginAlreadyExists() throws SQLException, IOException {
        int counter = 0;
        DataBaseManager manager = new DataBaseManager();
        Connection conn = manager.getConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT COUNT(*) AS total FROM users WHERE login = ?");
        stat.setString(1, login);
        ResultSet res = stat.executeQuery();
        if (res.next())
            counter = res.getInt("total");
        res.close();
        stat.close();
        conn.close();
        return counter > 0;
    }

    protected boolean passwordIsTooShort() {
        if (password.length() < 8) {
            report.getReports().add("Пароль должен содержать не менее 8 символов.");
            return true;
        } else return false;
    }


    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        try {
            if (!loginAlreadyExists()) {
                if (!passwordIsTooShort()){
                    DataBaseManager manager = new DataBaseManager();
                    Connection conn = manager.getConnection();
                    PreparedStatement stat = conn.prepareStatement("INSERT INTO users (login, password)" +
                            "values (?, ?)");
                    stat.setString(1, login);
                    stat.setString(2, PasswordEncryptor.getHash(password, "SHA-256"));
                    stat.executeUpdate();
                    stat.close();
                    PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
                    statement.setString(1, login);
                    ResultSet res = statement.executeQuery();
                    if (res.next()) {
                        long id = res.getLong("user_id");
                        setClientId(id);
                    }
                    res.close();
                    statement.close();
                    conn.close();
                    report.getReports().add("Успешная регистрация, ваш номер пользователя: " + getClientId());
                }
            } else report.getReports().add("Аккаунт с таким логином уже существует, придумайте другой логин " +
                    "или войдите, если это ваш аккаунт.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            report.getReports().add("Алгоритм обработки паролей на сервере неисправен.");
        } catch (SQLException | IOException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка регистрации в базе данных: " + e.getMessage());
            else report.getReports().add("Ошибка регистрации в базе данных: " + e.getMessage());
        }
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
