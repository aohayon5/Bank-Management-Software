package edu.yu.cs.intro.bank2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Models a brokerage account, i.e. an account used to buy, sell, and own stocks
 */
public class BrokerageAccount extends Account{
    private Map<String, StockShares> sharesMap;
    private List<StockShares> los; //list of shares, only one per stock
    /**
     * This will be called by the Bank class.
     * @param accountNumber the account number assigned by the bank to this new account
     * @param patron the Patron who owns this account
     * @see Bank#openNewBrokerageAccount(Patron)
     */
    protected BrokerageAccount(int accountNumber, Patron patron) {
        super(accountNumber, patron);
        this.sharesMap = new HashMap<>();
        this.los = new ArrayList<>();
    }

    /**
     * @return an unmodifiable list of all the shares of stock currently owned by this account
     * @see java.util.Collections#unmodifiableList(List)
     */
    public List<StockShares> getListOfShares(){
        List<StockShares> permaLos = Collections.unmodifiableList(this.los);
        return permaLos; //make sure to add the stuff to my list
    }
    
    /**
     * If the transaction is not an instanceof StockTransaction, throw an InvalidTransactionException.
     *
     * If tx.getType() is BUY, do the following:
     *         If there aren't enough shares of the stock available for purchase, throw an InvalidTransactionException.
     *         The total amount of cash needed for the tx  = tx.getQuantity() * tx.getStock().getPrice(). If the patron doesn't have enough cash in his SavingsAccount for this transaction, throw InsufficientAssetsException.
     *         If he does have enough cash, do the following:
     *         1) reduce available share of StockListing by tx.getQuantity()
     *         2) reduce cash in patron's savings account by tx.getQuantity() * StockListing.getPrice()
     *         3) create a new StockShare for this stock with the quantity set to tx.getQuantity() and listing set to tx.getStock()
     *              (or increase StockShare quantity, if there already is a StockShare instance in this account, by tx.getQuantity())
     *         4) add this to the set of transactions recorded in this account
     *
     * If tx.getType() is SELL, do the following:
     *          //If this account doesn't have the specified number of shares in the given stock, throw an InsufficientAssetsException.
     *          //Reduce the patron's shares in the stock by the tx.getQuantity()
     *          //The revenue from the sale = the current price per share of the stock * number of shares to be sold. Use a DEPOSIT transaction to add the revenue to the Patron's savings account.
     *
     * @param tx the transaction to execute on this account
     * @see StockTransaction
     */
    @Override
    public void executeTransaction(Transaction tx) throws InsufficientAssetsException, InvalidTransactionException {
        if(!(tx instanceof StockTransaction)){
            throw new InvalidTransactionException("invalid tx; not a stock transaction", tx.getType());
        }
        StockTransaction st = (StockTransaction)tx;
        if(st.getType() == Transaction.TxType.BUY){
            if(st.getStock().getAvailableShares() < st.getQuantity()){
                throw new InvalidTransactionException("not enough shares available for the amount you want to buy", st.getType());
            }
            double cashNeeded =  st.getQuantity() * st.getStock().getPrice();
            if (this.getPatron().getSavingsAccount().getValue() < cashNeeded){
                throw new InsufficientAssetsException(st, this.getPatron());
            }
            //1
            st.getStock().reduceAvailableShares(st.getQuantity()); 
            //2
            Transaction ct = new CashTransaction(Transaction.TxType.WITHDRAW, cashNeeded);
            this.getPatron().getSavingsAccount().executeTransaction(ct);
            //3
            if (this.sharesMap == null || !this.sharesMap.containsKey(st.getStock().getTickerSymbol())){
                StockShares mySt = new StockShares(st.getStock());
                mySt.setQuantity(st.getQuantity());
                //Stockshare didn't exist becuase new stock, so add it to the map and to the list.
                this.los.add(mySt);
                this.sharesMap.put(mySt.getListing().getTickerSymbol(), mySt);
            }
            else{
                StockShares myTempShares = this.sharesMap.get(st.getStock().getTickerSymbol());
                myTempShares.setQuantity(myTempShares.getQuantity()+st.getQuantity());
                //Should update in the list and in the map automatically, because the instance is being changed
            }
            super.transactions.add(st);
        }
        else if(st.getType().equals(Transaction.TxType.SELL)){
            StockShares myStock = sharesMap.get(st.getStock().getTickerSymbol());
            //1
            if(myStock == null || st.getQuantity() > myStock.getQuantity()){
                throw new InsufficientAssetsException(st, super.getPatron());
            }
            //2
            myStock.setQuantity(myStock.getQuantity()-st.getQuantity());
            //3
            double revenue = myStock.getListing().getPrice()*st.getQuantity();
           
            super.transactions.add(st); //adding the st first, because selling, and then Depositing
            Transaction ct = new CashTransaction(Transaction.TxType.DEPOSIT, revenue);
            super.getPatron().getSavingsAccount().executeTransaction(ct);
        }//make sure not trying to sell stock that isn't owned
    }

    /**
     * the value of a BrokerageAccount is calculated by adding up the values of each StockShare.
     * The value of a StockShare is calculated by multiplying the StockShare quantity by its listing's price.
     * @return
     */
    @Override
    public double getValue() {//add the value every time you BUY or SELL
        double value = 0;
        for (StockShares sh : los) {
            value +=sh.getQuantity()*sh.getListing().getPrice();
        }
        return value;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        BrokerageAccount ba = (BrokerageAccount) obj;
        return this.getAccountNumber() == ba.getAccountNumber();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getAccountNumber());
    }
}
