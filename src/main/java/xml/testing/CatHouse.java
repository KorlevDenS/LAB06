package xml.testing;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

@XmlRootElement(namespace = "CAT_HOUSE")
public class CatHouse {

    public HashSet<Cat> catSet;
}
