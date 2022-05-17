package Kilent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class MainK {

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost", 4004);

        Scanner reader = new Scanner(System.in);

        Scanner in = new Scanner(clientSocket.getInputStream());
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        System.out.println("Вы что-то хотели сказать? Введите это здесь:");
        System.out.println(in.nextLine());


        boolean done = false;
        while (!done) {
            String word = reader.nextLine();

            out.write(word + "\n");
            out.flush();

            String serverWord = in.nextLine();
            System.out.println(serverWord);
            if (word.equals("BYE")) done = true;
        }

    }
}