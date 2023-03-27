package org.example.http.response;

import com.google.gson.Gson;
import org.example.http.response.Response;
import org.example.model.Quote;

public class JsonResponse extends Response {

    private String jsonQuote;
    private Gson gson = new Gson();

    public JsonResponse(Quote quote) {
        this.jsonQuote = gson.toJson(quote);
    }

    @Override
    public String sendResponseString() {
        // jsonQuote = {"author":"author","content":"quote"}
        // a hocemo "{\"author\":\"author2\",\"content\":\"quote2\"}"
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n";
        response += gson.toJson(jsonQuote, String.class);
        return response;
    }
}
