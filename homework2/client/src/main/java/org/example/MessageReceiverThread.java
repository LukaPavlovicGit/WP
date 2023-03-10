package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiverThread implements Runnable{

    private BufferedReader reader;

    public MessageReceiverThread(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
            while (true) {
                try {
                    String message = reader.readLine();
                    if (message.equals("Status code: Exit") || Thread.currentThread().isInterrupted()) break;
                    else System.out.println(message);
                } catch (IOException e) {e.printStackTrace();}
            }

    }
}
