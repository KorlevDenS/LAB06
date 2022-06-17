package server.interfaces;

import server.ServerStatusRegister;
import common.basic.MusicBand;
import common.exceptions.InvalidDataFromFileException;

/**
 * Interface {@code Adding} requires implementing classes
 * to realise adding new elements to the collection.
 */
public interface Adding {

    void addElement() throws InvalidDataFromFileException;

    /**
     * Loads new {@link  MusicBand} objects from data from script
     */
    void loadElement() throws InvalidDataFromFileException;
}
