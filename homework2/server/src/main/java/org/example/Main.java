package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static final int PORT = 8000;
    public static List<String> database = new CopyOnWriteArrayList<>();
    public static List<org.example.Message> messageHistory = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client is getting connection with a server");
            Thread serverThread = new Thread(new org.example.ServerThread(socket));
            serverThread.start();
        }
    }
}