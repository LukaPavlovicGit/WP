package org.example.http.response;

public class HtmlResponse extends Response {
    private final String html;

    public HtmlResponse(String html) {
        this.html = html;
    }

    @Override
    public String sendResponseString() {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        response += html;
        return response;
    }
}
