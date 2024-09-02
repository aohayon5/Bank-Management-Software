package edu.yu.cs.intro.bank2023;

import java.util.Objects;

public class SavingsAccount extends Account{
    private double balance;
    protected SavingsAccount(int accountNumber, Patron patron) {
        super(accountNumber, patron);
        this.balance = 0;
    }

    /**
     * for a DEPOSIT transaction: increase the balance by transaction amount
     * for a WITHDRAW transaction: decrease the balance by transaction amount
     * add the transaction to the transaction history of this account
     * @param tx
     * @return
     * @throws InvalidTransactionException thrown if tx is not a CashTransaction
     */
    @Override
    public void executeTransaction(Transaction tx) throws InsufficientAssetsException, InvalidTransactionException {
        if (!(tx instanceof CashTransaction)){
            throw new InvalidTransactionException("Not a cash transaction", tx.getType());
        }
        CashTransaction ct = (CashTransaction) tx;
        switch (ct.getType()){ //why not tx.getType, either one works
            case DEPOSIT:
                balance += ct.getAmount();
            break;

            case WITHDRAW:
                if (balance-ct.getAmount() < 0){
                    throw new InsufficientAssetsException(ct, this.getPatron());
                }
                else{
                    balance -= ct.getAmount();
                }    
                break;
            default: //getting rid of annoying highlight message that is wrong in vs
            break;
        }
        super.transactions.add(ct);
    }

    /**
     * @return the account's balance
     */
    @Override
    public double getValue() {
        return this.balance;
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
        SavingsAccount sa = (SavingsAccount) obj;
        return this.getAccountNumber() == sa.getAccountNumber();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getAccountNumber());
    }
}