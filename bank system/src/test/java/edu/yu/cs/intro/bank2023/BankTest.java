package edu.yu.cs.intro.bank2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.TransferHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class BankTest {
    StockExchange exchange = new StockExchange();
    Bank bank = new Bank(exchange);

    //Bank
    @Test
    public void checkNullExchange() throws Exception{
       try{
             Bank nullBank = new Bank(null);
             throw new Exception();
        }
        catch (IllegalArgumentException e){
        }
    }
    @Test
    public void createNewPatron() throws Exception{
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        if(p3.getId() <= p2.getId() || p2.getId()+1 != p3.getId()){
            throw new Exception();
        }
        if (p1.getBrokerageAccount() != null || p1.getSavingsAccount() != null){
            throw new Exception();
        }
    }
    @Test
    public void openNewSavingsAccount() throws Exception{
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        bank.openNewSavingsAccount(p1);
        bank.openNewSavingsAccount(p2);
        bank.openNewSavingsAccount(p3);
        Patron pNull = null;
        assertThrows(IllegalArgumentException.class,  ()->{bank.openNewSavingsAccount(pNull);}); 
        assertThrows(ApplicationDeniedException.class,  ()->{
            bank.openNewSavingsAccount(p1);
        });
        assertEquals(1, p1.getSavingsAccount().getAccountNumber());
        assertEquals(3, p3.getSavingsAccount().getAccountNumber());
    }
    @Test
    public void openNewBrokerageAccount() throws Exception{
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        Patron p4 = bank.createNewPatron();
        int xp1 = bank.openNewSavingsAccount(p1);
        int xp2 = bank.openNewSavingsAccount(p4);
        int xp3 = bank.openNewBrokerageAccount(p4);
        int xp4 = bank.openNewSavingsAccount(p2);
        int xp5 = bank.openNewBrokerageAccount(p1);
        int xp6 = bank.openNewBrokerageAccount(p2);    
        Patron pNull = null;
        assertThrows(IllegalArgumentException.class,  ()->{bank.openNewBrokerageAccount(pNull);}); 
        assertThrows(ApplicationDeniedException.class,  ()->{bank.openNewBrokerageAccount(p3);}); 
        assertThrows(ApplicationDeniedException.class,  ()->{bank.openNewBrokerageAccount(p1);}); 
       //makes sure the return is accurate
        assertEquals(1, xp1);
        assertEquals(2, xp2);
        assertEquals(3, xp3);
        assertEquals(4, xp4);
        assertEquals(5, xp5);
        assertEquals(6, xp6);    
        //makes sure sets are accurate
        assertEquals(1, p1.getSavingsAccount().getAccountNumber());
        assertEquals(2, p4.getSavingsAccount().getAccountNumber());
        assertEquals(3, p4.getBrokerageAccount().getAccountNumber());
        assertEquals(4, p2.getSavingsAccount().getAccountNumber());
        assertEquals(5, p1.getBrokerageAccount().getAccountNumber());
        assertEquals(6, p2.getBrokerageAccount().getAccountNumber());
    }
    @Test
    public void getAllAccounts() throws Exception{
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        Patron p4 = bank.createNewPatron();
        int xp1 = bank.openNewSavingsAccount(p1);
        int xp2 = bank.openNewSavingsAccount(p4);
        int xp3 = bank.openNewBrokerageAccount(p4);
        int xp4 = bank.openNewSavingsAccount(p2);
        int xp5 = bank.openNewBrokerageAccount(p1);
        int xp6 = bank.openNewBrokerageAccount(p2);
        Set<Account> myAcc = bank.getAllAccounts();
        Patron p5 = new Patron(5, bank);
        SavingsAccount sa5 = new SavingsAccount(7, p5);
        SavingsAccount fa = new SavingsAccount(xp6, p2);
        assertFalse(myAcc.contains(sa5));
        assertFalse(myAcc.contains(fa));
        assert(myAcc.size() == 6);
        boolean be= false;
        for (Account ee : myAcc){
            if (ee.getAccountNumber() == 5) 
            be = true;
        }
        assertTrue(be);
    }
    @Test
    public void getAllPatrons(){
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        Patron p4 = bank.createNewPatron();
        Set<Patron> pSet = bank.getAllPatrons();
        Patron p5 = new Patron(5, bank);
        assert(pSet.contains(p1));
        assert(pSet.contains(p2));
        assert(pSet.contains(p3));
        assert(pSet.contains(p4));
        assertFalse(pSet.contains(p5)); //mysteriously but righfully true;
    }
    @Test
    void getExchange(){
        assertEquals(exchange, bank.getExchange());
        StockExchange ex = new StockExchange();
        assertNotEquals(ex, bank.getExchange());
    }




    //Exceptions
    @Test
    void InsufficientAssetsException() throws Exception{
        //not checking getTx
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        InsufficientAssetsException ex1 = new InsufficientAssetsException(new CashTransaction(Transaction.TxType.DEPOSIT, 50), p1);
        InsufficientAssetsException ex3 = new InsufficientAssetsException(new StockTransaction(new StockListing("stocky", 43.3, 5), Transaction.TxType.SELL, 50), p2);
        try {
            InsufficientAssetsException ex2 = new InsufficientAssetsException(new CashTransaction(Transaction.TxType.SELL, 50), p1);
            throw new Exception();
        } catch (InvalidTransactionException e) {
            //success
        }
        assertEquals(p1, ex1.getPatron());
        assertEquals(p2, ex3.getPatron());
    }

    @Test
    void InvalidTransactionException(){
        InvalidTransactionException exBUY = new InvalidTransactionException("",Transaction.TxType.BUY);
        assertEquals(Transaction.TxType.BUY, exBUY.getType());

        InvalidTransactionException exSell = new InvalidTransactionException("",Transaction.TxType.SELL);
        assertEquals(Transaction.TxType.SELL, exSell.getType());
    }


    @Test
    void ApplicationDeniedException(){
        Exception ae = new ApplicationDeniedException("Whop");
        assert(ae instanceof Exception);
        assert(ae instanceof ApplicationDeniedException);
    }

    //StockExchange
    @Test
    void StockExchange(){
        exchange.createNewListing("s1", 55.5, 10);
        assertThrows(IllegalArgumentException.class,  ()->{
            exchange.createNewListing("s1", 50, 4);
        });
        exchange.createNewListing("s2", 13.7, 14);
        exchange.createNewListing("s3", 5, 7);
        assertEquals(new StockListing("s2", 13.7, 14), exchange.getStockListing("s2"));
        List<StockListing> pList = exchange.getAllCurrentListings();
        assertEquals("s1", pList.get(0).getTickerSymbol());
        assertEquals(13.7, pList.get(1).getPrice());
        assertEquals(7, pList.get(2).getAvailableShares());

        assertThrows(UnsupportedOperationException.class,  ()->{
            pList.set(0, new StockListing("pq", 5, 4));
        });
    }


    //Stocklisting
    @Test
    void StockListing(){
        assertThrows(IllegalArgumentException.class,  ()->{
            StockListing l1 = new StockListing(null, 5, 5);
        });
        assertThrows(IllegalArgumentException.class,  ()->{
            StockListing l1 = new StockListing("", 5, 5);
        });
         assertThrows(IllegalArgumentException.class,  ()->{
            StockListing l1 = new StockListing("war", 0, 5);
        });
        assertThrows(IllegalArgumentException.class,  ()->{
            StockListing l1 = new StockListing("war", 5, 0);
        });
        assertThrows(IllegalArgumentException.class,  ()->{
            StockListing l1 = new StockListing("war", -4, 5);
        });
        StockListing l1 = new StockListing("stock1", 15.5, 5); 
        assertEquals("stock1", l1.getTickerSymbol());
        assertEquals(15.5, l1.getPrice());
        assertEquals(5, l1.getAvailableShares());
        l1.setPrice(14.73795);
        assertEquals(14.73795, l1.getPrice());
        assertThrows(IllegalArgumentException.class,  ()->{
            l1.addAvailableShares(0);
        });
        assertEquals(12, l1.addAvailableShares(7));
        assertEquals(12, l1.getAvailableShares());
        assertThrows(IllegalArgumentException.class,  ()->{
            l1.reduceAvailableShares(13);
        });
        
        assertEquals(5, l1.reduceAvailableShares(7));
        assertEquals(5, l1.getAvailableShares());
    }



    //StockShares
    @Test
    void StockShares(){
        assertThrows(IllegalArgumentException.class,  ()->{
            StockShares s = new StockShares(null);
        });
        StockListing l1 = new StockListing("stock1", 15.5, 5); 
        StockShares s = new StockShares(l1);
        s.setQuantity(55);
        assertEquals(55, s.getQuantity());
        //this won't work if you didn't define your own equals, which is ok
        StockListing l2 = new StockListing("stock1", 15.5, 5); 
        assertEquals(l2, s.getListing());
    }


    //Transactions
    @Test
    void CashTransaction() throws Exception{
        assertThrows(InvalidTransactionException.class,  ()->{
            CashTransaction xyz = new CashTransaction(Transaction.TxType.SELL, 5);        
        });
        assertThrows(InvalidTransactionException.class,  ()->{
            CashTransaction xyz = new CashTransaction(Transaction.TxType.BUY, 5);        
        });
        assertThrows(InvalidTransactionException.class,  ()->{
            CashTransaction xyz = new CashTransaction(Transaction.TxType.DEPOSIT, 0);        
        });
        CashTransaction ct = new CashTransaction(Transaction.TxType.DEPOSIT, 5.79);
        assertEquals(5.79, ct.getAmount());
        assertEquals(Transaction.TxType.DEPOSIT, ct.getType());
        CashTransaction ct2 = new CashTransaction(Transaction.TxType.WITHDRAW, 15.25);
        ct.getNanoTimestamp();
    }

    @Test
    void StockTransaction()throws Exception{
        StockListing l1 = new StockListing("stock1", 15.5, 5); 
        assertThrows(InvalidTransactionException.class,  ()->{
            StockTransaction xyz = new StockTransaction(l1, Transaction.TxType.DEPOSIT, 5);        
        });
        assertThrows(InvalidTransactionException.class,  ()->{
            StockTransaction xyz = new StockTransaction(l1, Transaction.TxType.WITHDRAW, 5);        
        });
        assertThrows(InvalidTransactionException.class,  ()->{
            StockTransaction xyz = new StockTransaction(l1, Transaction.TxType.SELL, 0);        
        });
        assertThrows(InvalidTransactionException.class,  ()->{
            StockTransaction xyz = new StockTransaction(null, Transaction.TxType.BUY, 5);        
        });
        StockTransaction s1 = new StockTransaction(l1, Transaction.TxType.BUY, 5);        
        StockTransaction s2 = new StockTransaction(l1, Transaction.TxType.SELL, 10);        

        assertEquals(l1, s2.getStock());
        assertEquals(5, s1.getQuantity());
        assertEquals(Transaction.TxType.SELL, s2.getType());
        Long xsm = s2.getNanoTimestamp();
    }
    

    //Savings Account
    @Test
    void savingsAccount() throws Exception{
        Patron p1 = bank.createNewPatron();
        Patron p2 = bank.createNewPatron();
        Patron p3 = bank.createNewPatron();
        Patron p4 = bank.createNewPatron();
        SavingsAccount sa1 = new SavingsAccount(1, p1);
        SavingsAccount sa2 = new SavingsAccount(2, p2);
        p2.setSavingsAccount(sa2);
        assertEquals(1, sa1.getAccountNumber());
        assertEquals(p2, sa2.getPatron());
        assertThrows(InvalidTransactionException.class,  ()->{
            sa1.executeTransaction(new StockTransaction(new StockListing("wr", 2, 2), Transaction.TxType.BUY, 5));
        });
        CashTransaction ct = new CashTransaction(Transaction.TxType.DEPOSIT, 5.79);
        sa2.executeTransaction(ct);
        CashTransaction ct2 = new CashTransaction(Transaction.TxType.WITHDRAW, 15.25);
        assertEquals(5.79, sa2.getValue());
        try {
            sa2.executeTransaction(ct2);
            throw new Exception();
        } catch (InsufficientAssetsException e) {
            //safe (I'm to lazy for assert throws lol)
        }
        CashTransaction ct3 = new CashTransaction(Transaction.TxType.WITHDRAW, 5);
        sa2.executeTransaction(ct3);
        assertEquals(.79, sa2.getValue());
        CashTransaction ct4 = new CashTransaction(Transaction.TxType.WITHDRAW, .04);
        sa2.executeTransaction(ct4);
        List<Transaction> myList = Collections.unmodifiableList(sa2.getTransactionHistory());
        assertTrue(myList.contains(ct));
        assertFalse(myList.contains(ct2));
        assertEquals(ct, myList.get(0));
        assertEquals(ct3, myList.get(1));
        assertEquals(ct4, myList.get(2));
        //assumes hashcode method implemented
        int hc1 = sa1.hashCode();
        int hc1_1 = sa1.hashCode();
        assertEquals(hc1, hc1_1);
        //sa1.executeTransaction()
        CashTransaction ct5 = new CashTransaction(Transaction.TxType.WITHDRAW, .5);
        sa2.executeTransaction(ct5);
        assertEquals(.25, sa2.getValue());
        assertTrue(myList.contains(ct5)); //this I feel like should return false, let me know if it acts differently for you.
        assertEquals(ct5, myList.get(3));
        assertEquals(.25, p2.getNetWorth());
    }


    @Test
    void BrokerageAccount() throws Exception {
        Patron p1 = bank.createNewPatron();
        SavingsAccount sa1 = new SavingsAccount(1, p1);
        p1.setSavingsAccount(sa1);
        CashTransaction ct = new CashTransaction(Transaction.TxType.DEPOSIT, 50);
        sa1.executeTransaction(ct);
        assertEquals(50, p1.getNetWorth());
        BrokerageAccount ba1 = new BrokerageAccount(2, p1);
        StockListing l1 = new StockListing("stock1", 4, 15); 
        p1.setBrokerageAccount(ba1);

        StockTransaction str1 = new StockTransaction(l1, Transaction.TxType.BUY, 16);
        assertThrows(InvalidTransactionException.class,  ()->{
            ba1.executeTransaction(str1);
        });

        StockTransaction str2 = new StockTransaction(l1, Transaction.TxType.BUY, 13);
        assertThrows(InsufficientAssetsException.class,  ()->{
        ba1.executeTransaction(str2);
        });
        assertEquals(50, p1.getNetWorth());
        StockTransaction st1 = new StockTransaction(l1, Transaction.TxType.BUY, 2);
        ba1.executeTransaction(st1);
        assertEquals(13, l1.getAvailableShares());
        assertEquals(42, sa1.getValue());
        
        List<StockShares> los = ba1.getListOfShares();//it still gets modifies is fine, the mechancichs are complicatec (ask)
        assert(los.size() == 1);
        assertEquals(2, los.get(0).getQuantity());
        assertEquals(50, p1.getNetWorth());
        StockTransaction st2 = new StockTransaction(l1, Transaction.TxType.BUY, 1);
        ba1.executeTransaction(st2);
        assertEquals(12, l1.getAvailableShares());
        assertEquals(38, sa1.getValue());

        assertEquals(3, los.get(0).getQuantity());
        assert(los.size() == 1);
        assertEquals(12, ba1.getValue());

        StockTransaction str3 = new StockTransaction(l1, Transaction.TxType.SELL, 5);
        assertThrows(InsufficientAssetsException.class,  ()->{
            ba1.executeTransaction(str3);
        });
        StockTransaction st3 = new StockTransaction(l1, Transaction.TxType.SELL, 2);
        ba1.executeTransaction(st3);
        assertTrue(los.get(0).getQuantity() == 1);
        assertEquals(46, sa1.getValue());
        //Account Tests
        assertEquals(2, ba1.getAccountNumber());
        assertEquals(p1, ba1.getPatron());

        List myListBA = ba1.getTransactionHistory();
        List myListSa = sa1.getTransactionHistory();
        assertEquals(3, myListBA.size());
        assertEquals(4, myListSa.size());
        assertEquals(50, p1.getNetWorth());
    }




    //Patron
    @Test
    void Patron(){
        Patron p1 = new Patron(1, bank);
        assertEquals(1, p1.getId());
        assertEquals(bank, p1.getBank());
        //tested elsewhere, or eyes

    }

}







































