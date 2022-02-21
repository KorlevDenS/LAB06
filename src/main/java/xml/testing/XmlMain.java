package xml.testing;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class XmlMain {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JaxbManager.writeXml();
        JaxbManager.readXml();
    }
}
