package edu.yu.cs.intro.bank2023;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Bank {
    private Set<Account> accounts;
    private Set<Patron> patrons;
    private StockExchange exchange;
    private int patronId;
    private int accountId;
    /**
     * @param exchange the stock exchange on which all stock are listed
     * @throws IllegalArgumentException if exchange is null
     */
    protected Bank(StockExchange exchange){
        if (exchange == null){
            throw new IllegalArgumentException();
        }
        this.exchange = exchange;
        this.patronId = 1;
        this.accountId = 1;
        this.accounts = new HashSet<Account>();
        this.patrons = new HashSet<Patron>();
    }
    /**
     * Create a new Patron whose ID is the next unique available Patron ID and whose Bank is set to this bank.
     * Add the new Patron to the Bank's Set of Patrons.
     * No two Patrons can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * @return a new Patron with a unique ID, but no accounts
     */
    public Patron createNewPatron(){
        Patron newGuy = new Patron(patronId, this);
        this.patrons.add(newGuy);
        this.patronId++;
        return newGuy;
    }

    /**
     * Create a new SavingsAccount for the Patron.
     * The SavingsAccount's id must be the next unique account ID available.
     * No two accounts of ANY KIND can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * Add the new SavingsAccount to the Bank's Set of Accounts.
     * @param p the Patron for whom the account is being created
     * @return the SavingsAccount's id
     * @throws ApplicationDeniedException thrown if Patron already has a SavingsAccount
     * @throws IllegalArgumentException if p is null
     */
    public int openNewSavingsAccount(Patron p) throws ApplicationDeniedException{ //define contains
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (p.getSavingsAccount() != null){//if the patron is mentioned twice
            throw new ApplicationDeniedException("U trippin foo, bro's already got a savings account, can't have two homie");
        }
        Account newAcc = new SavingsAccount(this.accountId, p);
        this.accounts.add(newAcc);
        p.setSavingsAccount((SavingsAccount)newAcc);
        this.accountId++;
        return this.accountId-1;
    }

    /**
     * Create a new BrokerageAccount for the Patron.
     * The BrokerageAccount's id must be the next unique account ID available.
     * No two accounts of ANY KIND can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * Add the new BrokerageAccount to the Bank's Set of Accounts.
     * @param p the Patron for whom the account is being created
     * @return the BrokerageAccount's id
     * @throws ApplicationDeniedException thrown if the Patron doesn't have a SavingsAccount or DOES already have a BorkerageAccount
     * @throws IllegalArgumentException if p is null
     */
    public int openNewBrokerageAccount(Patron p)throws ApplicationDeniedException{
        if (p==null){
            throw new IllegalArgumentException();
        }
        if (p.getSavingsAccount() == null){
            throw new ApplicationDeniedException("Stop trying to open a brokerage account if you got no savings- not allowed");
        }
        if (p.getBrokerageAccount() != null){
            throw new ApplicationDeniedException("Don't be greedy, ya already a brokereage account, can't have two");
        }
        Account newAcc = new BrokerageAccount(this.accountId, p);
        this.accounts.add(newAcc);
        p.setBrokerageAccount((BrokerageAccount)newAcc);
        this.accountId++;
        return this.accountId-1;
    }

    /**
     *
     * @return an unmodifiable set of all the accounts (both Savings and Brokerage)
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    protected Set<Account> getAllAccounts() {
        Set<Account> permAccounts = Collections.unmodifiableSet(this.accounts);
        return permAccounts;
    }

    /**
     *
     * @return an unmodifiable set of all the Patrons
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    protected Set<Patron> getAllPatrons() {
        Set<Patron> permaPatron = Collections.unmodifiableSet(this.patrons);
        return permaPatron;
    }

    /**
     * @return the exchange used by this Bank
     */
    protected StockExchange getExchange() {
        return this.exchange;
    }
}