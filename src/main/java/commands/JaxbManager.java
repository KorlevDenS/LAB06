package commands;

import basic.objects.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Class {@code JaxbManager} is used to realize opportunities
 * of JAXB in the current program. Objects of this class can
 * write down collections of objects to xml file or get them from it.
 */
public class JaxbManager {

    /** Current file to read or to write down to.*/
    private final File collectionLink;
    /** JAXBContext object to use in methods.*/
    private final JAXBContext context;

    /**
     * Constructs new JaxbManger object.
     * @param file file to read or to write down to.
     */
    public JaxbManager(File file) throws JAXBException {
        this.collectionLink = file;
        this.context = JAXBContext.newInstance(Accumulator.class);
    }

    /** Writes down collections of objects to xml file.
     * @throws JAXBException if collection cannot be written down.
     */
    public void writeXml() throws JAXBException {
        Accumulator accumulator = new Accumulator();
        accumulator.CurrentBandSet = Accumulator.appleMusic;
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(accumulator, collectionLink);
    }

    /** Reads collections of objects to xml file.
     * @throws JAXBException if collection cannot be read.
     * @throws IOException if something is wrong with current file.
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
}