package common;

import basic.objects.*;
import exceptions.InvalidDataFromFileException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class {@code ScanValidation} contains overridden methods
 * of class {@code Scanner} and not only to read data from {@code System.in}.
 * All these methods ask user to enter valid data while
 * detecting incorrect input.
 */
public class ScanValidation {

    /**
     * Asks user to enter {@code int} value while
     * catching {@link InputMismatchException} exception.
     *
     * @return entered {@code int} value.
     */
    public static int nextInt() {
        Scanner varScan = new Scanner(System.in);
        int obj;
        try {
            obj = varScan.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Введенная строка не является целым числом, введите корректное значение.");
            return nextInt();
        }
        return obj;
    }

    /**
     * Asks user to enter {@code double} value while
     * catching {@link InputMismatchException} exception.
     *
     * @return entered {@code double} value.
     */
    public static double nextDouble() {
        Scanner varScan = new Scanner(System.in);
        double obj;
        try {
            obj = varScan.nextDouble();
        } catch (InputMismatchException ex) {
            System.out.println("Введенная строка не является дробным десятичным числом, введите корректное значение.");
            return nextDouble();
        }
        return obj;
    }

    /**
     * Asks user to enter {@code long} value while
     * catching {@link InputMismatchException} exception.
     *
     * @return entered {@code long} value.
     */
    public static long nextLong() {
        Scanner varScan = new Scanner(System.in);
        long obj;
        try {
            obj = varScan.nextLong();
        } catch (InputMismatchException ex) {
            System.out.println("Введенная строка не является целым числом, введите корректное значение.");
            return nextLong();
        }
        return obj;
    }

    /**
     * Asks user to enter {@link MusicGenre} value while
     * catching {@link InputMismatchException} exception.
     *
     * @return entered {@link MusicGenre} value.
     */
    public static MusicGenre nextGenre() {
        Scanner varScan = new Scanner(System.in);
        MusicGenre genre;
        try {
            String g = varScan.next();
            varScan.nextLine();
            genre = MusicGenre.valueOf(g);
        } catch (IllegalArgumentException ex) {
            System.out.println("На музыкальной площадке нет такого жанра, введите один из предложенных ранее.");
            return nextGenre();
        }
        return genre;
    }

    /**
     * Asks user to enter not empty {@code String} value
     * while detecting empty inputted {@code String}.
     *
     * @return entered not empty {@code String} value.
     */
    public static String nextNonEmptyLine() {
        Scanner varScan = new Scanner(System.in);
        String srt = varScan.nextLine();
        if (srt.equals("")) {
            System.out.println("Строка не может быть пустой. Введите её корректно.");
            return nextNonEmptyLine();
        }
        return srt;
    }

    /**
     * @return valid {@code int} from file.
     * @throws InvalidDataFromFileException if Scanner meets not {@code int} in file.
     */
    public static int ReadNextInt() throws InvalidDataFromFileException {
        int obj;
        try {
            String str = Accumulator.scriptScanner.nextLine();
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
    public static double ReadNextDouble() throws InvalidDataFromFileException {
        double obj;
        try {
            String str = Accumulator.scriptScanner.nextLine();
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
    public static long ReadNextLong() throws InvalidDataFromFileException {
        long obj;
        try {
            String str = Accumulator.scriptScanner.nextLine();
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
    public static MusicGenre ReadNextGenre() throws InvalidDataFromFileException {
        MusicGenre genre;
        try {
            String g = Accumulator.scriptScanner.nextLine();
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
    public static String ReadNextNonEmptyLine() throws InvalidDataFromFileException {
        String srt = Accumulator.scriptScanner.nextLine();
        if (srt.equals("")) {
            throw new InvalidDataFromFileException("Введена пустая строка, где её не должно быть.");
        }
        return srt;
    }

}
