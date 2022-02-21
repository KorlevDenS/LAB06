package interfaces;

/**
 * Interface {@code RemovingIf} requires implementing classes
 * to realise removing objects from collection according to the conditions.
 */
public interface RemovingIf {
    /**
     * This method checks certain conditions and
     * removes objects from {@code HashSet} depending on the results.
     */
    void analyseAndRemove();
}
