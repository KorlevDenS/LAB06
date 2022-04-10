import basic.objects.Coordinates;
import basic.objects.MusicBand;
import basic.objects.MusicGenre;
import basic.objects.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main2 {

    static LocalDate date;

    public static void main(String[] args) {
        date = LocalDate.of(1999, 12, 8);
        //System.out.println(toStringG());
        MusicBand band = new MusicBand("Group2",new Coordinates(222,20.5),5, MusicGenre.BRIT_POP,
                new Person("Billy", 186, 90, ZonedDateTime.of(1999, 12,8,8,8,
                        8,8, ZoneId.of("Europe/Moscow")),"QW1234556h123563"),123456);
        System.out.println(band.toScriptString());
    }

    public static String toStringG() {
        return date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth();
    }

}
