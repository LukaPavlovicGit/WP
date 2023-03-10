package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static final int PORT = 8000;
    public static List<String> database = new CopyOnWriteArrayList<>();
    public static List<Message> messageHistory = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Server and Client are connecting");
            Thread serverThread = new Thread(new ServerThread(socket));
            serverThread.start();
        }
    }
}