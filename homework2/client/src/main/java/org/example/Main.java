package org.example;

import java.io.*;
import java.net.Socket;

public class Main {
    public static boolean authorisationSuccessful = false;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Thread receiverThread = new Thread(new MessageReceiverThread(reader));
            Thread senderThread = new Thread(new MessageSenderThread(output));
            receiverThread.start();
            senderThread.start();
            receiverThread.join();
            senderThread.join();
            socket.close();
        } catch (IOException | InterruptedException e) {e.printStackTrace();}
    }
}