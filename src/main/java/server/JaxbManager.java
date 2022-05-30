package server;

import common.basic.MusicBand;
import server.commands.Add;
import server.commands.ExecuteScript;
import common.AvailableCommands;
import common.exceptions.InvalidDataFromFileException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
     * JAXBContext object to use in methods.
     */
    private final JAXBContext context;

    public static boolean readingXml = false;

    public ArrayList<String> loadInfo = new ArrayList<>();

    /**
     * Constructs new JaxbManger object.
     */
    public JaxbManager() throws JAXBException {
        this.context = JAXBContext.newInstance(ServerStatusRegister.class);
    }

    /**
     * Writes down collections of objects to xml file.
     *
     * @throws JAXBException if collection cannot be written down.
     */
    public void writeXml() throws JAXBException {
        ServerStatusRegister accumulator = new ServerStatusRegister();
        accumulator.currentBandSet = ServerStatusRegister.appleMusic;
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        marshaller.marshal(accumulator, o);
        ServerStatusRegister.xmlData = o.toByteArray();
    }

    /**
     * Reads collections of objects to xml file.
     */
    public void readXml(byte[] xmlData) throws JAXBException {
        if (xmlData.length != 0) {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ServerStatusRegister musicBands = (ServerStatusRegister) unmarshaller.unmarshal(new InputStreamReader(
                    new ByteArrayInputStream(xmlData), StandardCharsets.UTF_8));
            ServerStatusRegister.appleMusic = musicBands.getCurrentBandSet();
        }
    }

    private void validateXmlData() {
        HashSet<MusicBand> checkSet = new HashSet<>(ServerStatusRegister.appleMusic);
        ServerStatusRegister.appleMusic.clear();
        for (MusicBand band : checkSet) {

            String validateString = band.toScriptString();
            ServerStatusRegister.scriptScanner = new ExecuteScript.ExecutionStringScanner(validateString);
            ServerStatusRegister.readingTheScript = true;
            readingXml = true;
            Add add = new Add(AvailableCommands.ADD);
            try {
                add.execute();
            } catch (InvalidDataFromFileException ex) {
                loadInfo.add("Загрузка прервана ошибкой: " + ex.getMessage());
                loadInfo.add("Объект " + band);
                ServerStatusRegister.readingTheScript = false;
                readingXml = false;
                continue;
            }
            loadInfo.add("Объект " + band);
            ServerStatusRegister.readingTheScript = false;
            readingXml = false;
        }
        loadInfo.add(0,"Загруженные элементы:");
    }

    private void idValidation() {
        for (Long id : ServerStatusRegister.uniqueIdList) {
            Set<MusicBand> removeSet = ServerStatusRegister.appleMusic.stream().filter(s -> id.equals(s.getId()))
                    .collect(Collectors.toSet());
            if (removeSet.size() > 1) {
                removeSet.forEach(band -> ServerStatusRegister.appleMusic.remove(band));
                loadInfo.add("Элементы с одинаковыми ID недопустимы и были удалены:");
                removeSet.forEach(band -> loadInfo.add("Объект: " + band.toString()));
            }
        }
    }

    private void passwordValidation() {
        for (String passport : ServerStatusRegister.passports) {
            Set<MusicBand> removeSet = ServerStatusRegister.appleMusic.stream()
                    .filter(s -> (s.getFrontMan() != null) && (Objects.equals(passport, s.getFrontMan().getPassportID())))
                    .collect(Collectors.toSet());
            if (removeSet.size() > 1) {
                removeSet.forEach(band -> ServerStatusRegister.appleMusic.remove(band));
                loadInfo.add("Элементы с одинаковыми паролями фронтменов недопустимы и были удалены:");
                removeSet.forEach(band -> loadInfo.add("Объект: " + band.toString()));
            }
        }
    }

    public ArrayList<String> validateXmlCollection() {
        validateXmlData();
        idValidation();
        passwordValidation();
        return this.loadInfo;
    }
}