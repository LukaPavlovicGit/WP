package org.example.http;

import org.example.model.Quote;

public class Request {

    private final HttpMethods httpMethods;
    private final String path;
    private Quote quote;

    public Request(HttpMethods httpMethods, String path, Quote quote) {
        this.httpMethods = httpMethods;
        this.path = path;
        this.quote = quote;
    }

    public HttpMethods getHttpMethod() {
        return httpMethods;
    }
    public String getPath() {
        return path;
    }
    public Quote getQuote() {
        return quote;
    }
}
