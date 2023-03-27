package org.example.firstServer.controller;

import org.example.http.HttpMethods;
import org.example.http.Request;
import org.example.http.response.Response;

public class RequestHandler {

    public Response handle(Request request) throws Exception {
        if (request.getPath().equals("/quotes") && request.getHttpMethod().equals(HttpMethods.GET))
            return (new QuotesController(request)).doGet();
        else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HttpMethods.POST))
            return (new QuotesController(request)).doPost();
        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");
    }
}
