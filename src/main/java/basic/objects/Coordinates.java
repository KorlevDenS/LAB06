package basic.objects;

import java.io.Serializable;

/**
 * Class {@code Coordinates} makes an object that represents
 * position of something using rectangular cartesian coordinate system.
 */
public class Coordinates implements Serializable {

    /**
     * {@code Integer} field keeps coordinate X, cannot be more than 381.
     */
    private Integer x;
    /**
     * {@code double} field keeps coordinate Y, cannot be more than 381.
     */
    private double y;

    /**
     * Constructs {@code Coordinates} object for Xml.
     */
    public Coordinates() {
    }

    /**
     * Constructs a new position
     */
    public Coordinates(int x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Used for Xml files.
     *
     * @return X to write down to or to read from xml.
     */
    public Integer getX() {
        return this.x;
    }

    /**
     * Used for Xml files.
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @return the field {@link Coordinates#y}.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Used for Xml files.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return position of the object.
     */
    public String getPosition() {
        return "X = " + x + ", Y = " + y;
    }

    /**
     * Compares this object to the specified object.
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
        Coordinates otherObj = (Coordinates) obj;
        return x.equals(otherObj.x)
                && (y == otherObj.y);
    }

    /**
     * Returns a hash code using values of {@link Coordinates#x} and {@link Coordinates#y}.
     *
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.x.hashCode()
                + (int) this.y * 100;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Coordinates} value.
     *
     * @return a string representation of the value of this object.
     */
    public String toString() {
        return getClass().getName()
                + ";X=" + x
                + ";Y=" + y;
    }
}
