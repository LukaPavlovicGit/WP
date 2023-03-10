package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.example.Main.database;

public class ServerThread implements Runnable{
    public List<ServerThread> clients = new CopyOnWriteArrayList<>();
    private HashSet<String> forbiddenWords = new HashSet<>();
    private BufferedReader reader = null;
    private String message = "";
    private String username;
    private Gson gson = new Gson();
    private Socket socket;
    private PrintWriter output = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        setForbiddenWords();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            // Authentication
            while (true) {
                while (true) {
                    output.println("Enter username...");
                    username = reader.readLine();

                    if(database.contains(username)){
                        System.out.println("Client is rejected, username already exists");
                        output.println("Client with that username already exists");
                    }
                    else{
                        Main.database.add(username);
                        System.out.println("Client " + username + " is connected");
                        output.println("Welcome");
                        clients.add(this);
                        clients.iterator().forEachRemaining((ServerThread serverThread) -> {
                            if (!serverThread.equals(this)) {
                                serverThread.output.println("New user " + username + " joined");
                            }
                        });
                        Main.messageHistory.iterator().forEachRemaining((Message message) -> this.output.println(message));
                        break;
                    }
                }
                // Messaging
                while (true) {
                    message = reader.readLine();
                    if (message.equals("exit")) {
                        clients.iterator().forEachRemaining((ServerThread serverThread) -> {
                            if (!serverThread.equals(this)) serverThread.output.println("User " + username + " has left the chat.");
                            else {
                                serverThread.output.println("Status code: Exit");
                                System.out.println("User " + username + " is disconnected");
                            }
                        });
                        clients.remove(this);
                        Main.database.remove(username);
                        socket.close();
                        reader.close();
                        output.close();
                        break;
                    }
                    else if (message.equals("message history")) output.println(Main.messageHistory.toString());
                    else{
                        message = blurTheWord(message);
                        if (Main.messageHistory.size() > 100) Main.messageHistory.remove(0);
                        Main.database.add(username);
                        Message messageToClient = new Message(message, username, getTime());
                        Main.messageHistory.add(messageToClient);
                        output.println(messageToClient);
                        clients.iterator().forEachRemaining((ServerThread serverThread) -> {
                            if (!serverThread.equals(this)) {
                                serverThread.output.println(messageToClient);
                            }
                        });
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setForbiddenWords(){
        forbiddenWords.add("politics");
        forbiddenWords.add("kill");
        forbiddenWords.add("idiot");
    }

    private String blurTheWord(String message){
        StringBuilder stringBuilder = new StringBuilder();
        String[] splitWord = message.split(" ");
        for(int i = 0 ; i < splitWord.length ; i++){
            String word = splitWord[i];
            if(forbiddenWords.contains(word)){
                for (int j = 0; i < word.length(); j++) {
                    if (j == 0 || j == word.length() - 1) stringBuilder.append(word.charAt(i));
                    else stringBuilder.append("*");
                }
            }
            else stringBuilder.append(word);
            if(i != splitWord.length - 1) stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private String getTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }
}
