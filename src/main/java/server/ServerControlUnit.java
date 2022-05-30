package server;

import common.CompleteMessage;
import common.InstructionPattern;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerControlUnit {

    static DatagramChannel dc;
    static ByteBuffer buf;
    static int port = 6789;
    static SocketAddress adr;

    public ServerControlUnit() throws IOException, ClassNotFoundException, InvalidDataFromFileException, JAXBException {
        dc = DatagramChannel.open();
        call();
    }


    public void call() throws IOException, ClassNotFoundException, InvalidDataFromFileException, JAXBException {
        adr = new InetSocketAddress(port);

        //связка канала с адресом
        dc.bind(adr);

        buf = ByteBuffer.allocate(1048576);
        adr = dc.receive(buf);

        ByteArrayInputStream i = new ByteArrayInputStream(buf.array());
        ObjectInputStream in = new ObjectInputStream(i);
        CompleteMessage receivedMessage = (CompleteMessage) in.readObject();

        ServerDataInstaller installer = new ServerDataInstaller(receivedMessage.getTransportedData());
        installer.installFromTransported();
        InstructionPattern instructionPattern = receivedMessage.getInstructionPattern();

        ResultPattern resultPattern;
        try {
            if (receivedMessage.getTransportedData().getXmlData() != null)
                installXmlData(receivedMessage.getTransportedData().getXmlData());
            ServerCommandManager commandManager = new ServerCommandManager(instructionPattern);
            resultPattern = commandManager.execution(commandManager.instructionFetch());
        } catch (JAXBException e) {
            resultPattern = new ResultPattern();
            resultPattern.getReports().add("Файл xml содержит ошибки и не может быть загружен в коллекцию. \n" +
                    "При выполнении команды exit файл будет перезаписан на основе выполнения следующих команд. \n" +
                    "Текущее содержимое файла во избежании потери информации: \n" +
                    new String(receivedMessage.getTransportedData().getXmlData()));
        }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        CompleteMessage sendingMessage = new CompleteMessage(newData, resultPattern);
        System.out.println(resultPattern.getReports()); //testing receive

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(o);
        out.writeObject(sendingMessage);
        byte[] buff = o.toByteArray();

        buf = ByteBuffer.wrap(buff);
        dc.send(buf, adr);

        dc.close();
        new ServerControlUnit();

    }

    public static void installXmlData(byte[] xmlData) throws JAXBException {
        JaxbManager manager = new JaxbManager();
        manager.readXml(xmlData);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidDataFromFileException, JAXBException {
        new ServerControlUnit();
    }
}
