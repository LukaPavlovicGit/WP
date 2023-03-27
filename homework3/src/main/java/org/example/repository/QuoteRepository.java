package org.example.repository;

import org.example.model.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteRepository {
    private static QuoteRepository instance = null;
    private List<Quote> quotes = new ArrayList<>();

    private Random random = new Random();

    private QuoteRepository(){
        initQuotes();
    }

    public static QuoteRepository getInstance(){
        if(instance == null){
            instance = new QuoteRepository();
        }
        return instance;
    }


    public void addQuote(Quote quote){
        quotes.add(quote);
        System.out.println(quotes.size());
    }

    public Quote getRandomQuote(){
        System.out.println(quotes.size());
        return quotes.get(random.nextInt(quotes.size()));
    }

    public List<Quote> getQuotes(){
        return quotes;
    }

    private void initQuotes(){
        quotes.add(new Quote("author1","quote1"));
        quotes.add(new Quote("author2","quote2"));
        quotes.add(new Quote("author3","quote3"));
        quotes.add(new Quote("author4","quote4"));
        quotes.add(new Quote("author5","quote5"));
        quotes.add(new Quote("author6","quote6"));
    }
}
