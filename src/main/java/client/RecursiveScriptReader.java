package client;

import common.exceptions.InvalidDataFromFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecursiveScriptReader {

    protected int fileIndex;
    private StringBuilder dataStringBuilder;
    protected ArrayList<String> infoData;
    protected String commandsAndData;
    protected LinkedHashMap<Integer, ScannerWithMemory> scriptScanners;
    protected File mainScript;
    protected LinkedHashMap<String, String> mistakesInfo;

    public RecursiveScriptReader(String stringRepresentation) {
        this.mainScript = new File(stringRepresentation.trim());
    }

    private static class ScannerWithMemory {

        protected Scanner scanner;
        protected File file;
        protected Integer stringNumber;

        ScannerWithMemory(File file) throws InvalidDataFromFileException {
            try {
                this.scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new InvalidDataFromFileException("Файла с таким именем не существует." + "\n"
                        + "Ведите команду с аргументом в виде имени существующего файла.");
            }
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

    private void fillScriptData() throws InvalidDataFromFileException {
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

    private void addNeededData(Matcher matcher, String line) {
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
            } catch (InvalidDataFromFileException e) {
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

    public void installScriptData() throws InvalidDataFromFileException {
        fillScriptData();
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
}
