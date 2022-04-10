import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main3 {

    protected static int fileIndex = 0;
    protected static LinkedHashMap<Integer, ScannerWithMemory> scriptScanners = new LinkedHashMap<>();
    protected static ArrayList<String> infoData = new ArrayList<>();
    protected static ArrayList<String> scriptData = new ArrayList<>();

    protected static LinkedHashMap<String, String> mistakesInfo;

    protected static File bufferFile = new File("src/main/java/file4.txt");

    static class ScannerWithMemory {

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

    public static void main(String[] args) throws IOException {
        File firstFile = new File("src/main/java/file1.txt");
        String currentFileName = "execute_script .*\\.txt$";
        Pattern pattern = Pattern.compile(currentFileName);
        scriptScanners.put(fileIndex, new ScannerWithMemory(firstFile));
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
        for (int j = 0; j < infoData.size(); j++) {
            System.out.println(infoData.get(j) + "" + scriptData.get(j));
            mistakesInfo.put(infoData.get(j), "");
        }

        for (String key : mistakesInfo.keySet()) {
            System.out.println(key);
        }


    }

    public static void addNeededData(Matcher matcher, String line) throws FileNotFoundException {
        if (matcher.matches()) {
            Scanner scanner = new Scanner(line);
            scanner.next();
            File newFile = new File(scanner.next());
            for (int i = 0; i <= fileIndex; i++) {
                if (scriptScanners.get(i).getFileName().equals(newFile.toString())) {
                    infoData.add("Файл:" + scriptScanners.get(fileIndex).getFileName() + ";стр."
                            + scriptScanners.get(fileIndex).getStringNumber() + ": ");
                    scriptData.add("RECURSION_ERROR");
                    return;
                }
            }
            fileIndex++;
            scriptScanners.put(fileIndex, new ScannerWithMemory(newFile));
        } else {
            infoData.add("Файл:" + scriptScanners.get(fileIndex).getFileName() + ";стр."
                    + scriptScanners.get(fileIndex).getStringNumber() + ": ");
            scriptData.add(line);
        }
    }
}
