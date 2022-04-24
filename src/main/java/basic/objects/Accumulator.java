package basic.objects;

import commands.ExecuteScript;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Class {@code Accumulator} is used to keep {@code static} collection
 * of {@code MusicBand} and also for variables that keeps data using
 * by various commands.
 * Objects of {@code Accumulator} are used to load current collection
 * to Xml file and to load elements from it in class {@link commands.JaxbManager}
 */
@XmlRootElement(name = "ACCUMULATOR")
public class Accumulator {

    /**
     * Main collection fot {@code MusicBand} objects. All commands
     * are created to work with it.
     */
    public static HashSet<MusicBand> appleMusic = new HashSet<>();

    /**
     * Keeps time of main collection initialisation. Is always loaded
     * in Main class when program starts and can be used by commands.
     */
    public static Date current;

    /**
     * Is {@code true} when program is executing script. Is used
     * to help commands understand what to do.
     */
    public static boolean readingTheScript = false;

    /**
     * Is always initialised at the beginning of script execution
     * and is used to read commands from it.
     */
    public static ExecuteScript.ExecutionStringScanner scriptScanner;

    /**
     * Keeps current Xml file read from and write down to.
     */
    public static File currentXml;


    /**
     * {@code ArrayList} keeping {@code String} values is used for checking
     * for uniqueness of user's input of passportIds when creating
     * new {@link Person} objects.
     */
    public static HashSet<String> passports = new HashSet<>();

    /**
     * {@code ArrayList} keeping {@code Long} values is used for generating unique
     * {@code  MusicBand#id} in method of {@code  MusicBand#generateId()}
     */
    public static HashSet<Long> uniqueIdList = new HashSet<>();

    @XmlElementWrapper(name = "CollectionKeeper")
    @XmlElement(name = "MusicBand")
    public HashSet<MusicBand> CurrentBandSet = new HashSet<>();

    /**
     * Used for Xml files.
     *
     * @return collection to write down to or to read from xml.
     */
    public HashSet<MusicBand> getCurrentBandSet() {
        return this.CurrentBandSet;
    }

}
