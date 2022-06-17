package server;

import common.CompleteMessage;
import common.InstructionPattern;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class ClientThreadHandler extends Thread {

    private Socket incoming;
    private int clientID;

    public ClientThreadHandler(Socket incoming) throws IOException {
        this.incoming = incoming;
    }

    public void run() {
        try (ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
             ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            sendToClient.writeObject("Cоединение установлено");

            while (true) {
                try {
                    CompleteMessage receivedMessage = (CompleteMessage) getFromClient.readObject();
                    ServerDataInstaller installer = new ServerDataInstaller(receivedMessage.getTransportedData());
                    installer.installFromTransported();

                    InstructionPattern instructionPattern = receivedMessage.getInstructionPattern();
                    ResultPattern resultPattern;
                    ServerCommandManager commandManager = new ServerCommandManager(instructionPattern);
                    resultPattern = commandManager.execution(commandManager.instructionFetch());
                    TransportedData newData = ServerDataInstaller.installIntoTransported();
                    CompleteMessage sendingMessage = new CompleteMessage(newData, resultPattern);
                    sendToClient.writeObject(sendingMessage);


                } catch (SocketException | InvalidDataFromFileException e) {
                    System.out.println(incoming + " недоступен и отключён.");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(incoming + " отключён.");
        }
    }

}
