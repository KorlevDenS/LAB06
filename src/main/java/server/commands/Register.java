package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.IncorrectDataForObjectException;
import server.PasswordEncryptor;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.interfaces.Operand;

import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

public class Register extends Command implements Operand {

    private String loginToInstall;

    private String passwordToInstall;

    public Register(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.REGISTER)
            throw new IncorrectDataForObjectException("Class Register cannot perform this task");
    }

    public void installOperand(String stringRepresentation) {
        String[] parts = stringRepresentation.split(" ");
        loginToInstall = parts[0].trim();
        passwordToInstall = parts[1].trim();
    }


    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        if (!isReadingTheScript())
            installOperand(dataBase.getOperand());
        try {
            String strongPassword = PasswordEncryptor.getHash(passwordToInstall, "SHA-256");
            report.getReports().add("Ваши данные: " + loginToInstall + " " + strongPassword);
            setClientId((long) ((Math.random() + 1 ) * 100));/////////////////////////////////////////
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            report.getReports().add("Алгоритм обработки паролей на сервере неисправен.");
        }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
