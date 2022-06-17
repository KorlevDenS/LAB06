import common.CompleteMessage;
import common.InstructionPattern;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;
import server.ServerCommandManager;
import server.ServerDataInstaller;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

@Deprecated
public class ServerUnit {

    static DatagramChannel dc;
    static ByteBuffer buf;
    static int port = 6789;
    static SocketAddress adr;
    static CompleteMessage receivedMessage;

    public ServerUnit() throws IOException, InvalidDataFromFileException, ClassNotFoundException {
        dc = DatagramChannel.open();
        call();
    }


    public void call() throws IOException, ClassNotFoundException, InvalidDataFromFileException {
        adr = new InetSocketAddress(port);

        dc.bind(adr);

        buf = ByteBuffer.allocate(1048576);
        adr = dc.receive(buf);

        ByteArrayInputStream i = new ByteArrayInputStream(buf.array());
        ObjectInputStream in = new ObjectInputStream(i);
        try {
            receivedMessage = (CompleteMessage) in.readObject();
        } catch (ClassNotFoundException e) {
            dc.close();
            new ServerUnit();
        }
        ServerDataInstaller installer = new ServerDataInstaller(receivedMessage.getTransportedData());
        installer.installFromTransported();
        formAndSendResult();
    }


    public void formAndSendResult() throws InvalidDataFromFileException, IOException, ClassNotFoundException {
        InstructionPattern instructionPattern = receivedMessage.getInstructionPattern();
        ResultPattern resultPattern;
            ServerCommandManager commandManager = new ServerCommandManager(instructionPattern);
            try {
                resultPattern = commandManager.execution(commandManager.instructionFetch());
            } catch (InvalidDataFromFileException e) {
                dc.close();
                new ServerUnit();
                return;
            }

        TransportedData newData = ServerDataInstaller.installIntoTransported();
        CompleteMessage sendingMessage = new CompleteMessage(newData, resultPattern);

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(o);
        out.writeObject(sendingMessage);
        byte[] buff = o.toByteArray();

        buf = ByteBuffer.wrap(buff);
        dc.send(buf, adr);

        dc.close();
        new ServerUnit();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidDataFromFileException {
        new ServerUnit();
    }
}
