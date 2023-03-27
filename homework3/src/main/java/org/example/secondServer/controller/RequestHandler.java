package org.example.secondServer.controller;

import org.example.http.HttpMethods;
import org.example.http.Request;
import org.example.http.response.Response;

public class RequestHandler {
    public Response handle(Request request) throws Exception {
        if (request.getPath().equals("/qod") && request.getHttpMethod().equals(HttpMethods.GET)) {
            return (new QuotesController(request)).doGet();
        }
        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");
    }
}
