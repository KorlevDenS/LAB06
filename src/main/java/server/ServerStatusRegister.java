package server;

import common.basic.MusicBand;
import common.basic.Person;

import java.util.HashSet;

/**
 * Class {@code ServerStatusRegister} is used to keep {@code static} collection
 * of {@code MusicBand} and also for variables that keeps data using
 * by various commands.
 */
public class ServerStatusRegister {

    /**
     * Main collection fot {@code MusicBand} objects. All commands
     * are created to work with it.
     */
    public static HashSet<MusicBand> appleMusic = new HashSet<>();

    /**
     * {@code ArrayList} keeping {@code String} values is used for checking
     * for uniqueness of user's input of passportIds when creating
     * new {@link Person} objects.
     */
    public static HashSet<String> passports = new HashSet<>();

}
