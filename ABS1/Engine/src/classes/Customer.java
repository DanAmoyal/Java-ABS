package classes;

import java.util.ArrayList;
import java.util.List;

import classes.operations.Transaction;
import manager.timeline;
import resources.generated.generated.AbsCustomer;

public class Customer {

    private String name;
    private double balance;

    private List<Transaction> transactionList = new ArrayList<Transaction>();

    private List<Loan> loansGivenIndexes = new ArrayList<>();
    private List<Loan> loansTakenIndexes = new ArrayList<>();

    private List<String> notifications = new ArrayList<>();


    public Customer(AbsCustomer c)
    {
        name = c.getName();
        balance = c.getAbsBalance();
    }


    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public List<Loan> getGiven(){
        return loansGivenIndexes;
    }

    public List<Loan> getTaken(){
        return loansTakenIndexes;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransaction(Transaction t)
    {
        transactionList.add(t);
        balance = balance + t.getAmount();

    }

    public void addMoney(int amount){
        Transaction transaction = new Transaction(timeline.getCurrentDate(), amount, this.balance);
        this.transactionList.add(transaction);
        this.balance =  balance + amount;
    }

    public void drawMoney(int amount){
        Transaction transaction = new Transaction(timeline.getCurrentDate(), -1*amount, this.balance);
        this.transactionList.add(transaction);
        this.balance =  balance - amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public List<String> getNotifications() {
        return notifications;
    }
}
