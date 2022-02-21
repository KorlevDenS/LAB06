package xml.testing;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JaxbWriter {

    public static void main(String[] args) throws JAXBException {
        Tail tail1 = new Tail(23);
        Cat cat2 = new Cat("Grusha", 10, 15000, tail1);

        File file = new File("Test.xml");
        JAXBContext context = JAXBContext.newInstance(Cat.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(cat2, file);

    }
}
