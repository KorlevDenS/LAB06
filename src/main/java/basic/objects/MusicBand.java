package basic.objects;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class {@code MusicBand} makes an object that represents a music
 * band and keeps its data.
 */
public class MusicBand implements Comparable<MusicBand> {

    /**
     * {@code ArrayList} keeping {@code Long} values is used for generating unique
     * {@link MusicBand#id} in method {@link MusicBand#generateId()}
     */
    private static final ArrayList<Long> uniqueIdList = new ArrayList<>();
    /**
     * Unique id of {@code MusicBand} object. The field shouldn't be {@code null}
     * and should be more than 0. Its value is generated automatically
     * by method {@link MusicBand#generateId().}
     */
    private final Long id;
    /** Name of {@code MusicBand} object. Cannot be {@code null} or empty.*/
    private final String name;
    /** Position on of {@code MusicBand} object. Cannot be {@code null}.*/
    private final Coordinates coordinates;
    /**
     * Creation date of music band. The field shouldn't be {@code null}.
     * Its value is generated automatically by method {@link MusicBand#generateDate().}
     */
    private final java.time.LocalDate creationDate;
    /** Number of music band participants. Should be greater than 0.*/
    private final long numberOfParticipants;
    /** Music genre of music band from {@link MusicGenre}. Cannot be {@code null}.*/
    private final MusicGenre genre;
    /** FrontMan of the music band. Can be {@code null}.*/
    private final Person frontMan;

    /**
     * Constructs a new music band.
     *
     * @param name - for {@link MusicBand#name}
     * @param coordinates - for {@link MusicBand#coordinates}
     * @param numberOfParticipants - for {@link MusicBand#numberOfParticipants}
     * @param genre - for {@link MusicBand#genre}
     * @param frontMan - for {@link MusicBand#frontMan}
     */
    public MusicBand(String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre, Person frontMan) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.frontMan = frontMan;
        this.creationDate = generateDate();
        this.id = generateId();
    }

    /**
     * Special constructor to use only for updating {@code MusicBand} object in Collections.
     * @param id can be taken only from {@code MusicBand} objects with an already generated id.
     */
    public MusicBand(String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre,
                     Person frontMan, long id) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.creationDate = generateDate();
        this.frontMan = frontMan;
        this.id = id;
    }

    /**
     * Generates unique {@link MusicBand#id} using {@link MusicBand#uniqueIdList}
     * not to repeat the id of already created music band.
     * @return unique id of MusicBand.
     */
    private Long generateId() {
        Long i = (long) (Math.random() * 100000 + 1);
        while (uniqueIdList.contains(i)) {
            i = (long) (Math.random() * 100000 + 1);
        }
        uniqueIdList.add(i);
        return i;
    }

    /**
     * Generates a date of music band creation since 1970
     * up to last year at the time of execution.
     * @return creation date of the music band.
     */
    private java.time.LocalDate generateDate() {
        LocalDate freshDate = LocalDate.now();
        int maxYear = freshDate.getYear();
        int creationYear = (int) ((Math.random() * (maxYear - 1970)) + 1970);
        int creationMonth = (int) ((Math.random() * (13 - 1)) + 1);
        int creationDay = (int) ((Math.random() * (29 - 1)) + 1);
        return LocalDate.of(creationYear, creationMonth, creationDay);
    }

    /** @return {@link MusicBand#numberOfParticipants} of the object.*/
    public long getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /** @return {@link  MusicBand#frontMan} of the object.*/
    public Person getFrontMan() {
        return this.frontMan;
    }

    /** @return {@link MusicBand#id} of the object.*/
    public Long getId() {
        return this.id;
    }

    /**
     * Compares two MusicBand objects using numbers of their participants.
     * @param anotherBand {@link MusicBand#numberOfParticipants} of another
     *                    {@code MusicBand} object to compare with this one.
     * @return numerical difference.
     */
    @Override
    public int compareTo(MusicBand anotherBand) {
        return (int) (this.numberOfParticipants - anotherBand.numberOfParticipants);
    }

    /**
     * Compares this object to the specified object.
     *
     * @param obj the object to compare with.
     * @return {@code true} if the unique {@link MusicBand#id}s
     * of the objets are the same, {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass())
            return false;
        MusicBand otherObj = (MusicBand) obj;
        return id.equals(otherObj.id);
    }

    /**
     * Returns a hash code using values of not {@code null} MusicBand fields.
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.name.hashCode()
                + this.id.hashCode()
                + this.coordinates.hashCode()
                + this.genre.hashCode();
    }

    /**
     * Returns a {@code String} object representing this {@code MusicBand} value.
     * @return a string representation of the value of this object.
     */
    public String toString() {
        return getClass().getName()
                + "[id=" + id
                + ";name=" + name
                + ";creationDate=" + creationDate
                + ";coordinates=" + coordinates
                + ";numberOfParticipants=" + numberOfParticipants
                + ";genre=" + genre
                + ";frontMan=" + frontMan
                + "]";
    }
}
