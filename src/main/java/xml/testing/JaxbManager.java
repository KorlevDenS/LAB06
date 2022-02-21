package xml.testing;

import basic.objects.MusicGenre;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

public class JaxbManager {

    private static final String CATHOUSE_XML = "Test.xml";

    public static void writeXml() throws JAXBException {
        Tail tail1 = new Tail(23);
        Tail tail2 = new Tail(14);
        Tail tail3 = new Tail(40);
        Tail tail4 = new Tail(22);
        Cat cat2 = new Cat("Grusha", 10, 15000, tail1, MusicGenre.BRIT_POP);
        Cat cat3 = new Cat("Grush", 3, 13000, tail2, MusicGenre.BRIT_POP);
        Cat cat4 = new Cat("Gru", 9, 16000, tail3, MusicGenre.BRIT_POP);
        Cat cat5 = new Cat("Gr", 12, 11000, tail4, MusicGenre.BRIT_POP);

        CatHouse house = new CatHouse();

        house.catSet.add(cat2);
        house.catSet.add(cat3);
        house.catSet.add(cat4);
        house.catSet.add(cat5);

        File file = new File("Test.xml");
        JAXBContext context = JAXBContext.newInstance(CatHouse.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(house, file);
    }

    public static void readXml() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(CatHouse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CatHouse catHouse = (CatHouse) unmarshaller.unmarshal(new InputStreamReader(
                new FileInputStream(CATHOUSE_XML), StandardCharsets.UTF_8));

        HashSet<Cat> catHashSet = catHouse.getCatSet();
        System.out.println(catHashSet);
    }
}