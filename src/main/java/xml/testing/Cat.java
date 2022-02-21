package xml.testing;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Cat")

@XmlType(propOrder = {"id", "name", "weight" , "tail"})
public class Cat {

    private String name;
    private int weight;
    private int id;
    private Tail tail;

    public Cat() {
    }

    public Cat(String name, int weight, int id, Tail tail){
        this.name = name;
        this.weight= weight;
        this.id = id;
        this.tail = tail;
    }

    @XmlAttribute
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public Tail getTail() {
        return this.tail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }
}
