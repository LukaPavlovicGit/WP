package org.example;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.example.Main.database;

public class ServerThread implements Runnable{
    private static final HashSet<String> forbiddenWords = new HashSet<>();
    private final Socket socket;
    public static List<ServerThread> clients = new CopyOnWriteArrayList<>();
    private PrintWriter output = null;
    private BufferedReader reader = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        setForbiddenWords();
    }

    @Override
    public void run() {
        try {
            while (true) {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                output.println("You are communicating with a server...");
                String username;
                // Authentication
                while (true) {
                    output.println("Enter username...");
                    username = reader.readLine();
                    if(database.contains(username)){
                        System.out.println("Client rejected...");
                        output.println("Username is already in user...try different one");
                    }
                    else{
                        Main.database.add(username);
                        System.out.println("Client " + username + " is connected");
                        output.println("Welcome to the chat");
                        clients.add(this);
                        for(ServerThread serverThread : clients){
                            if(!serverThread.equals(this)) serverThread.output.println("User " + username + " joined the chat");
                        }
                        output.println(getMessageHistory());
                        break;
                    }
                }
                // Chat
                while (true) {
                    String message = reader.readLine();
                    if (message.equals("shutdown")) {
                        // Client disconnected, notify everyone
                        for(ServerThread serverThread : clients){
                            if (!serverThread.equals(this)) serverThread.output.println("User " + username + " has left the chat.");
                            else {
                                serverThread.output.println("code: shutdown");
                                System.out.println("User " + username + " disconnected");
                            }
                        }
                        clients.remove(this);
                        Main.database.remove(username);
                        socket.close();
                        reader.close();
                        output.close();
                        break;
                    }
                    else if (message.equals("message history")) output.println(getMessageHistory());
                    else{
                        message = blurTheWord(message);
                        if (Main.messageHistory.size() > 100) Main.messageHistory.remove(0);
                        Message arrivedMessage = new Message(message, username, getTime());
                        Main.messageHistory.add(arrivedMessage);
                        // Notify all clients that the message arrived
                        for(ServerThread serverThread : clients)
                            serverThread.output.println(arrivedMessage);
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private String blurTheWord(String message){
        StringBuilder sb = new StringBuilder();
        String[] words = message.split(" ");
        for(int i = 0 ; i < words.length ; i++){
            String singleWord = words[i];
            if(forbiddenWords.contains(singleWord)){
                sb.append(singleWord.charAt(0));
                for (int j = 1; j < singleWord.length() - 1; j++)
                    sb.append("*");
                sb.append(singleWord.charAt(singleWord.length() - 1));
            }
            else sb.append(singleWord);
            if(i != words.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    private String getTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    private String getMessageHistory(){
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i<Main.messageHistory.size() ; i++){
            sb.append(Main.messageHistory.get(i));
            if(i != Main.messageHistory.size() - 1)
                sb.append("\n");
        }
        return (sb.toString().length()==0) ? "There is no sent messages" : sb.toString();
    }

    private void setForbiddenWords(){
        forbiddenWords.add("politics");
        forbiddenWords.add("kill");
        forbiddenWords.add("idiot");
        forbiddenWords.add("corona");
        forbiddenWords.add("corona-virus");
        forbiddenWords.add("deep-state");
        forbiddenWords.add("war");
        forbiddenWords.add("nigger");
        forbiddenWords.add("nigga");
        forbiddenWords.add("sect");
    }
}
