package manager;


import classes.*;
import resources.generated.generated.AbsCustomer;
import resources.generated.generated.AbsDescriptor;
import resources.generated.generated.AbsLoan;

import java.util.ArrayList;
import java.util.List;

public class initializer {

    static public List<Customer> customerList = new ArrayList<Customer>();
    static public List<Loan> loanList = new ArrayList<Loan>();
    static public Categories categories;
    static public List<Loan> activeLoans = new ArrayList<>();

    public initializer(AbsDescriptor descriptor)
    {
        List<AbsCustomer> AbsCustomerList = descriptor.getAbsCustomers().getAbsCustomer();
        List<AbsLoan> AbsLoanList = descriptor.getAbsLoans().getAbsLoan();
        List<String> categoryList = descriptor.getAbsCategories().getAbsCategory();
        categories = new Categories(categoryList);

        if(!customerList.isEmpty())
            customerList.clear();
        for (AbsCustomer c : AbsCustomerList)
        {
           Customer tempCustomer = new Customer(c);
           customerList.add(tempCustomer);
        }

        if(!loanList.isEmpty())
            loanList.clear();
        for (AbsLoan l : AbsLoanList)
        {
            Loan tempLoan = new Loan(l);
            loanList.add(tempLoan);
        }
    }

    public static Customer nameToCustomer(String name){
        for (Customer c : initializer.customerList)
        {
            if (name.equals(c.getName()))
            {
                return c;
            }
        }
        System.out.println("ERROR NO CUSTOMER");
        return null;
    }
}
