package basic.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

@XmlRootElement(name = "ACCUMULATOR")
public class Accumulator {

    public static HashSet<MusicBand> appleMusic = new HashSet<>();
    public static Date current;

    public static boolean readingTheScript = false;

    public static Scanner fileScanner;
    public static File currentScript;

    /**
     * {@code ArrayList} keeping {@code String} values is used for checking
     * for uniqueness of user's input of passportIds when creating
     * new {@link Person} objects.
     */
    public static final ArrayList<String> passports = new ArrayList<>();

    /**
     * {@code ArrayList} keeping {@code Long} values is used for generating unique
     * {@code  MusicBand#id} in method of {@code  MusicBand#generateId()}
     */
    public static ArrayList<Long> uniqueIdList = new ArrayList<>();

    @XmlElementWrapper(name = "CollectionKeeper")
    @XmlElement(name = "MusicBand")
    public HashSet<MusicBand> CurrentBandSet = new HashSet<>();

    public HashSet<MusicBand> getCurrentBandSet(){
        return this.CurrentBandSet;
    }

}
