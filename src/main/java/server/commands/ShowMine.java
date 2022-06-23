package server.commands;

import common.AvailableCommands;
import common.ResultPattern;
import common.TransportedData;
import common.basic.MusicBand;
import common.exceptions.IncorrectDataForObjectException;
import server.ResponseHandler;
import server.ServerDataInstaller;
import server.ServerStatusRegister;

import java.io.ObjectOutputStream;

public class ShowMine extends Show {

    public ShowMine(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.SHOW_MINE)
            throw new IncorrectDataForObjectException("Class ShowMine cannot perform this task");
    }

    public void execute(ObjectOutputStream sendToClient) {
        report = new ResultPattern();
        report.getReports().add(getClientId() + " ");
        if (!ServerStatusRegister.appleMusic.isEmpty()) {
            report.getReports().add("Все элементы коллекции пользователя " + getClientId() +":");
            ServerStatusRegister.appleMusic.stream().filter(s -> s.getClientId() == getClientId()).map(MusicBand::toString)
                    .forEach(s -> report.getReports().add(s));
        } else {
            report.getReports().add("В коллекции ещё нет элементов.");
        }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        if (!isReadingTheScript())
            new ResponseHandler(sendToClient, newData, report).start();
    }
}
