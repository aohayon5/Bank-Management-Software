package edu.yu.cs.intro.bank2023;

import java.util.Objects;

/**
 * A patron (i.e. customer) of the bank, who can have up to 2 accounts - 1 savings account, 1 brokerage account.
 */
public class Patron {
    private final int id;
    private Bank bank;
    private SavingsAccount savings;
    private BrokerageAccount brokerage;
    /**
     * Will be called by Bank
     * MUST be created with its SavingsAccount and BrokerageAccount being null. Accounts are to be created ONLY via the appropriate calls to the Bank class.
     * @param id account id assigned by the bank
     * @param bank
     * @see Bank#createNewPatron()
     * @see Bank#openNewSavingsAccount(Patron)
     * @see Bank#openNewBrokerageAccount(Patron)
     */
    protected Patron(int id, Bank bank){
        this.id = id;
        this.bank = bank;
        this.savings = null;
        this.brokerage = null;
    }

    public int getId() {
        return this.id;
    }

    protected Bank getBank() {
        return this.bank;
    }

    /**
     *
     * @param savings
     * @throws ApplicationDeniedException thrown if the Patron already has a savings account
     * @see Bank#openNewSavingsAccount(Patron)
     */
    protected void setSavingsAccount(SavingsAccount savings) throws ApplicationDeniedException{
        if (this.savings != null){
            throw new ApplicationDeniedException("Bro, you already got a savings account, can't have two");
        }
        this.savings = savings;
    }

    /**
     *
     * @param brokerage
     * @throws ApplicationDeniedException thrown if the Patron already has a BrokerageAccount OR if the Patron does NOT have a SavingsAccount
     * @see Bank#openNewBrokerageAccount(Patron)
     */
    protected void setBrokerageAccount(BrokerageAccount brokerage) throws ApplicationDeniedException{
        if(this.savings == null){
            throw new ApplicationDeniedException("You can't set a brokereage account if you do not have a savings account");
        }
        if (this.brokerage != null){
           throw new ApplicationDeniedException("Don't be greedy, ya already a brokereage account, so you can't set a second one");
        }
        this.brokerage = brokerage;
    }

    /**
     * @return the value of the Patron's SavingsAccount + the value of the Patron's BrokerageAccount
     * @see SavingsAccount#getValue()
     * @see BrokerageAccount#getValue()
     */
    public double getNetWorth(){
        if (this.savings == null){
            return 0;
        }
        return this.savings.getValue() + (this.brokerage == null ? 0 : this.brokerage.getValue());
    }

    public SavingsAccount getSavingsAccount() {
      return this.savings;
    }

    public BrokerageAccount getBrokerageAccount() {
        return this.brokerage;
    }
    @Override
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
        Patron ePatron = (Patron) obj;
        return this.id == ePatron.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }
}