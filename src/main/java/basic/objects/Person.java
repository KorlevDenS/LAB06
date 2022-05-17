package basic.objects;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Class {@code Person} makes an object that represents a Person and keeps its data.
 */
public class Person implements Serializable {

    /**
     * Keeps name of the person. Cannot be {@code null} or empty {@code String}.
     */
    private String name;
    /**
     * Person's birthday in zoned time. Can be unknown ({@code null}).
     */
    private java.time.ZonedDateTime birthday;
    /**
     * Person's height. Should be greater than 0.
     */
    private long height;
    /**
     * Person's weight. Should be greater than 0.
     */
    private int weight;
    /**
     * Unique Passport id of {@code Person} object. Can be unknown ({@code null}).
     * The length of Passport id cannot be longer than 29 symbols.
     */
    private String passportID;

    /**
     * Constructs {@code Person} object for Xml.
     */
    public Person() {
    }

    /**
     * Constructs a new Person with unknown birthday.
     *
     * @see Person#Person(String, long, int, java.time.ZonedDateTime, String)
     */
    public Person(String name, long height, int weight, String passportID) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
    }

    /**
     * Constructs a new Person.
     *
     * @param name       for {@link Person#name}
     * @param height     for {@link Person#height}
     * @param weight     for {@link Person#weight}
     * @param birthday   for {@link Person#birthday}
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
     * @param name to set field {@link Person#name}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link  Person#name} of the object.
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link  Person#weight} of the object.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight to set field {@link Person#weight}.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return {@link  Person#height} of the object.
     */
    public long getHeight() {
        return height;
    }

    /**
     * @param height to set field {@link Person#height}.
     */
    public void setHeight(long height) {
        this.height = height;
    }

    /**
     * @return {@link  Person#passportID} of the object.
     */
    public String getPassportID() {
        if (passportID == null) return null;
        return passportID;
    }

    /**
     * @param passportID to set field {@link Person#passportID}.
     */
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    /**
     * Parses string representation of frontMan birthday for
     * interaction with xml file. Class {@link ZonedDateTime}
     * is not adapted to work with JAXB.
     *
     * @param birth to set field {@link Person#birthday}.
     */
    public void setBirthday(String birth) {
        this.birthday = ZonedDateTime.parse(birth);
    }

    public String getBirthdayForScript() {
        if (this.birthday == null)
            return null;
        String[] times = this.getBirthday().split("[-T:.+\\[\\]]");
        int year = Integer.parseInt(times[0]);
        int month = Integer.parseInt(times[1]);
        int day = Integer.parseInt(times[2]);
        int hour = Integer.parseInt(times[3]);
        int minute = Integer.parseInt(times[4]);
        int second = Integer.parseInt(times[5]);
        int nanosecond = Integer.parseInt(times[6]);
        return year + " " + month + " " + day + " " + hour + " " + minute + " " + second + " " + nanosecond;
    }

    /**
     * Parses {@link ZonedDateTime} representation of frontMan birthday for
     * interaction with xml file. Class {@link ZonedDateTime}
     * is not adapted to work with JAXB.
     * Returns {@code null} if {@link Person#birthday} is {@code null}
     * because it is possible.
     *
     * @return string representation of {@link ZonedDateTime} object.
     */
    public String getBirthday() {
        if (this.birthday == null)
            return null;
        return birthday.toString();
    }


    /**
     * Compares this object to the specified object.
     * All fields are compared. For fields that can be null method
     * uses {@link Objects#equals(Object, Object)} method.
     *
     * @param obj the object to compare with.
     * @return {@code true} if the objects are the same;
     * {@code false} otherwise.
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
     *
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
     *
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
