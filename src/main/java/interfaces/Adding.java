package interfaces;

import commands.DataLoader;

/**
 * Interface {@code Adding} requires implementing classes
 * to realise adding new elements to the collection.
 */
public interface Adding {
    /**
     * Loads new MusicBand object and adds it to the {@code HashSet}.
     * The method uses {@link DataLoader#loadObjectFromData()} to load the object.
     */
    void addElement();
}
