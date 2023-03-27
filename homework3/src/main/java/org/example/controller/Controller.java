package org.example.controller;

import org.example.http.Request;
import org.example.http.response.Response;

public abstract class Controller {
    protected Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public abstract Response doGet();
    public abstract Response doPost();
}
