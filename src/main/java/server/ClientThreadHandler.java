package server;

import common.CompleteMessage;
import common.InstructionPattern;
import common.ResultPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;
import server.commands.Command;
import server.commands.CommandObjects;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class ClientThreadHandler extends Thread {

    private final Socket incoming;
    private long clientID;
    private boolean isRegistered = false;

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
                    InstructionPattern instructionPattern = receivedMessage.getInstructionPattern();

                    if (instructionPattern.getInstructionType().equals(CommandObjects.REGISTER.toString()) ||
                            instructionPattern.getInstructionType().equals(CommandObjects.LOGIN.toString())) {
                        ServerCommandManager commandManager = new ServerCommandManager(instructionPattern);
                        Command register = commandManager.instructionFetch();
                        register.execute(sendToClient);
                        if (register.getClientId() != 0) {
                            this.isRegistered = true;
                            this.clientID = register.getClientId();
                        }
                        continue;
                    }

                    if (!isRegistered && (!instructionPattern.getInstructionType().equals(CommandObjects.REGISTER.toString()))) {
                        ResultPattern resultPattern = new ResultPattern();
                        resultPattern.getReports().add("Зарегистрируйтесь или войдите" +
                                " прежде чем выполнять команды.");
                        TransportedData newData = ServerDataInstaller.installIntoTransported();
                        CompleteMessage sendingMessage = new CompleteMessage(newData, resultPattern);
                        sendToClient.writeObject(sendingMessage);
                        continue;
                    }

                    ////////// получение clientId
                    instructionPattern.setClientId(this.clientID);
                    new CommandThreadHandler(sendToClient, instructionPattern).start();

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
