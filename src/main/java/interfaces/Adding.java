package interfaces;

import basic.objects.Accumulator;
import basic.objects.MusicBand;
import commands.DataLoader;
import exceptions.InvalidDataFromFileException;

/**
 * Interface {@code Adding} requires implementing classes
 * to realise adding new elements to the collection.
 */
public interface Adding {
    /**
     * Loads new MusicBand object and adds it to the {@code HashSet}.
     * The method uses {@link DataLoader#loadObjectFromData()} to load the object.
     */
    void addElement() throws InvalidDataFromFileException;

    /**
     * Loads new {@link  MusicBand} objects from data from script or from
     * {@code System.in}, depends on {@link Accumulator#readingTheScript}.
     */
    void loadElement() throws InvalidDataFromFileException;
}
