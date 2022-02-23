package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
import interfaces.Operand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends Command implements Operand {

    public ExecuteScript(AvailableCommands command){
        super(command);
        if (command != AvailableCommands.EXECUTE_SCRIPT)
            throw new IncorrectDataForObjectException("Class ExecuteScript cannot perform this task");
    }

    private void validateScript() {
        try {
            Accumulator.fileScanner = new Scanner(Accumulator.currentScript);
        } catch (FileNotFoundException e) {
            System.out.println("Файла с таким именем не существует.");
            System.out.println("Ведите команду с аргументом в виде имени существующего файла.");
            Accumulator.readingTheScript = false;
            return;
        }
        if (!Accumulator.fileScanner.hasNext()) {
            System.out.println("Скрипт пуст, команды для исполнения не найдены.");
            Accumulator.readingTheScript = false;
        }
    }

    private void scanScriptCommand() {
        if (!Accumulator.readingTheScript)
            return;
        String line;
        line = Accumulator.fileScanner.nextLine();
        while (Accumulator.fileScanner.hasNextLine()) {
            if (line.equals("exit"))
                System.exit(0);
            CommandManager manager = new CommandManager(line);
            manager.execution(manager.instructionFetch());
            if (!Accumulator.readingTheScript) return;
            line = Accumulator.fileScanner.nextLine();
        }
        if (line.equals("exit"))
            System.exit(0);
        CommandManager manager = new CommandManager(line);
        manager.execution(manager.instructionFetch());

    }

    public void execute() {
        Accumulator.readingTheScript = true;
        validateScript();
        scanScriptCommand();
        Accumulator.readingTheScript = false;
        System.out.println("Выполнение скрипта завершено.");
    }

    public String getDescription(){
        return this.description;
    }

    public void installOperand(String stringRepresentation) {
       Accumulator.currentScript  = new File(stringRepresentation);
    }
}
