package xml.testing;

import basic.objects.MusicGenre;
import exceptions.ScanValidation;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Cat")

@XmlType(propOrder = {"id", "name", "weight" , "tail", "genre"})
public class Cat {

    private MusicGenre genre;
    private String name;
    private int weight;
    private int id;
    private Tail tail;

    public Cat() {
    }

    public Cat(String name, int weight, int id, Tail tail, MusicGenre genre){
        this.name = name;
        this.weight= weight;
        this.id = id;
        this.tail = tail;
        this.genre = genre;
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

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public MusicGenre getGenre() {
        return this.genre;
    }

    @Override

    public String toString(){
        return getClass().getName()
                + "[id=" + id
                + ";name=" + name
                + ";weight=" + weight
                + ";tail=" + tail
                + ";genre=" + genre;
    }
}
