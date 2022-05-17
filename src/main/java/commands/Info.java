package commands;

import basic.objects.Accumulator;
import common.ResultPattern;
import exceptions.*;

import java.util.Collections;
import java.util.Date;

/**
 * Class {@code Info} is used for creating command "info" object,
 * that prints information about current condition of {@code HashSet}
 * with {@code MusicBand} objects.
 */
public class Info extends Command {

    /**
     * Type of collection received by {@code getClass().getName()}
     */
    private String typeOfCollection;
    /**
     * Date of initialisation of {@code HashSet} with {@code MusicBand}
     * objects == Date and time of program has been started.
     */
    private Date initDateOfCollection;
    /**
     * Current amount of elements in {@code HashSet}
     */
    private int amountOfElements;
    /**
     * Type of {@code HashSet} inner elements.
     */
    private String typeOfInnerElements;
    /**
     * Current minimum element of {@code HashSet}.
     */
    private String minElement;
    /**
     * Current maximum element of {@code HashSet}.
     */
    private String maxElement;

    /**
     * Constructs new {@code Info} object.
     *
     * @param command relevant {@link AvailableCommands} command.
     * @throws IncorrectDataForObjectException if {@link AvailableCommands} command
     *                                         does not match this class.
     */
    public Info(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.INFO)
            throw new IncorrectDataForObjectException("Class Info cannot perform this task");
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Analysing and filling current data about {@code HashSet}.
     */
    private void knowInformation() {
        typeOfCollection = Accumulator.appleMusic.getClass().getName();
        initDateOfCollection = Accumulator.current;
        amountOfElements = Accumulator.appleMusic.size();
        if (!Accumulator.appleMusic.isEmpty()) {
            minElement = Collections.min(Accumulator.appleMusic).toString();
            maxElement = Collections.max(Accumulator.appleMusic).toString();
            typeOfInnerElements = Collections.min(Accumulator.appleMusic).getClass().getName();
        }
    }

    public ResultPattern execute() {
        knowInformation();
        report.getReports().add("Информация о созданной коллекции:");
        report.getReports().add("Коллекция представляет собой " + typeOfCollection);
        report.getReports().add("Дата инициализации: " + initDateOfCollection);
        report.getReports().add("Текущее количество элементов: " + amountOfElements);
        if (Accumulator.appleMusic.isEmpty()) {
            report.getReports().add("Минимального элемента ещё нет, коллекция пуста.");
            report.getReports().add("Максимального элемента ёщё нет, коллекция пуста.");
            report.getReports().add("Тип неизвестен, так как в коллекции еще нет ни одного элемента.");
        } else {
            report.getReports().add("Минимальный элемент: " + minElement);
            report.getReports().add("Максимальный элемент: " + maxElement);
            report.getReports().add("Тип хранимых элементов: " + typeOfInnerElements);
        }
        return report;
    }
}
