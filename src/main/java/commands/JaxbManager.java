package commands;

import basic.objects.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class JaxbManager {

    private final File collectionLink;
    private final JAXBContext context;

    public JaxbManager(File file) throws JAXBException {
        this.collectionLink = file;
        this.context = JAXBContext.newInstance(Accumulator.class);
    }

    public void writeXml() throws JAXBException {

        Accumulator accumulator = new Accumulator();
        accumulator.CurrentBandSet = Accumulator.appleMusic;
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(accumulator, collectionLink);
    }

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