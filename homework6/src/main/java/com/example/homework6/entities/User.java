package com.example.homework6.entities;

public class User {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String role;
    private String hashedPassword;

    public User() {
    }

    public User(String username, String role, String hashedPassword) {
        this.username = username;
        this.role = role;
        this.hashedPassword = hashedPassword;
    }

    public User(String username, String firstname, String lastname, String role, String hashedPassword) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.hashedPassword = hashedPassword;
    }

    public User(int id, String username, String firstname, String lastname, String role, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
