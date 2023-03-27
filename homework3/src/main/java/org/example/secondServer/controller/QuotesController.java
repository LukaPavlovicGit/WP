package org.example.secondServer.controller;


import com.google.gson.Gson;
import org.example.controller.Controller;
import org.example.http.Request;
import org.example.http.response.JsonResponse;
import org.example.http.response.Response;
import org.example.model.Quote;
import org.example.repository.QuoteRepository;

public class QuotesController extends Controller {
    private Gson gson = new Gson();
    public QuotesController(Request request) {
        super(request);
    }
    @Override
    public Response doGet() {
        Quote quote = QuoteRepository.getInstance().getRandomQuote();
        return new JsonResponse(quote);
    }
    @Override
    public Response doPost() {
        return null;
    }

}
