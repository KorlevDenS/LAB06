package commands;

import basic.objects.Accumulator;
import exceptions.IncorrectDataForObjectException;
import exceptions.InvalidDataFromFileException;
import interfaces.Operand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteScript extends Command implements Operand {

    protected int fileIndex;
    protected LinkedHashMap<Integer, ScannerWithMemory> scriptScanners;
    protected ArrayList<String> infoData;
    protected String commandsAndData;
    protected File mainScript;
    private StringBuilder dataStringBuilder;
    protected LinkedHashMap<String, String> mistakesInfo;

    /**
     * Constructs new ExecuteScript object.
     *
     * @param command enum constant from {@link AvailableCommands}
     */
    public ExecuteScript(AvailableCommands command) {
        super(command);
        if (command != AvailableCommands.EXECUTE_SCRIPT)
            throw new IncorrectDataForObjectException("Class ExecuteScript cannot perform this task");
    }

    private static class ScannerWithMemory {

        protected Scanner scanner;
        protected File file;
        protected Integer stringNumber;

        ScannerWithMemory(File file) throws FileNotFoundException {
            this.scanner = new Scanner(file);
            this.file = file;
            this.stringNumber = 0;
        }

        public Scanner getScanner() {
            return this.scanner;
        }

        public String readNextLine() {
            String line = this.scanner.nextLine();
            increaseStringNumber();
            return line;
        }

        public String getFileName() {
            return this.file.toString();
        }

        public Integer getStringNumber() {
            return this.stringNumber;
        }

        public void increaseStringNumber() {
            this.stringNumber++;
        }

    }

    static public class ExecutionStringScanner {

        private final Scanner stringScanner;
        private int commandIndex;

        public ExecutionStringScanner(String executionString) {
            this.stringScanner = new Scanner(executionString);
            this.commandIndex = -1;
        }

        public String nextLine() {
            this.commandIndex++;
            return stringScanner.nextLine();
        }

        public int getCommandIndex() {
            return this.commandIndex;
        }

        public boolean hasNextLine() {
            return stringScanner.hasNextLine();
        }
    }

    private void fillScriptData() throws FileNotFoundException {
        dataStringBuilder = new StringBuilder();
        infoData = new ArrayList<>();
        commandsAndData = null;
        fileIndex = 0;
        scriptScanners = new LinkedHashMap<>();
        String currentFileName = "execute_script .*\\.txt$";
        Pattern pattern = Pattern.compile(currentFileName);
        scriptScanners.put(fileIndex, new ScannerWithMemory(mainScript));
        while (fileIndex >= 0) {
            if (!scriptScanners.get(fileIndex).getScanner().hasNextLine())
                break;
            String line = scriptScanners.get(fileIndex).readNextLine();
            Matcher matcher = pattern.matcher(line);
            addNeededData(matcher, line);
            if (!scriptScanners.get(fileIndex).getScanner().hasNextLine())
                fileIndex--;
        }
        mistakesInfo = new LinkedHashMap<>();
        for (String infoDatum : infoData) {
            mistakesInfo.put(infoDatum, "");
        }
    }

    private void addNeededData(Matcher matcher, String line)  {
        if (matcher.matches()) {
            Scanner scanner = new Scanner(line);
            scanner.next();
            File newFile = new File(scanner.next());
            for (int i = 0; i <= fileIndex; i++) {
                if (scriptScanners.get(i).getFileName().equals(newFile.toString())) {
                    infoData.add("Файл:" + scriptScanners.get(fileIndex).getFileName() + ";стр."
                            + scriptScanners.get(fileIndex).getStringNumber() + ": ");
                    dataStringBuilder.append("RECURSION_ERROR").append("\n");
                    return;
                }
            }
            fileIndex++;
            try {
                scriptScanners.put(fileIndex, new ScannerWithMemory(newFile));
            } catch (FileNotFoundException e) {
                fileIndex--;
                infoData.add("Файл:" + scriptScanners.get(fileIndex).getFileName() + ";стр."
                        + scriptScanners.get(fileIndex).getStringNumber() + ": ");
                dataStringBuilder.append("Файла с именем ").append(newFile).append(" не существует").append("\n");
                return;
            }

        } else {
            infoData.add("Файл:" + scriptScanners.get(fileIndex).getFileName() + ";стр."
                    + scriptScanners.get(fileIndex).getStringNumber() + ": ");
            dataStringBuilder.append(line).append("\n");
        }
        commandsAndData = dataStringBuilder.toString();
    }

    private void scanScriptCommand() {
        if (!Accumulator.readingTheScript)
            return;
        String line;
        reader:
        while (Accumulator.scriptScanner.hasNextLine()) {
            line = Accumulator.scriptScanner.nextLine();
            if (line.equals("exit")) {
                System.out.println("Выполнение скрипта завершено.");
                mistakesInfo.keySet().forEach(key -> {
                    if ((!Objects.equals(mistakesInfo.get(key), "")))
                        System.out.println(key + mistakesInfo.get(key));});
                System.exit(0);
            }
            for (AvailableCommands command : AvailableCommands.values()) {
                if (command.getRegex(line).matches()) {
                    try {
                        CommandManager manager = new CommandManager(line);
                        manager.execution(manager.instructionFetch());
                    } catch (InvalidDataFromFileException ex) {
                        mistakesInfo.put(infoData.get(Accumulator.scriptScanner.getCommandIndex()), line +
                                ": " + ex.getMessage());
                    }
                    continue reader;
                }
            }
            if (line.equals("RECURSION_ERROR")) {
                mistakesInfo.put(infoData.get(Accumulator.scriptScanner.getCommandIndex()), line + ": Исполнение " +
                        "данного скрипта в этом файле вызывает бесконечный цикл.");
            } else
                mistakesInfo.put(infoData.get(Accumulator.scriptScanner.getCommandIndex()), line + ": useless data.");
        }
    }


    public void execute() {
        Accumulator.readingTheScript = true;
        try {
            fillScriptData();
        } catch (FileNotFoundException e) {
            System.out.println("Файла с таким именем не существует.");
            System.out.println("Ведите команду с аргументом в виде имени существующего файла.");
            Accumulator.readingTheScript = false;
            return;
        }
        if (commandsAndData != null) {
            Accumulator.scriptScanner = new ExecutionStringScanner(commandsAndData);
            scanScriptCommand();
        }
        Accumulator.readingTheScript = false;
        mistakesInfo.keySet().forEach(key -> {
            if ((!Objects.equals(mistakesInfo.get(key), "")))
                System.out.println(key + mistakesInfo.get(key));});
        System.out.println("Выполнение скрипта завершено.");
    }

    public String getDescription() {
        return this.description;
    }

    public void installOperand(String stringRepresentation) {
        this.mainScript = new File(stringRepresentation);
        System.out.println("INSTALLED" + mainScript);
    }
}