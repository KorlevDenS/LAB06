import basic.objects.Accumulator;
import commands.CommandManager;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Accumulator.appleMusic = new HashSet<>();
        Accumulator.current = new Date();
        scanCommand();
        //testing();
    }

    public static void testing() {
        //Scanner scanner = new Scanner(System.in);
        //String line = scanner.nextLine();

        //while (!line.equals("exit")) {
        //    System.out.println(line);
        //    if (line.equals("date")) System.out.println(Accumulator.current);
        //    System.out.println(Collections.min(Accumulator.appleMusic).toString());
        //    line = scanner.nextLine();
        //}
    }

    public static void scanCommand() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            CommandManager manager = new CommandManager(line);
            manager.execution(manager.instructionFetch());
            line = scanner.nextLine();
        }

    }
}
