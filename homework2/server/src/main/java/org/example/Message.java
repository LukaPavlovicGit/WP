package org.example;

public class Message {
    private String message;
    private String sender;
    private String dateAndTime;

    public Message(String message, String sender, String dateAndTime) {
        this.message = message;
        this.sender = sender;
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        return "Message: " + '\'' + message + '\'' +
                ", sender: '" + sender + '\'' +
                ", dateAndTime: '" + dateAndTime + '\'';
    }
}
