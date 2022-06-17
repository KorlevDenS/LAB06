package server;

import common.basic.MusicGenre;
import common.exceptions.InvalidDataFromFileException;
import server.commands.ExecuteScript;

public class ScriptScanValidation {

    /**
     * @return valid {@code int} from file.
     * @throws InvalidDataFromFileException if Scanner meets not {@code int} in file.
     */
    public synchronized static int ReadNextInt(ExecuteScript.ExecutionStringScanner scriptScanner) throws InvalidDataFromFileException {
        int obj;
        try {
            String str = scriptScanner.nextLine();
            obj = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw new InvalidDataFromFileException("При создании объекта вместо целого числа int введено что-то другое.");
        }
        return obj;
    }

    /**
     * @return valid {@code double} from file.
     * @throws InvalidDataFromFileException if Scanner meets not {@code double} in file.
     */
    public synchronized static double ReadNextDouble(ExecuteScript.ExecutionStringScanner scriptScanner) throws InvalidDataFromFileException {
        double obj;
        try {
            String str = scriptScanner.nextLine();
            obj = Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            throw new InvalidDataFromFileException("При создании объекта вместо вещественного числа введено что-то другое.");
        }
        return obj;
    }

    /**
     * @return valid {@code long} from file.
     * @throws InvalidDataFromFileException if Scanner meets not {@code int}
     *                                      or {@code long} in file.
     */
    public synchronized static long ReadNextLong(ExecuteScript.ExecutionStringScanner scriptScanner) throws InvalidDataFromFileException {
        long obj;
        try {
            String str = scriptScanner.nextLine();
            obj = Long.parseLong(str);
        } catch (NumberFormatException ex) {
            throw new InvalidDataFromFileException("При создании объекта вместо целого числа введено что-то другое.");
        }
        return obj;
    }

    /**
     * @return valid {@link MusicGenre} from file.
     * @throws InvalidDataFromFileException if Scanner meets not {@link MusicGenre} in file.
     */
    public synchronized static MusicGenre ReadNextGenre(ExecuteScript.ExecutionStringScanner scriptScanner) throws InvalidDataFromFileException {
        MusicGenre genre;
        try {
            String g = scriptScanner.nextLine();
            genre = MusicGenre.valueOf(g);
        } catch (IllegalArgumentException ex) {
            throw new InvalidDataFromFileException("Значение жанра введено некорректно.");
        }
        return genre;
    }

    /**
     * @return valid non-empty {@code String} from file.
     * @throws InvalidDataFromFileException if Scanner meets empty {@code String} in file.
     */
    public synchronized static String ReadNextNonEmptyLine(ExecuteScript.ExecutionStringScanner scriptScanner) throws InvalidDataFromFileException {
        String srt = scriptScanner.nextLine();
        if (srt.equals("")) {
            throw new InvalidDataFromFileException("Введена пустая строка, где её не должно быть.");
        }
        return srt;
    }

}
