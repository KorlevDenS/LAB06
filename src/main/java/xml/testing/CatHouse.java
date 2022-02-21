package xml.testing;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;

@XmlRootElement(name = "CATHOUSE")
public class CatHouse {

    @XmlElementWrapper(name = "cathouse")
    @XmlElement(name = "kitten")
    public HashSet<Cat> catSet = new HashSet<>();

    //public void setCatSet(HashSet<Cat> catSet){
    //    this.catSet = catSet;
    //}

    public HashSet<Cat> getCatSet(){
        return this.catSet;
    }
}
