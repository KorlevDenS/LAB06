package commands;

import basic.objects.*;
import exceptions.InvalidDataFromFileException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class {@code JaxbManager} is used to realize opportunities
 * of JAXB in the current program. Objects of this class can
 * write down collections of objects to xml file or get them from it.
 */
public class JaxbManager {

    /**
     * Current file to read or to write down to.
     */
    private final File collectionLink;
    /**
     * JAXBContext object to use in methods.
     */
    private final JAXBContext context;

    public static boolean readingXml = false;

    /**
     * Constructs new JaxbManger object.
     *
     * @param file file to read or to write down to.
     */
    public JaxbManager(File file) throws JAXBException {
        this.collectionLink = file;
        this.context = JAXBContext.newInstance(Accumulator.class);
    }

    /**
     * Writes down collections of objects to xml file.
     *
     * @throws JAXBException if collection cannot be written down.
     */
    public void writeXml() throws JAXBException {
        Accumulator accumulator = new Accumulator();
        accumulator.CurrentBandSet = Accumulator.appleMusic;
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(accumulator, collectionLink);
    }

    /**
     * Reads collections of objects to xml file.
     *
     * @throws JAXBException if collection cannot be read.
     * @throws IOException   if something is wrong with current file.
     */
    public void readXml() throws JAXBException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(collectionLink));
        if (br.readLine() == null) {
            writeXml();
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Accumulator musicBands = (Accumulator) unmarshaller.unmarshal(new InputStreamReader(
                new FileInputStream(collectionLink), StandardCharsets.UTF_8));
        Accumulator.appleMusic = musicBands.getCurrentBandSet();
    }

    public void validateXmlData() {
        HashSet<MusicBand> checkSet = new HashSet<>(Accumulator.appleMusic);
        Accumulator.appleMusic.clear();
        for (MusicBand band : checkSet) {

            String validateString = band.toScriptString();
            Accumulator.scriptScanner = new ExecuteScript.ExecutionStringScanner(validateString);
            Accumulator.readingTheScript = true;
            readingXml = true;
            Add add = new Add(AvailableCommands.ADD);
            try {
                add.execute();
            } catch (InvalidDataFromFileException ex) {
                System.out.println("Загрузка прервана ошибкой: " + ex.getMessage());
                System.out.println("Объект " + band);
                Accumulator.readingTheScript = false;
                readingXml = false;
                continue;
            }
            System.out.println("Объект " + band);
            Accumulator.readingTheScript = false;
            readingXml = false;
        }
    }

    public static void idValidation() {
        for (Long id : Accumulator.uniqueIdList) {
            Set<MusicBand> removeSet = Accumulator.appleMusic.stream().filter(s -> id.equals(s.getId()))
                    .collect(Collectors.toSet());
            if (removeSet.size() > 1) {
                removeSet.forEach(band -> Accumulator.appleMusic.remove(band));
                System.out.println("Элементы с одинаковыми ID недопустимы и были удалены:");
                removeSet.forEach(band -> System.out.println("Объект: " + band.toString()));
            }
        }
    }

    public static void passwordValidation() {
        for (String passport : Accumulator.passports) {
            Set<MusicBand> removeSet = Accumulator.appleMusic.stream()
                    .filter(s -> (s.getFrontMan() != null)&&(Objects.equals(passport, s.getFrontMan().getPassportID())))
                    .collect(Collectors.toSet());
            if (removeSet.size() > 1) {
                removeSet.forEach(band -> Accumulator.appleMusic.remove(band));
                System.out.println("Элементы с одинаковыми паролями фронтменов недопустимы и были удалены:");
                removeSet.forEach(band -> System.out.println("Объект: " + band.toString()));
            }
        }
    }
}