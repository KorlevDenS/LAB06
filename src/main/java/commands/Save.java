package commands;

import basic.objects.Accumulator;
import common.ResultPattern;
import exceptions.IncorrectDataForObjectException;

import javax.xml.bind.JAXBException;

/**
 * Class {@code Save} is used for creating command "save" objects,
 * that save current {@code MusicBand} {@code HashSet} to Xml file.
 */
public class Save extends Command {

    /**
     * Constructs new Save object.
     *
     * @param command enum constant from {@link AvailableCommands}
     */
    public Save(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.SAVE)
            throw new IncorrectDataForObjectException("Class Save cannot perform this task");
    }

    /**
     * Saves current collection to current xml file.
     */
    private void saveCollection() throws JAXBException {
        JaxbManager manager = new JaxbManager(Accumulator.currentXml);
        manager.writeXml();
    }

    public ResultPattern execute() {
        try {
            saveCollection();
        } catch (JAXBException exception) {
            report.getReports().add("Instruction \"save\" cannot save current collection:");
            report.getReports().add("Xml JAXB management realized incorrectly in the program.");
            report.getReports().add("User cannot fix this problem.");
        }
        report.getReports().add("Текущая версия коллекции успешно сохранена в файл.");
        return report;
    }

    public String getDescription() {
        return this.description;
    }

}
