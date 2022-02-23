package commands;

import exceptions.IncorrectDataForObjectException;

import javax.xml.bind.JAXBException;
import java.io.File;

public class Save extends Command {

    public Save(AvailableCommands command){
        super(command);
        if (command != AvailableCommands.SAVE)
            throw new IncorrectDataForObjectException("Class Save cannot perform this task");
    }

    private void saveCollection() throws JAXBException {
        File file = new File("src/main/resources/MusicBandCollections.xml");
        JaxbManager manager = new JaxbManager(file);
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
