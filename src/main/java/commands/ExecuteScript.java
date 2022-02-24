package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
import interfaces.Operand;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class {@code ExecuteScript} is used for creating "execute_script" command
 * object that makes the program execute all available commands in this script
 * up to the end of the file or up to the first mistake in this script.
 */
public class ExecuteScript extends Command implements Operand {

    /**
     * Constructs new ExecuteScript object.
     * @param command enum constant from {@link AvailableCommands}
     */
    public ExecuteScript(AvailableCommands command){
        super(command);
        if (command != AvailableCommands.EXECUTE_SCRIPT)
            throw new IncorrectDataForObjectException("Class ExecuteScript cannot perform this task");
    }

    /**
     * Method check current scrips and stops its execution if the file
     * is empty on does not exist. The message about such kind of mistake
     * will be immediately shown to user.
     */
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

    /**
     * Method is used fir reading commands from script, execute them or
     * interrupt this process when it is necessary.
     */
    private void scanScriptCommand() {
        if (!Accumulator.readingTheScript)
            return;
        String line;
        line = Accumulator.fileScanner.nextLine();
        while (Accumulator.fileScanner.hasNextLine()) {
            if (line.equals("exit")) {
                System.out.println("Выполнение скрипта завершено.");
                System.exit(0);
            }
            CommandManager manager = new CommandManager(line);
            manager.execution(manager.instructionFetch());
            if (!Accumulator.readingTheScript) return;
            line = Accumulator.fileScanner.nextLine();
        }
        if (line.equals("exit")) {
            System.out.println("Выполнение скрипта завершено.");
            System.exit(0);
        }
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
