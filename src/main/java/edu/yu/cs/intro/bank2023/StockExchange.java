package edu.yu.cs.intro.bank2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockExchange {
    private Map<String, StockListing> stocks;
    private List<StockListing> allListings;
    protected StockExchange(){
        this.stocks = new HashMap<>();
        this.allListings = new ArrayList<>();
    }
    /**
     *
     * @param tickerSymbol symbol of the new stock to be created, e.g. "IBM", "GOOG", etc.
     * @param initialPrice price of a single share of the stock
     * @param availableShares how many shares of the stock are available initially
     * @throws IllegalArgumentException if there's already a listing with that tickerSymbol
     */
    public void createNewListing(String tickerSymbol, double initialPrice, int availableShares){
        if (this.stocks.containsKey(tickerSymbol)){
            throw new IllegalArgumentException();
        }
        StockListing newLisiting = new StockListing(tickerSymbol, initialPrice, availableShares);
        this.stocks.put(tickerSymbol, newLisiting);
        this.allListings.add(newLisiting);
    }

    /**
     * @param tickerSymbol
     * @return the StockListing object for the given tickerSymbol, or null if there is none
     */
    public StockListing getStockListing(String tickerSymbol){
        return this.stocks.get(tickerSymbol);
    }

    /**
     * @return an umodifiable list of all the StockListings currently found on this exchange
     * @see java.util.Collections#unmodifiableList(List)
     */
    public List<StockListing> getAllCurrentListings(){
        List<StockListing> permaListings = Collections.unmodifiableList(this.allListings);
        return permaListings;
    }
}