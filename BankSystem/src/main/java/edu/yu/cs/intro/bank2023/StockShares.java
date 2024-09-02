package edu.yu.cs.intro.bank2023;

import java.util.Objects;

/**
 * represents the quantity of shares a single Patron owns of single stock/listing
 */
public class StockShares {
    private StockListing listing;
    private int quantity;

    /**
     * @param listing the stock listing this instance is tracking the Patron's shares of
     * @throws IllegalArgumentException if listing is null
     */
    protected StockShares(StockListing listing){
        if (listing == null){
            throw new IllegalArgumentException("listing is null");
        }
        this.listing = listing;
        this.quantity = 0;
    }

    public int getQuantity() {
        return this.quantity;
    }

    protected void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StockListing getListing() {
            return this.listing;
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
        StockShares myListing = (StockShares) obj;
        return this.listing == myListing.listing;
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.listing);
    }
}