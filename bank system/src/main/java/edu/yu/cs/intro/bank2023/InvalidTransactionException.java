package edu.yu.cs.intro.bank2023;

public class InvalidTransactionException extends Exception {
private Transaction.TxType type;

    public InvalidTransactionException(String message, Transaction.TxType type){
        super(message);
        this.type = type; //I don't know if this is even being accessed
    }

    public Transaction.TxType getType(){
        return this.type;
    }
}
