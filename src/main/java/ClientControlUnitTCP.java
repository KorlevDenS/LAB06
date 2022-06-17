import client.ClientCommandManager;
import client.ClientDataInstaller;
import client.Demonstrator;
import common.CompleteMessage;
import common.InstructionPattern;
import common.TransportedData;
import common.exceptions.InvalidDataFromFileException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientControlUnitTCP {

    public static void main(String[] args) {
        while (true) {
            try (Socket outComing = new Socket(InetAddress.getLocalHost(), 6789)) {
                outComing.setSoTimeout(10000);
                try (ObjectOutputStream outputStream = new ObjectOutputStream(outComing.getOutputStream());
                     ObjectInputStream inputStream = new ObjectInputStream(outComing.getInputStream())) {
                    System.out.println(inputStream.readObject());
                    listenCommands(inputStream, outputStream);
                }
            } catch (IOException | InvalidDataFromFileException | ClassNotFoundException e) {
                System.err.println("Нет связи с сервером. Подключиться ещё раз ({да} или {нет})?");
                String answer;
                while (!(answer = new Scanner(System.in).nextLine()).equals("да")) {
                    switch (answer) {
                        case "":
                            break;
                        case "нет":
                            System.exit(0);
                            break;
                        default: System.out.println("Введите корректный ответ.");
                    }
                }
                System.out.print("Подключение ...");
                //TODO запоминает логин и пароль и автоматически подключается
            }
        }
    }

    public static void listenCommands(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, InvalidDataFromFileException {
        while (true) {
            Scanner commandScanner = new Scanner(System.in);
            String currentCommand = commandScanner.nextLine();
            ClientCommandManager commandManager = new ClientCommandManager(currentCommand);

            InstructionPattern instructionPattern = commandManager.execution(commandManager.instructionFetch());
            TransportedData transportedData = ClientDataInstaller.installIntoTransported();
            CompleteMessage sendingMessage = new CompleteMessage(transportedData, instructionPattern);
            out.writeObject(sendingMessage);

            CompleteMessage receivedMessage = (CompleteMessage) in.readObject();
            ClientDataInstaller clientDataInstaller = new ClientDataInstaller(receivedMessage.getTransportedData());
            clientDataInstaller.installFromTransported();
            Demonstrator demonstrator = new Demonstrator(receivedMessage.getResultPattern());
            demonstrator.demonstrateCommandResult();
            if (receivedMessage.getResultPattern().isTimeToExit()) {
                System.exit(0);
            }
        }
    }
}
