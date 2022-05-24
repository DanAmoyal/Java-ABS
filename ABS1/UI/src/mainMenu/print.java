package mainMenu;

import classes.Customer;
import classes.Loan;

import java.util.ArrayList;
import java.util.List;

import classes.operations.Transaction;
import classes.operations.loanPayment;
import manager.initializer;
import manager.timeline;
import org.omg.CosNaming.BindingIterator;

public class print {
    public static void menu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("                MENU                ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("1. Load XML file.");
        System.out.println("2. Loan details and status.");
        System.out.println("3. Customer details.");
        System.out.println("4. Charge money to an account.");
        System.out.println("5. Draw money from an account.");
        System.out.println("6. Manage loans.");
        System.out.println("7. Advance timeline.");
        System.out.println("8. Exit.");
    }
    public static void listOfLoans(List<Loan> loanList){
        int i = 1;
        for(Loan loan : loanList){
            System.out.println("Loan #" + i);
            print.loanDetails(loan);
            i++;
        }
    }

    public static void loanDetails(Loan loan){
        System.out.println("ID: " + loan.getId());
        System.out.println("Owner: " + loan.getOwnerName());
        System.out.println("Category: " + loan.getCategory());
        System.out.println("Capital: " + loan.getCapital());
        System.out.println("Total months for the loan: " + loan.getTotalMonths());
        System.out.println("Interest rate:" + loan.getInterestRate());
        System.out.println("Months per payment: " + loan.getMonthsPerPayment());
        System.out.println("Status: " + loan.getStatus());
        switch (loan.getStatusInt())
        {
            case -1:
            {
                System.out.println("ERROR enum value is -1");
            }
            case 1: //PENDING
            {
                loan.getInvolvedCustomers().forEach( (c, amount)  -> System.out.println(c.getName() + " paid " + amount));
                System.out.print("Collected capital:" + loan.getMoneyInvestedSoFar());
                System.out.print("Remaining capital:" + loan.getMoneyNeeded());
                break;
            }
            case 2: //ACTIVE
            {
                loan.getInvolvedCustomers().forEach( (c, amount)  -> System.out.println(c.getName() + " paid " + amount));
                System.out.println("Active since month:" + loan.getDateOfActivation());
                System.out.println("Next payment on month:" + loan.getNextPayment(timeline.getCurrentDate()));
                double capitalSum = 0;
                double interestSum = 0;
                boolean risk = false;
                List<loanPayment> unpaidList = new ArrayList<loanPayment>();
                for(loanPayment lp : loan.getPayList())
                {
                    if (!lp.isPaid())
                    {
                        risk = true;
                        unpaidList.add(lp);
                    }
                    else {
                        System.out.println(lp);
                        capitalSum = capitalSum + lp.getCapital();
                        interestSum = interestSum + lp.getInterest();
                    }
                }
                System.out.println("Capital paid:" + capitalSum);
                System.out.println("Interest paid:" + interestSum);
                System.out.println("Capital remaining:" + (loan.getCapital() - capitalSum));
                System.out.println("Interest remaining:" + (loan.getTotalInterest() - interestSum));
                if(risk)
                {
                    System.out.println("Loan is in risk, unpaid requests:");
                    for (loanPayment lp : unpaidList)
                    {
                        System.out.println(lp);
                    }
                }
                break;

            }
            case 3: //FINISHED
            {
                loan.getInvolvedCustomers().forEach((c, amount) -> System.out.println(c.getName() + " paid " + amount));
                System.out.println("Date of activation:" + loan.getDateOfActivation());
                System.out.println("Date finished:" + loan.getDateFinished());
                for(loanPayment lp : loan.getPayList())
                {
                    System.out.println(lp);
                }
                break;
            }
            default:
            {
                break;
            }
        }
    }

    public static void customerDetails(List<Customer> customerList){
        int i = 1;
        int j = 1;
        for(Customer cus : customerList){
            System.out.println("Customer #" + i);
            System.out.println("Name: " + cus.getName());
            System.out.println("Balance: " + cus.getBalance());




            if (cus.getTransactionList().isEmpty())
                System.out.println(cus.getName() + " has no transactions.");
            else {
                for (Transaction transaction : cus.getTransactionList()) {
                    System.out.println("Transaction #" + j);
                    System.out.println(transaction);
                    j++;
                }
            }
            if (cus.getGiven().isEmpty())
                System.out.println("No loans given out.");
            else
            {
                System.out.println("Loans given out:");
                for (int k : cus.getGiven())
                {
                    loanDetails(initializer.loanList.get(k));
                }
            }
            if (cus.getTaken().isEmpty())
                System.out.println("No loans taken.");
            else
            {
                System.out.println("Loans taken:");
                for (int k : cus.getTaken())
                {
                    loanDetails(initializer.loanList.get(k));
                }
            }
            i++;
            j=1;
        }

    }

    public static void listCustomers(List<Customer> customerList){
        int i = 1;
        for (Customer cus : customerList)
        {
            System.out.println("Customer #" + i);
            System.out.println(cus.getName());
           i++;
        }
        System.out.println("Total customers:" + (i-1));
    }

    public static void listOfCategories(List<String> catList){
        int i = 1;
        for (String str : catList)
        {
            System.out.println("Category #" + i + ": " + str);
            i++;
        }
    }
}
