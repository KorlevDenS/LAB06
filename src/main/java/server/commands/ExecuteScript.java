package server.commands;

import server.ScriptCommandManager;
import server.ServerStatusRegister;
import common.AvailableCommands;
import common.ResultPattern;
import common.exceptions.IncorrectDataForObjectException;
import common.exceptions.InvalidDataFromFileException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.*;
import java.util.Scanner;

public class ExecuteScript extends Command {

    protected ArrayList<String> infoData;
    protected String commandsAndData;
    protected LinkedHashMap<String, String> mistakesInfo;
    private ExecutionStringScanner scriptScanner;

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

    private void scanScriptCommand() {
        if (!isReadingTheScript())
            return;
        String line;
        reader:
        while (scriptScanner.hasNextLine()) {
            line = scriptScanner.nextLine();
            if (line.equals("exit")) {
                report.getReports().add("Выполнение скрипта завершено.");
                mistakesInfo.keySet().forEach(key -> {
                    if ((!Objects.equals(mistakesInfo.get(key), "")))
                        report.getReports().add(key + mistakesInfo.get(key));
                });
                report.setTimeToExit(true);
                break;
            }
            for (AvailableCommands command : AvailableCommands.values()) {
                if (command.getRegex(line).matches()) {
                    try {
                        ScriptCommandManager manager = new ScriptCommandManager(line, scriptScanner);
                        report.getReports().addAll(manager.execution(manager.instructionFetch()).getReports());
                        report.getReports().add("");
                    } catch (InvalidDataFromFileException ex) {
                        mistakesInfo.put(infoData.get(scriptScanner.getCommandIndex()), line +
                                ": " + ex.getMessage());
                    }
                    continue reader;
                }
            }
            if (line.equals("RECURSION_ERROR")) {
                mistakesInfo.put(infoData.get(scriptScanner.getCommandIndex()), line + ": Исполнение " +
                        "данного скрипта в этом файле вызывает бесконечный цикл.");
            } else
                mistakesInfo.put(infoData.get(scriptScanner.getCommandIndex()), line + ": useless data.");
        }
    }

    public ResultPattern execute() {
        turnOnScriptMode();
        report = new ResultPattern();
        this.mistakesInfo = dataBase.getMistakesInfo();
        this.infoData = dataBase.getInfoData();
        this.commandsAndData = dataBase.getCommandsAndData();
        if (commandsAndData != null) {
            this.scriptScanner = new ExecutionStringScanner(commandsAndData);
            scanScriptCommand();
        }
        mistakesInfo.keySet().forEach(key -> {
            if ((!Objects.equals(mistakesInfo.get(key), "")))
                report.getReports().add(key + mistakesInfo.get(key));
        });
        report.getReports().add("Выполнение скрипта завершено.");

        return report;
    }

}