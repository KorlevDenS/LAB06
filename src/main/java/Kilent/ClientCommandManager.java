package Kilent;

import commands.AvailableCommands;
import common.InstructionPattern;
import exceptions.InvalidDataFromFileException;

import java.util.Arrays;
import java.util.Scanner;

public class ClientCommandManager {

    private String instructionTitle;

    public ClientCommandManager(String instructionTitle){
        this.instructionTitle = instructionTitle;
    }

    public AvailableCommands instructionFetch() {
        for (AvailableCommands command : AvailableCommands.values()) {
            if (command.getRegex(instructionTitle).matches()) {
                return command;
            }
        }
        System.out.println("Команда не существует или введена некорректно");
        System.out.println("Введите одну из доступных команд из списка:");
        Arrays.stream(AvailableCommands.values()).forEachOrdered(s -> System.out.println(s.getTitle()));
        Scanner scanner = new Scanner(System.in);
        instructionTitle = scanner.nextLine();
        return instructionFetch();
    }

    public String operandFetch() {
        Scanner scanner = new Scanner(instructionTitle);
        scanner.next();
        if (scanner.hasNext()) return scanner.next();
        else return "";
    }

    public InstructionPattern execution() throws InvalidDataFromFileException {
        AvailableCommands command = instructionFetch();
        InstructionPattern instructionPattern = new InstructionPattern(command);
        instructionPattern.setInstructionType(command.toString());
        instructionPattern.chooseAndLoadArguments();
        instructionPattern.setOperand(operandFetch());
        return instructionPattern;
    }
}
