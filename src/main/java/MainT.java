
import Kilent.ClientCommandManager;
import Server.ServerCommandManager;
import commands.Command;
import common.InstructionPattern;
import commands.AvailableCommands;
import common.ResultPattern;
import exceptions.InvalidDataFromFileException;

import java.io.*;

public class MainT {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidDataFromFileException {
        //InstructionPattern pattern = new InstructionPattern(AvailableCommands.ADD);
        //pattern.chooseAndLoadArguments();


        //var out = new ObjectOutputStream(new FileOutputStream("file.txt"));
        //out.writeObject(pattern);

        //var in = new ObjectInputStream(new FileInputStream("file.txt"));
        //var obj = (InstructionPattern) in.readObject();

        //System.out.println(obj.getMusicBand().toString());

        ClientCommandManager commandManager = new ClientCommandManager("add");
        InstructionPattern pattern = commandManager.execution();
        ResultPattern resultPattern = new ServerCommandManager(pattern).execution();
        resultPattern.getReports().forEach(System.out::println);

    }

}
