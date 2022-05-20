
import Kilent.ClientCommandManager;
import Server.ServerCommandManager;
import common.InstructionPattern;
import common.AvailableCommands;
import common.ResultPattern;
import exceptions.InvalidDataFromFileException;

import java.io.*;

public class MainT {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidDataFromFileException {
        InstructionPattern pattern = new InstructionPattern(AvailableCommands.HELP);
        pattern.chooseAndLoadArguments();

        //soket
        var out = new ObjectOutputStream(new FileOutputStream("file.txt"));
        out.writeObject(pattern);

        var in = new ObjectInputStream(new FileInputStream("file.txt"));
        var obj = in.readObject();
        if(obj.getClass() == pattern.getClass()) {
            InstructionPattern ind = (InstructionPattern) obj;
            System.out.println(ind.getInstructionType());
        }
        //ClientCommandManager commandManager = new ClientCommandManager("add");
        //InstructionPattern pattern = commandManager.execution();
        //отправить на сервер

        //ResultPattern resultPattern = new ServerCommandManager(pattern).execution();
        //обратно клиенту
        //resultPattern.getReports().forEach(System.out::println);

    }

}
