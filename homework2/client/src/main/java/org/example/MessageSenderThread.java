package org.example;

import java.io.PrintWriter;
import java.util.Scanner;

public class MessageSenderThread implements Runnable{
    private PrintWriter output;
    private Scanner scanner = new Scanner(System.in);

    public MessageSenderThread(PrintWriter output) {
        this.output = output;
    }


    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) break;
            String message = scanner.nextLine();
            output.println(message);
            if (message.equalsIgnoreCase("shutdown")) break;
        }
    }
}
