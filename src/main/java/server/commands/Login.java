package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;
import server.DataBaseManager;
import server.PasswordEncryptor;
import server.ResponseHandler;
import server.ServerDataInstaller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Login extends Register {

    public Login(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.LOGIN)
            throw new IncorrectDataForObjectException("Class Login cannot perform this task");
    }

    protected boolean passwordMatches() throws SQLException, IOException, NoSuchAlgorithmException {
        String passwordToCheck = "";
        String hashPassword;
        long id = 0;
        DataBaseManager manager = new DataBaseManager();
        Connection conn = manager.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        statement.setString(1, login);
        ResultSet res = statement.executeQuery();
        if(res.next()) {
            passwordToCheck = res.getString("password");
            id = res.getLong("user_id");
        }
        res.close();
        statement.close();
        conn.close();
        hashPassword = PasswordEncryptor.getHash(password, "SHA-256");
        setClientId(id);
        return Objects.equals(hashPassword, passwordToCheck);
    }

    public synchronized void execute(ObjectOutputStream sendToClient) throws InvalidDataFromFileException {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        try {
            if (loginAlreadyExists()) {
                if (passwordMatches())
                    report.getReports().add("Успешный вход в аккаунт.");
                else report.getReports().add("Неверный пароль.");
            } else report.getReports().add("Пользователя с таким логином не существует.");
        } catch (SQLException | IOException | NoSuchAlgorithmException e) {
            if (isReadingTheScript())
                throw new InvalidDataFromFileException("Ошибка входа в аккаунт в базе данных: " + e.getMessage());
            else report.getReports().add("Ошибка входа в аккаунт в базе данных: " + e.getMessage());
        }
        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
