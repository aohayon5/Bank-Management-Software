package edu.yu.cs.intro.bank2023;

import java.util.Objects;

/**
 * represents the stock of a single company that is listed on the StockExchange
 */
public class StockListing {
    private String tickerSymbol;
    private double price;
    private int availableShares;
    /**
     *
     * @param tickerSymbol
     * @param initialPrice
     * @param availableShares
     * @throws IllegalArgumentException if the tickerSymbol is null or empty, if the initial price is <= 0, of if availableShares <= 0
     */
    protected StockListing(String tickerSymbol, double initialPrice, int availableShares){
        if(tickerSymbol == null
                ||tickerSymbol.equals("")
                || initialPrice <= 0
                || availableShares <= 0){
            throw new IllegalArgumentException();
        }
        this.tickerSymbol = tickerSymbol;
        this.price = initialPrice;
        this.availableShares = availableShares;
    }

    public String getTickerSymbol() {
        return this.tickerSymbol;
    }
    public double getPrice() {
        return this.price;
    }
    public int getAvailableShares() {
       return this.availableShares;
    }

    /**
     * set the price for a single share of this stock
     * @param price
     */
    protected void setPrice(double price) {
        this.price = price;
    }
    /**
     * increase the number of shares available
     * @param availableShares
     * @return the total number of shares after adding availableShares
     * @throws IllegalArgumentException if availableShares <= 0
     */
    protected int addAvailableShares(int availableShares) {
        if (availableShares <= 0){
            throw new IllegalArgumentException();
        }
        this.availableShares += availableShares;
        return this.availableShares;
    }
    /**
     * reduce the number of shares available
     * @param quantityToSubtract
     * @return the total number of shares after reducing availableShares
     * @throws IllegalArgumentException if quantityToSubtract > the number of available shares
     */
    protected int reduceAvailableShares(int quantityToSubtract){
        if (quantityToSubtract > this.availableShares){
            throw new IllegalArgumentException();
        }
        this.availableShares -= quantityToSubtract;
        return this.availableShares;
    }
    
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        StockListing myListing = (StockListing) obj;
        return this.tickerSymbol == myListing.tickerSymbol;
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.tickerSymbol);
    }
}