package mainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import classes.Customer;
import manager.*;
import resources.SchemaJAXB;

import static java.lang.Math.min;
import static java.lang.System.exit;

public class menuManager {

    private static boolean isInit = false;

    public static void runMenu() {

        String prevFileName = " ";
        int choice;
        while (!isInit) {
            print.menu();
            Scanner scanf = new Scanner(System.in);
            choice = scanf.nextInt();
            scanf.nextLine();

            while (choice != 1) {
                System.out.println("Please load an XML file.");
                choice = scanf.nextInt();
            }
            System.out.println("Please enter the path to your XML file.");
            String fileName = scanf.next();
            fileName = fileName + scanf.nextLine();
            while (!XMLerrorCheck.pathCheck(fileName)) {
                System.out.println("Incorrect file extension. XML file required.");
                fileName = scanf.next();
                fileName = fileName + scanf.nextLine();
            }
            prevFileName = fileName;
            SchemaJAXB.getDescList(fileName);

            initializer init = new initializer(SchemaJAXB.descList);
            XMLerrorCheck checker = new XMLerrorCheck(initializer.loanList, initializer.customerList, initializer.categories.getCategories());
            if(!checker.check())
            {
                System.out.println("Error. Enter a suitable XML.");
            }
            else
                isInit = true;
        }


        while (true) {

            print.menu();
            Scanner scanf = new Scanner(System.in);
            choice = scanf.nextInt();

            while (choice < 1 || choice > 8) {
                System.out.println("Error.");
                choice = scanf.nextInt();
            }

            switch (choice) {
                case 1: {
                    System.out.println("Please enter the path to your XML file.");

                    String fileName = scanf.next();
                    fileName = fileName + scanf.nextLine();
                    if (fileName.equals(prevFileName))
                    {
                        System.out.println("File already loaded.");
                        break;
                    }
                    else
                        prevFileName = fileName;

                    while (!XMLerrorCheck.pathCheck(fileName)) {
                        System.out.println("Incorrect file extension. XML file required.");
                        fileName = scanf.next();
                        fileName = fileName + scanf.nextLine();

                    }
                    SchemaJAXB.getDescList(fileName);
                    initializer init = new initializer(SchemaJAXB.descList);
                    XMLerrorCheck checker = new XMLerrorCheck(initializer.loanList, initializer.customerList, initializer.categories.getCategories());
                    if(!checker.check())
                    {
                        System.out.println("Error, reverting back to previous XML file.");
                    }
                    break;
                }
                case 2: {
                    print.listOfLoans(initializer.loanList);
                    break;
                }
                case 3: {
                    print.customerDetails(initializer.customerList);
                    break;
                }
                case 4: {
                    int addAmount;
                    choice = chooseCustomer(initializer.customerList);
                    System.out.println("Enter an amount of money to add:");
                    addAmount = scanf.nextInt();

                    while (addAmount < 0) {
                        System.out.println("Cannot add negative money. Enter amount again.");
                        addAmount = scanf.nextInt();
                    }

                    initializer.customerList.get(choice).addMoney(addAmount);
                }

                break;
                case 5: {
                    int drawAmount;
                    choice = chooseCustomer(initializer.customerList);

                    System.out.println("Enter an amount of money to draw:");
                    drawAmount = scanf.nextInt();
                    while (drawAmount > initializer.customerList.get(choice).getBalance() || drawAmount < 0) {
                        if (drawAmount > initializer.customerList.get(choice).getBalance()) {
                            System.out.println("Cannot draw more than the customer's balance. Enter amount again.");
                        } else if (drawAmount < 0) {
                            System.out.println("Cannot draw negative money. Enter amount again.");
                        }
                        drawAmount = scanf.nextInt();
                    }
                    initializer.customerList.get(choice).drawMoney(drawAmount);
                    break;

                }
                case 6: {
                    loanFinderInput();
                    break;

                }
                case 7: {
                    timeline.nextMonth();
                    break;

                }
                case 8: {
                    System.out.println("Shutting down.");
                    exit(0);
                }
            }
        }
    }

