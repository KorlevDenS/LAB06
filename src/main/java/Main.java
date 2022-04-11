import basic.objects.Accumulator;
import basic.objects.MusicBand;
import commands.CommandManager;
import commands.JaxbManager;
import exceptions.InvalidDataFromFileException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import static commands.JaxbManager.*;

public class Main {
    public static void main(String[] args) throws InvalidDataFromFileException {
        Accumulator.appleMusic = new HashSet<>();
        Accumulator.current = new Date();
        try {
            Accumulator.currentXml = new File(System.getenv("COLLECTION_FILE"));
        } catch (NullPointerException e) {
            System.out.println("Необходимая переменная окружения не задана. \n" +
                    "Задайте переменную COLLECTION_FILE при помощи команды export c необходимым файлом xml.");
            System.exit(0);
        }
        //Accumulator.currentXml = new File("src/main/resources/MusicBandCollections.xml");
        try {
            JaxbManager manager = new JaxbManager(Accumulator.currentXml);
            manager.readXml();
            manager.validateXmlData();
        } catch (JAXBException e) {
            System.out.println("Не удалось загрузить коллекцию из файла, нарушен формат XML.");
        } catch (IOException e) {
            System.out.println("Не удалось загрузить коллекцию из файла, файл не существует или нечитаем");
        }
        for (MusicBand band : Accumulator.appleMusic) {
            Accumulator.uniqueIdList.add(band.getId());
            if (band.getFrontMan() != null)
                Accumulator.passports.add(band.getFrontMan().getPassportID());
        }

        idValidation();
        passwordValidation();
        scanCommand();
    }

    public static void scanCommand() throws InvalidDataFromFileException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            CommandManager manager = new CommandManager(line);
            manager.execution(manager.instructionFetch());
            line = scanner.nextLine();
        }

    }
}
