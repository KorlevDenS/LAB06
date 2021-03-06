import common.exceptions.InvalidDataFromFileException;
import server.ClientThreadHandler;
import server.DataBaseManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class ConnectionAcceptModule {

    public static void main(String[] args) throws IOException, SQLException, InvalidDataFromFileException {

        //тут загрузка коллекции из бд
        DataBaseManager manager = new DataBaseManager();
        manager.sqlCollectionToMemory();

        try (ServerSocket server = new ServerSocket(6789)) {
            System.out.print("Сервер начал слушать клиентов: " + "Порт " + server.getLocalPort() +
                    " / Адрес " + InetAddress.getLocalHost() + ".\nОжидаем подключения клиентов");

            Thread pointer = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(" .");
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            pointer.setDaemon(true);
            pointer.start();

            while (true) {
                Socket incoming = server.accept();
                pointer.interrupt();
                System.out.println(incoming + " подключился к серверу.");
                new ClientThreadHandler(incoming).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
