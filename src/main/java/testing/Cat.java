package testing;

import java.io.Serializable;
import java.util.Scanner;

public class Cat implements Serializable {

    public String name;
    public int weight;
    Scanner s;

    public Cat(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public void setS(Scanner s) {
        this.s = s;
    }

    public void scan() {
        s.nextLine();
    }
}
