import basic.objects.Accumulator;
import basic.objects.MusicBand;
import commands.CommandManager;
import commands.JaxbManager;
import exceptions.InvalidDataFromFileException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidDataFromFileException {
        Accumulator.appleMusic = new HashSet<>();
        Accumulator.current = new Date();
        //Accumulator.currentXml = new File(System.getenv("COLLECTION_FILE"));
        Accumulator.currentXml = new File("src/main/resources/MusicBandCollections.xml");
        try {
            JaxbManager manager = new JaxbManager(Accumulator.currentXml);
            manager.readXml();
        } catch (JAXBException | IOException e) {
            System.out.println("Не удалось загрузить коллекцию из файла, возможно данные представлены.");
            System.out.println("Попробуйте исправить их вручную или удалите (команда clear, затем save).");
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
    public static void idValidation() {
        for (Long id : Accumulator.uniqueIdList) {
            int count = 0;
            ArrayList<MusicBand> bandsToRemove = new ArrayList<>();
            for (MusicBand band : Accumulator.appleMusic)
                if (id.equals(band.getId())) count++;
            if (count > 1) {
                for (MusicBand band : Accumulator.appleMusic)
                    if (band.getId().equals(id))
                        bandsToRemove.add(band);
                Accumulator.uniqueIdList.remove(id);
                for (MusicBand bandToRemove : bandsToRemove) {
                    Accumulator.appleMusic.remove(bandToRemove);
                }
                System.out.println("Элементы с одинаковыми ID недопустимы и были удалены.");
            }
        }
    }

        private static void passwordValidation() {
        for (String passport : Accumulator.passports) {
            int count = 0;
            ArrayList<MusicBand> bandsToRemove = new ArrayList<>();
            for (MusicBand band : Accumulator.appleMusic)
                if (band.getFrontMan() != null) {
                    if (band.getFrontMan().getPassportID().equals(passport)) count++;
                }
            if (count > 1) {
                for (MusicBand band : Accumulator.appleMusic)
                    if (band.getFrontMan().getPassportID().equals(passport))
                        bandsToRemove.add(band);
                Accumulator.passports.remove(passport);
                for (MusicBand bandToRemove : bandsToRemove) {
                    Accumulator.appleMusic.remove(bandToRemove);
                }
                System.out.println("Элементы с одинаковыми паролями фронтменов недопустимы и были удалены.");
            }
        }
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
