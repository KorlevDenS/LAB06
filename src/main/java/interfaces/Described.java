package interfaces;

/**
 * Interface {@code Described} requires implementing classes
 * to realise getting description.
 */
public interface Described {
    /**
     * @return the description of the operation it is used in.
     */
    String getDescription();
}