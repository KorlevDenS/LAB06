package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
import javax.xml.bind.JAXBException;

/**
 * Class {@code Save} is used for creating command "save" objects,
 * that save current {@code MusicBand} {@code HashSet} to Xml file.
 */
public class Save extends Command {

    /**
     * Constructs new Save object.
     * @param command enum constant from {@link AvailableCommands}
     */
    public Save(AvailableCommands command){
        super(command);
        if (command != AvailableCommands.SAVE)
            throw new IncorrectDataForObjectException("Class Save cannot perform this task");
    }

    /** Saves current collection to current xml file.*/
    private void saveCollection() throws JAXBException {
        JaxbManager manager = new JaxbManager(Accumulator.currentXml);
        manager.writeXml();
    }

    public void execute() {
        try {
            saveCollection();
        } catch (JAXBException exception) {
            System.out.println("Instruction \"save\" cannot save current collection:");
            System.out.println("Xml JAXB management realized incorrectly in the program.");
            System.out.println("User cannot fix this problem.");
        }
        System.out.println("Текущая версия коллекции успешно сохранена в файл.");
    }

    public String getDescription(){
        return this.description;
    }

}
