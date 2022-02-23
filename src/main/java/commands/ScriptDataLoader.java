package commands;

import basic.objects.*;
import exceptions.InvalidDataFromFileException;
import exceptions.ScanValidation;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class ScriptDataLoader{

    protected String loadBandName() throws InvalidDataFromFileException {
        return ScanValidation.ReadNextNonEmptyLine();
    }

    protected Coordinates loadBandCoordinates() throws InvalidDataFromFileException{
        int coordinateX = ScanValidation.ReadNextInt();
        double coordinateY = ScanValidation.ReadNextDouble();
        if ((coordinateX > 381)||(coordinateY > 381)) {
            System.out.println("Значения координат превышают 381.");
            throw new InvalidDataFromFileException();
        }
        return new Coordinates(coordinateX, coordinateY);
    }

    protected long loadNumberOfParticipants() throws InvalidDataFromFileException {
        long numberOfParticipants = ScanValidation.ReadNextLong();
        if (numberOfParticipants == 0) {
            System.out.println("Количество участников должно быть больше нуля.");
            throw new InvalidDataFromFileException();
        }
        return numberOfParticipants;
    }

    protected MusicGenre loadBandMusicGenre() throws InvalidDataFromFileException{
        return ScanValidation.ReadNextGenre();
    }

    protected String loadFrontManName() throws InvalidDataFromFileException{
        return ScanValidation.ReadNextNonEmptyLine();
    }

    protected long loadFrontManHeight() throws InvalidDataFromFileException {
        long frontManHeight = ScanValidation.ReadNextLong();
        if (frontManHeight <= 0){
            System.out.println("Рост фронтмена должен быть больше нуля.");
            throw new InvalidDataFromFileException();
        }
        return frontManHeight;
    }

    protected int loadFrontManWeight() throws InvalidDataFromFileException{
        int frontManWeight = ScanValidation.ReadNextInt();
        if (frontManWeight <= 0){
            System.out.println("Вес фронтмена должен быть больше нуля.");
            throw new InvalidDataFromFileException();
        }
        return frontManWeight;
    }

    protected String loadFrontManPassportID() throws InvalidDataFromFileException {
        String frontManPassportId = Accumulator.fileScanner.nextLine();
        if (frontManPassportId.equals("")) return null;
        if (frontManPassportId.length() > 29) {
            System.out.println("Длинна строки превысила 29 символов.");
            throw new InvalidDataFromFileException();
        }
        if ((Accumulator.passports.contains(frontManPassportId))&&(!Accumulator.readingTheScript)) {
            System.out.println("Человек с введенным ID уже существует.");
            throw new InvalidDataFromFileException();
        }
        Accumulator.passports.add(frontManPassportId);
        return frontManPassportId;
    }

    protected ZonedDateTime giveBirthFrontMan() {
        String LineWithTime = Accumulator.fileScanner.nextLine();
        if (LineWithTime.equals(""))
            return null;
        Scanner s = new Scanner(LineWithTime);
        return ZonedDateTime.of(s.nextInt(), s.nextInt(), s.nextInt(),
                s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), ZoneId.of("Europe/Paris"));
    }

    protected Person loadFrontManFromData() throws InvalidDataFromFileException{
        String frontManName = loadFrontManName();
        if (frontManName.equals("")) return null;
        long frontManHeight = loadFrontManHeight();
        int frontManWeight = loadFrontManWeight();
        String frontManPassportId = loadFrontManPassportID();
        ZonedDateTime frontManBirthday = giveBirthFrontMan();
        return new Person(frontManName, frontManHeight, frontManWeight, frontManBirthday, frontManPassportId);
    }

    public MusicBand loadObjectFromData() throws InvalidDataFromFileException{
        String nameOfBand;
        Coordinates bandCoordinates;
        long numberOfParticipants;
        MusicGenre musicGenre;
        Person frontMan;
        try {
            nameOfBand = loadBandName();
            bandCoordinates = loadBandCoordinates();
            numberOfParticipants = loadNumberOfParticipants();
            musicGenre = loadBandMusicGenre();
            frontMan = loadFrontManFromData();
        } catch (InvalidDataFromFileException ex) {
            System.out.println("Получены неверные данные для построения объекта MusicBand.");
            throw new InvalidDataFromFileException();
        }
        return new MusicBand(nameOfBand, bandCoordinates, numberOfParticipants,
                musicGenre, frontMan);
    }
}
