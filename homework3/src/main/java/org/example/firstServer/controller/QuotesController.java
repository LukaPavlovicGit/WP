package org.example.firstServer.controller;
import com.google.gson.Gson;
import org.example.controller.Controller;
import org.example.http.Request;
import org.example.http.response.HtmlResponse;
import org.example.http.response.RedirectResponse;
import org.example.http.response.Response;
import org.example.model.Quote;
import org.example.repository.QuoteRepository;


import java.io.*;
import java.net.Socket;

public class QuotesController extends Controller {
    private Request request;
    private BufferedReader inputFromServer;
    private PrintWriter outputToServer;
    private Gson gson = new Gson();
    private String requestLine;

    public QuotesController(Request request) {
        super(request);
        this.request = request;
    }

    @Override
    public Response doGet() {
        StringBuilder bodyBuilder = new StringBuilder();
        // post se izvrsava na \save-quote putanji
        bodyBuilder.append("<form method=\"POST\" action = \"/save-quote\">");
        bodyBuilder.append("<label>Author: </label><input name=\"author\" type=\"text\"><br><br>");
        bodyBuilder.append("<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>");
        bodyBuilder.append("<button type = \"submit\">Add quote</button>");
        bodyBuilder.append("</form>");

        bodyBuilder.append("<form>");
        bodyBuilder.append( "<h1>Saved quotes</h1>");
        for(Quote quote : QuoteRepository.getInstance().getQuotes()){
            bodyBuilder.append("<br>");
            bodyBuilder.append(quote.toString());
            bodyBuilder.append("<br>");
        }
        bodyBuilder.append("</form>");

        try {
            Socket socket = new Socket("localhost", 8081);
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            outputToServer.println("GET /qod HTTP/1.1\n" + "Accept: application/json\r\n\r\n");
            requestLine = inputFromServer.readLine();
            do {
                System.out.println(requestLine);
                requestLine = inputFromServer.readLine();
            } while (!requestLine.trim().equals(""));

            String quoteOfTheDay = gson.fromJson(inputFromServer.readLine(), String.class);
            bodyBuilder.append("<h1>Quote of the Day</h1>\n" +"<h2>" + quoteOfTheDay + "</h2>");

        } catch (IOException e) { e.printStackTrace();}

        return new HtmlResponse(bodyBuilder.toString());
    }

    @Override
    public Response doPost() {
        QuoteRepository.getInstance().addQuote(request.getQuote());
        return new RedirectResponse("/quotes");
    }
}
