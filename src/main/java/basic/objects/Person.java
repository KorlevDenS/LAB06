package basic.objects;

import java.util.Objects;

/**
 * Class {@code Person} makes an object that represents a Person and keeps its data.
 */
public class Person {

    /** Keeps name of the person. Cannot be {@code null} or empty {@code String}.*/
    private final String name;
    /** Person's birthday in zoned time. Can be unknown ({@code null}).*/
    private java.time.ZonedDateTime birthday;
    /** Person's height. Should be greater than 0.*/
    private final long height;
    /** Person's weight. Should be greater than 0.*/
    private final int weight;
    /**
     * Unique Passport id of {@code Person} object. Can be unknown ({@code null}).
     * The length of Passport id cannot be longer than 29 symbols.
     */
    private final String passportID;

    /**
     * Constructs a new Person with unknown birthday.
     * @see Person#Person(String,long,int,java.time.ZonedDateTime,String)
     */
    public Person(String name, long height, int weight, String passportID) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
    }

    /**
     * Constructs a new Person.
     * @param name for {@link Person#name}
     * @param height for {@link Person#height}
     * @param weight for {@link Person#weight}
     * @param birthday for {@link Person#birthday}
     * @param passportID for {@link Person#passportID}
     */
    public Person(String name, long height, int weight, java.time.ZonedDateTime birthday, String passportID) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.passportID = passportID;
    }

    /**
     * Compares this object to the specified object.
     * All fields are compared. For fields that can be null method
     * uses {@link Objects#equals(Object, Object)} method.
     * @param obj the object to compare with.
     * @return {@code true} if the objects are the same;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass())
            return false;
        Person otherObj = (Person) obj;
        return Objects.equals(passportID, otherObj.passportID)
                && name.equals(otherObj.name)
                && (height == otherObj.height)
                && (weight == otherObj.weight)
                && Objects.equals(birthday, otherObj.birthday);
    }

    /**
     * Returns a hash code using values of {@code Person} fields
     * that cannot be {@code null}.
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.name.hashCode()
                + this.passportID.hashCode()
                + 31 * (int) this.height
                + 31 * this.weight;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Person} value.
     * @return a string representation of the value of this object.
     */
    public String toString() {
        return getClass().getName()
                + ";name=" + name
                + ";birthday=" + birthday
                + ";height=" + height
                + ";weight=" + weight
                + ";passportID=" + passportID
                + "]";
    }
}
