package org.example.firstServer;
import org.example.firstServer.controller.RequestHandler;
import org.example.http.response.Response;
import org.example.http.HttpMethods;
import org.example.http.Request;
import org.example.model.Quote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String bodyLength;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String line = in.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("Client has sent a HTTP request");
            do {
                System.out.println(line);
                line = in.readLine();
                if (line.startsWith("Content-Length:")) {
                    bodyLength = line.substring(16);
                }
            } while (!line.trim().equals(""));


            Quote quote = null;
            if (method.equals(HttpMethods.POST.toString())) {
                char[] buffer = new char[Integer.parseInt(bodyLength)];
                in.read(buffer);
                String parameters = new String(buffer);
                String[] array = parameters.split("&");
                String author = array[0].split("=")[1].replace("+", " ");
                String content = array[1].split("=")[1].replace("+", " ");
                quote = new Quote(author, content);
            }

            Request request = new Request(HttpMethods.valueOf(method), path, quote);
            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);
            out.println(response.sendResponseString());

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {e.printStackTrace();}

    }
}
