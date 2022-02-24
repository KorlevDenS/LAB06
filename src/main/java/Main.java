import basic.objects.Accumulator;
import basic.objects.MusicBand;
import commands.CommandManager;
import commands.JaxbManager;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Accumulator.appleMusic = new HashSet<>();
        Accumulator.current = new Date();
        Accumulator.currentXml = new File("src/main/resources/MusicBandCollections.xml");
        try {
            JaxbManager manager = new JaxbManager(Accumulator.currentXml);
            manager.readXml();
        } catch (JAXBException | IOException e) {
            System.out.println("Не удалось загрузить коллекцию из файла, возможно данные представлены.");
            System.out.println("Попробуйте Исправить их вручную или удалите (команда clear, затем save).");
        }
        for (MusicBand band : Accumulator.appleMusic) {
            Accumulator.uniqueIdList.add(band.getId());
            if (band.getFrontMan() != null)
                Accumulator.passports.add(band.getFrontMan().getPassportID());
        }
        scanCommand();
    }

    public static void scanCommand() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            CommandManager manager = new CommandManager(line);
            manager.execution(manager.instructionFetch());
            line = scanner.nextLine();
        }

    }
}
