package org.example.http.response;

public class RedirectResponse extends Response {
    private String location;

    public RedirectResponse(String location) {
        this.location = location;
    }

    @Override
    public String sendResponseString() {
        String response = "HTTP/1.1 301 OK\r\nLocation: " + this.location + "\r\n\r\n";
        return response;
    }
}
