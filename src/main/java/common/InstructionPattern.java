package common;

import Kilent.ClientDataLoader;
import basic.objects.MusicBand;
import basic.objects.Person;
import commands.AvailableCommands;
import java.io.Serializable;

public class InstructionPattern extends ClientDataLoader implements Serializable {

    private final String argumentTitle;
    private final String titleRegex;
    private final String description;
    private String instructionType;
    private MusicBand musicBand;
    private Person frontMan;
    private String operand;

    public InstructionPattern(AvailableCommands command){
        this.instructionType = command.toString();
        this.titleRegex = command.getTitle();
        this.description = command.getDescription();
        this.argumentTitle = command.getArgumentTitle();
    }

    public void chooseAndLoadArguments() {
        switch (argumentTitle) {
            case ("MusicBand") -> this.musicBand = super.loadObjectFromData();
            case ("FrontMan") -> this.frontMan = loadFrontMan();
            default -> {
            }
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
