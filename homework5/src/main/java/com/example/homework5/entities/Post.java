package com.example.homework5.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Post {
    private int id;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Long dateLong;

    public Post(int id, String author, String title, String content, Long dateLong) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.dateLong = dateLong;
    }

    public Post(int id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Post(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Post() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
    }
}