    public static void loanFinderInput() {
        Scanner scan = new Scanner(System.in);
        int choice = chooseCustomer(initializer.customerList);
        Customer cus = initializer.customerList.get(choice);
        int amount;
        double minInterestPerPayment = -1;
        if (initializer.customerList.get(choice).getBalance() == 0)
        {
            System.out.println(initializer.customerList.get(choice).getName() + " is broke! Returning...");
            return;
        }
        System.out.println("Enter an investment amount:");
        amount = scan.nextInt();
        while (amount < 0 || amount > initializer.customerList.get(choice).getBalance()) {
            System.out.println("Invalid choice, enter amount again.");
            amount = scan.nextInt();
        }
        if (amount == 0)
        {
            System.out.println("Zero investment. Returning to menu...");
            return;
        }

        List<String> chosenCategories = chooseCategories(initializer.categories.getCategories());

        int minInterChoise;
        System.out.println("Do you want to set a min interest amount ? yes = enter 1 , no = enter 0 ");
        minInterChoise = scan.nextInt();
        while (minInterChoise != 1 && minInterChoise != 0) {
            System.out.println("Invalid choice! enter your choice again. ");
            minInterChoise = scan.nextInt();
        }
        if (minInterChoise == 1) {
            System.out.println("Enter minimum interest amount  ");
            minInterestPerPayment = scan.nextDouble();
            while (minInterestPerPayment < 0) {
                System.out.println("Amount needs to be positive. Enter amount again:");
                minInterestPerPayment = scan.nextDouble();
            }
        }

        int minTotalMonth = -1;
        int minMonthChoise;
        System.out.println("Do you want to set a min total months? yes = enter 1 , no = enter 0 ");
        minMonthChoise = scan.nextInt();
        while (minMonthChoise != 1 && minMonthChoise != 0) {
            System.out.println("Invalid choice! enter your choice again. ");
            minMonthChoise = scan.nextInt();
        }
        if (minMonthChoise == 1) {
            System.out.println("Enter minimum total months  ");
            minTotalMonth = scan.nextInt();
            while (minTotalMonth < 0) {
                System.out.println("Months number needs to be positive. Enter amount again:");
                minTotalMonth = scan.nextInt();
            }
        }
        List<Integer> loanIndexes = loanFinder.findLoans(amount, chosenCategories, minInterestPerPayment, minTotalMonth, cus.getName());
        if (loanIndexes.isEmpty())
            System.out.println("No suitable loans were found.");
        else
        {
            int lowestInvestment = -1;
            int choicesCount = 0;
            boolean flag = true;
            List<Integer> investmentIndexes = new ArrayList<>();
            boolean choiceBools[] = new boolean[loanIndexes.size()];
            Scanner scanrr = new Scanner(System.in);
            int chosen = -1;

            while(true)
            {
                if (chosen == (loanIndexes.size() + 1) | choicesCount == loanIndexes.size())
                {
                    System.out.println("Done choosing loans");
                    break;
                }
                System.out.println("Choose loans:");
                int j = 1;
                for (int i : loanIndexes) {
                    System.out.println("Loan #" + j);
                    print.loanDetails(initializer.loanList.get(i));
                    j++;
                }
                System.out.println("Press " + (loanIndexes.size() + 1) + " to exit.");
                chosen = scanrr.nextInt();

                if (chosen > 0 & chosen <= loanIndexes.size()){
                    if (choiceBools[chosen - 1] == false)
                    {
                        if (lowestInvestment == -1)
                            lowestInvestment = initializer.loanList.get(loanIndexes.get(chosen - 1)).getMoneyNeeded();
                        if (lowestInvestment > initializer.loanList.get(loanIndexes.get(chosen - 1)).getMoneyNeeded())
                            lowestInvestment = initializer.loanList.get(loanIndexes.get(chosen - 1)).getMoneyNeeded();
                        investmentIndexes.add(loanIndexes.get((chosen - 1)));
                        choicesCount++;
                        choiceBools[chosen - 1] = true;
                    }
                }
                else if (!(chosen == (loanIndexes.size() + 1) | choicesCount == loanIndexes.size()))
                    System.out.println("Invalid choice.");
            }
            if (!investmentIndexes.isEmpty()) {
                int money = min(lowestInvestment, amount / investmentIndexes.size());
                for (int i : investmentIndexes) {
                    initializer.loanList.get(i).addCustomer(cus, money);
                }
            }
            else
                System.out.println("No available loans. Returning...");
        }

    }



    public static int chooseCustomer(List<Customer> customerList) {
        print.listCustomers(customerList);
        System.out.println("Choose a customer number.");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        while (choice < 1 & choice > customerList.size())
        {
            System.out.println("Invalid choice.");
            choice = scan.nextInt();
        }
        System.out.println("Customer chosen:" + customerList.get(choice-1).getName());
        return (choice-1);
    }


    public static List<String> chooseCategories(List<String> categories)
    {
        int choicesCount = 0;
        List<String> res = new ArrayList<>();
        boolean choicesList[] = new boolean[categories.size()];
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (true) {
            print.listOfCategories(categories);
            System.out.println("Press " + (categories.size() + 1) + " to continue.");
            choice = scan.nextInt();
            if (choice == (categories.size() + 1) || choicesCount == categories.size())
                return res;
            else if (choice > 0 & choice < (categories.size() + 1)) {
                if (choicesList[choice - 1] == false) {
                    choicesList[choice - 1] = true;
                    res.add(categories.get(choice - 1));
                    choicesCount++;
                    if (choicesCount == categories.size())
                    {
                        return res;
                    }
                }
                else
                    System.out.println("Category already chosen.");
            }
            else
                System.out.println("Invalid choice. Choose again");

        }

    }
}

