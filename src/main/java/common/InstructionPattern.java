package common;

import client.ClientDataLoader;
import client.RecursiveScriptReader;
import common.basic.MusicBand;
import common.basic.Person;
import common.exceptions.InvalidDataFromFileException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InstructionPattern extends ClientDataLoader implements Serializable {

    private final String argumentTitle;
    private final String titleRegex;
    private final String description;
    private String instructionType;
    private MusicBand musicBand;
    private Person frontMan;
    private String operand;
    protected ArrayList<String> infoData;
    protected String commandsAndData;
    protected LinkedHashMap<String, String> mistakesInfo;

    public InstructionPattern(AvailableCommands command, String operand) {
        this.operand = operand;
        this.instructionType = command.toString();
        this.titleRegex = command.getTitle();
        this.description = command.getDescription();
        this.argumentTitle = command.getArgumentTitle();
    }

    public void chooseAndLoadArguments() throws InvalidDataFromFileException {
        switch (argumentTitle) {
            case ("MusicBand"):
                this.musicBand = super.loadObjectFromData();
                break;
            case ("FrontMan"):
                this.frontMan = loadFrontMan();
                break;
            case ("Script"):
                RecursiveScriptReader scriptReader = new RecursiveScriptReader(operand);
                scriptReader.installScriptData();
                this.infoData = scriptReader.getInfoData();
                this.mistakesInfo = scriptReader.getMistakesInfo();
                this.commandsAndData = scriptReader.getCommandsAndData();
            default:
                break;
        }
    }

    public void setInstructionType(String type) {
        this.instructionType = type;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getArgumentTitle() {
        return argumentTitle;
    }

    public String getTitleRegex() {
        return titleRegex;
    }

    public String getDescription() {
        return description;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public ArrayList<String> getInfoData() {
        return this.infoData;
    }

    public String getCommandsAndData() {
        return this.commandsAndData;
    }

    public LinkedHashMap<String, String> getMistakesInfo() {
        return this.mistakesInfo;
    }

    @Override
    protected String loadFrontManPassportID() {
        System.out.println("Введите уникальный номер паспорта фронтмена группы.");
        System.out.println("Если он неизвестен - пропустите.");
        String frontManPassportId = scanner1.nextLine();
        if (frontManPassportId.equals("")) return null;
        while ((frontManPassportId.length() > 29)) {
            System.out.println("Длинна строки не должна превышать 29 символов, введите ее правильно.");
            frontManPassportId = scanner1.nextLine();
        }
        return frontManPassportId;
    }

}
