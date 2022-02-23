import basic.objects.Accumulator;
import basic.objects.MusicBand;
import commands.CommandManager;
import commands.JaxbManager;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        Accumulator.appleMusic = new HashSet<>();
        Accumulator.current = new Date();
        File file = new File("src/main/resources/MusicBandCollections.xml");
        JaxbManager manager = new JaxbManager(file);
        manager.readXml();
        for (MusicBand band : Accumulator.appleMusic) {
            Accumulator.uniqueIdList.add(band.getId());
            //if (band.getFrontMan() != null)
            //    Accumulator.passports.add(band.getFrontMan().getPassportID());
            // Код закомментирован для удобства тестирования программы
        }
        //testing();
        scanCommand();
    }

    public static void testing()  {

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
