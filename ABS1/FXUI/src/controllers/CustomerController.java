package controllers;

import classes.Customer;
import classes.Loan;
import classes.operations.Transaction;
import controllers.tables.LoanerLoansTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import manager.initializer;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static manager.loanFinder.addLoans;
import static manager.loanFinder.findLoans;

public class CustomerController {
    private Customer theCustomer = null;

    @FXML
    private BorderPane customerBP;

    @FXML
    private LoanerLoansTableController loanerLoansTableController;

    @FXML
    private AdminController adminController;

    @FXML
    private TableView<Loan> infoTabGivenLoanTable;

    @FXML
    private TableColumn<?, ?> balance;

    @FXML
    private Button chargeButton;

    @FXML
    private Button withdrawButton;

    ////////////////////////////////////////////////CATEGORIES COMBO BOX ////////////////////////////
    @FXML
    private ComboBox<String> categoriesCB;

    @FXML
    private ComboBox<String> chosenCategoriesCB;

    @FXML
    void categoriesCBActionListener(ActionEvent event) throws Exception {
        String choice = categoriesCB.getValue();
        chosenCategoriesCB.getItems().add(choice);
    }


    @FXML
    void chosenCategoriesCBActionListener(ActionEvent event) throws Exception {
        String choice = chosenCategoriesCB.getValue();
        chosenCategoriesCB.getItems().remove(choice);
    }

    void intializeCategoriesComboBox()
    {
        categoriesCB.getItems().clear();
        for(String cat : initializer.categories.getCategories())
            categoriesCB.getItems().add(cat);

    }
    ////////////////////////////////////////////////CATEGORIES COMBO BOX ////////////////////////////
///////////information//////////////
//    @FXML
//    void infoTabActionListener(ActionEvent event) {
//        table1.refresh();
//    }

    @FXML
    private TableView<Loan> table;

    @FXML
    private TableColumn<Loan, String> owner;

    @FXML
    private TableColumn<Loan, Integer> capital;

    @FXML
    private TableColumn<Loan, Double> capitalRemaining;

    @FXML
    private TableColumn<Loan, Double> capitalPaid;

    @FXML
    private TableColumn<Loan, Integer> monthsPerPayment;

    @FXML
    private TableColumn<Loan, Integer> nextPaymentOn;

    @FXML
    private TableColumn<Loan, Integer> dateFinished;

    @FXML
    private TableColumn<Loan, Double> collectedCapital;

    @FXML
    private TableColumn<Loan, Double> interestPaid;

    @FXML
    private TableColumn<Loan, Double> interestRate;

    @FXML
    private TableColumn<Loan, Integer> remainingCapital;

    @FXML
    private TableColumn<Loan, Integer> totalMonths;

    @FXML
    private TableColumn<Loan, String> id;

    @FXML
    private TableColumn<Loan, String> category;

    @FXML
    private TableColumn<Loan, Double> interestRemaining;

    @FXML
    private TableColumn<Loan, Integer> activeSince;

    @FXML
    private TableColumn<Loan, Loan.loanStatus> status;
    public void initializeGivenLoansT() {
        ObservableList<Loan> list = FXCollections.observableList(theCustomer.getGiven());

        for (Loan l: list)
            l.updateRemainAndCollectedMoney(); //CALL WHEN MOVING TIME

        id.setCellValueFactory(new PropertyValueFactory<Loan, String>("id"));
        owner.setCellValueFactory(new PropertyValueFactory<Loan, String>("ownerName"));
        category.setCellValueFactory(new PropertyValueFactory<Loan, String>("category"));
        capital.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("capital"));
        totalMonths.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("totalMonths"));
        interestRate.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestRate"));
        monthsPerPayment.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("monthsPerPayment"));

        status.setCellValueFactory(new PropertyValueFactory<Loan, Loan.loanStatus>("status"));
        interestPaid.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestPaid"));
        capitalPaid.setCellValueFactory(new PropertyValueFactory<Loan, Double>("capitalPaid"));
        collectedCapital.setCellValueFactory(new PropertyValueFactory<Loan, Double >("moneyInvestedSoFar"));
        remainingCapital.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("moneyNeeded"));
        activeSince.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("dateOfActivation"));
        dateFinished.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("dateFinished"));
        nextPaymentOn.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("nextPaymentOn"));
        interestRemaining.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestRemaining"));
        capitalRemaining.setCellValueFactory(new PropertyValueFactory<Loan, Double>("capitalRemaining"));

        table.setItems(list);
    }
    @FXML
    private TableView<Loan> table1;

    @FXML
    private TableColumn<Loan, String> owner1;

    @FXML
    private TableColumn<Loan, Integer> capital1;

    @FXML
    private TableColumn<Loan, Double> capitalRemaining1;

    @FXML
    private TableColumn<Loan, Double> capitalPaid1;

    @FXML
    private TableColumn<Loan, Integer> monthsPerPayment1;

    @FXML
    private TableColumn<Loan, Integer> nextPaymentOn1;

    @FXML
    private TableColumn<Loan, Integer> dateFinished1;

    @FXML
    private TableColumn<Loan, Double> collectedCapital1;

    @FXML
    private TableColumn<Loan, Double> interestPaid1;

    @FXML
    private TableColumn<Loan, Double> interestRate1;

    @FXML
    private TableColumn<Loan, Integer> remainingCapital1;

    @FXML
    private TableColumn<Loan, Integer> totalMonths1;

    @FXML
    private TableColumn<Loan, String> id1;

    @FXML
    private TableColumn<Loan, String> category1;

    @FXML
    private TableColumn<Loan, Double> interestRemaining1;

    @FXML
    private TableColumn<Loan, Integer> activeSince1;

    @FXML
    private TableColumn<Loan, Loan.loanStatus> status1;
    public void initializeTakenLoansT() {
        ObservableList<Loan> list = FXCollections.observableList(theCustomer.getTaken());

        for (Loan l: list)
            l.updateRemainAndCollectedMoney(); //CALL WHEN MOVING TIME

        id1.setCellValueFactory(new PropertyValueFactory<Loan, String>("id"));
        owner1.setCellValueFactory(new PropertyValueFactory<Loan, String>("ownerName"));
        category1.setCellValueFactory(new PropertyValueFactory<Loan, String>("category"));
        capital1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("capital"));
        totalMonths1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("totalMonths"));
        interestRate1.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestRate"));
        monthsPerPayment1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("monthsPerPayment"));

        status1.setCellValueFactory(new PropertyValueFactory<Loan, Loan.loanStatus>("status"));
        interestPaid1.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestPaid"));
        capitalPaid1.setCellValueFactory(new PropertyValueFactory<Loan, Double>("capitalPaid"));
        collectedCapital1.setCellValueFactory(new PropertyValueFactory<Loan, Double >("moneyInvestedSoFar"));
        remainingCapital1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("moneyNeeded"));
        activeSince1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("dateOfActivation"));
        dateFinished1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("dateFinished"));
        nextPaymentOn1.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("nextPaymentOn"));
        interestRemaining1.setCellValueFactory(new PropertyValueFactory<Loan, Double>("interestRemaining"));
        capitalRemaining1.setCellValueFactory(new PropertyValueFactory<Loan, Double>("capitalRemaining"));

        table1.setItems(list);
    }


    ////////////////////////////////////////////////LOANS COMBO BOX ////////////////////////////
    @FXML
    private ComboBox<String> loansCB;

    private List<Loan> loansCBList;

    @FXML
    private ComboBox<String> chosenLoansCB;

    @FXML
    void loansCBActionListener(ActionEvent event) throws Exception {
        String choice = loansCB.getValue();
        chosenLoansCB.getItems().add(choice);
    }

    @FXML
    void chosenLoansCBActionListener(ActionEvent event) throws Exception {
        String choice = chosenCategoriesCB.getValue();
        chosenCategoriesCB.getItems().remove(choice);
    }

    void initializeLoansComboBox(List<Loan> loans)
    {
        loansCBList = loans;
        loansCB.getItems().clear();
        for(Loan l : loans)
            loansCB.getItems().add(l.getId());

    }

    @FXML
    void scrambleButtonActionListener(ActionEvent event) {
        List<Loan> choiceList = new ArrayList<>();
        for (String choice : chosenLoansCB.getItems())
        {
            for (Loan l : loansCBList)
            {
                if(choice == l.getId())
                {
                    choiceList.add(l);
                }
            }
        }
        addLoans(choiceList, theCustomer, Integer.parseInt(investmentAmountTF.getText()));
        loansCBList = null;
        table.refresh();
    }

    ////////////////////////////////////////////////LOANS COMBO BOX ////////////////////////////





    ////////////////////////////////////////////// TRANSACTION TABLE ////////////////////////////////////////////////

    @FXML
    private TableColumn<Transaction, Double> before;

    @FXML
    private TableColumn<Transaction, Double> after;

    @FXML
    private TableColumn<Transaction, Integer> date;

    @FXML
    private TableColumn<Transaction, Double> amount;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TextField trancTextField;

    ////////////////////////////////////////////// TRANSACTION TABLE ////////////////////////////////////////////////




    public void setAdminController(AdminController admin){
        this.adminController = admin;
    }
    public AdminController getAdminController(){
        return this.adminController;
    }


    public void initialize(String customerName){
        for (Customer c : initializer.customerList)
        {
            if (c.getName() == customerName){
                theCustomer = c;
                break;
            }

        }
        if (theCustomer == null)
            System.out.println("NO CUSTOMER");

        initTransactionTable();
        trancTextField.setText(theCustomer.getName());
        intializeCategoriesComboBox();
        initializeTakenLoansT();
        initializeGivenLoansT();
        initializeTakenLoansPaymentTAB();
        initializeNotifications();
        ///////////// MORE INIT
    }

    private void initTransactionTable()
    {
        ObservableList<Transaction> tranList = FXCollections.observableList(theCustomer.getTransactionList());
        date.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("date"));
        amount.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        before.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("balanceBefore"));
        after.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("balanceAfter"));
        transactionTable.setItems(tranList);
    }
    private void initScramble(){

    }

    @FXML
    void chargeButtonActionListener(ActionEvent event) {
        int chargeAmount;
        String text = trancTextField.getText();
        if(text.matches("[0-9]+"))
        {
            chargeAmount =  Integer.parseInt(text);
            theCustomer.addMoney(chargeAmount);
        }
        else
            trancTextField.setText("Numbers only.");
        transactionTable.refresh();
    }

    @FXML
    void withdrawButtonActionListener(ActionEvent event) {
        int drawAmount;
        String text = trancTextField.getText();
        if(text.matches("[0-9]+"))
        {
            drawAmount =  Integer.parseInt(text);
            if (drawAmount > theCustomer.getBalance())
                    trancTextField.setText("Cannot draw more than the customer's balance. Enter amount again.");
            theCustomer.drawMoney(drawAmount);
        }
        else
            trancTextField.setText("Numbers only.");
        transactionTable.refresh();

    }

    public BorderPane getCustomerBP() {
        return customerBP;
    }

    // SCRAMBLE

    @FXML
    private TextField minInterestTF;

    @FXML
    private TextField maxShareTF;

    @FXML
    private TextField minYazTF;

    @FXML
    private Button findLoansButton;

    @FXML
    private TextField investmentAmountTF;

    @FXML
    private TextField maxInvolvedLoansTF;

    @FXML
    private LoanerLoansTableController LLtableController;


    @FXML
    void findLoansButtonListener(ActionEvent event) {
        int minInterest = -1;
        int maxShare = -1;
        int minYaz =-1;
        int investmentAmount = 0;
        int maxInvolvedLoans = -1;
        List<Loan> goodLoans;
        loansCBList = null;

        List<String> categories = chosenCategoriesCB.getItems();

        String minInterestText = minInterestTF.getText();
        if(minInterestText.matches("[0-9]+"))
            minInterest =  Integer.parseInt(minInterestText);


        String maxShareText = maxShareTF.getText();
        if(maxShareText.matches("[0-9]+"))
            maxShare =  Integer.parseInt(maxShareText);

        String minYazText = minYazTF.getText();
        if(minYazText.matches("[0-9]+"))
            minYaz =  Integer.parseInt(minYazText);


        String investmentAmountText = investmentAmountTF.getText();
        if(investmentAmountText.matches("[0-9]+"))
            investmentAmount =  Integer.parseInt(investmentAmountText);

        String maxInvolvedLoansText = maxInvolvedLoansTF.getText();
        if(maxInvolvedLoansText.matches("[0-9]+"))
            maxInvolvedLoans =  Integer.parseInt(maxInvolvedLoansText);

        if(investmentAmount == 0)
            investmentAmountTF.setText("Invest something.");
        else {
            goodLoans = findLoans(investmentAmount, categories, minInterest, minYaz, theCustomer, maxInvolvedLoans, maxShare);/////////////////////////////
            initializeLoansComboBox(goodLoans);
        }

        ///fixxxx
    }


    //PAYMENT TAB
    @FXML
    private TableView<Loan> table11;

    @FXML
    private TableColumn<Loan, Integer> capital11;

    @FXML
    private TableColumn<Loan, Double> capitalRemaining11;

    @FXML
    private TableColumn<Loan, Double> capitalPaid11;

    @FXML
    private TableColumn<Loan, Integer> nextPaymentOn11;

    @FXML
    private TableColumn<Loan, Double> collectedCapital11;

    @FXML
    private TableColumn<Loan, Double> interestPaid11;


    @FXML
    private TableColumn<Loan, Integer> remainingCapital11;

    @FXML
    private TableColumn<Loan, Integer> totalMonths11;

    @FXML
    private TableColumn<Loan, String> id11;

    @FXML
    private TableColumn<Loan, String> category11;



    @FXML
    private TableColumn<Loan, Loan.loanStatus> status11;
    public void initializeTakenLoansPaymentTAB() {
        ObservableList<Loan> list = FXCollections.observableList(theCustomer.getTaken());

        for (Loan l: list)
            l.updateRemainAndCollectedMoney(); //CALL WHEN MOVING TIME

        id11.setCellValueFactory(new PropertyValueFactory<Loan, String>("id"));
        capital11.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("capital"));
        totalMonths11.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("totalMonths"));
        status11.setCellValueFactory(new PropertyValueFactory<Loan, Loan.loanStatus>("status"));
        nextPaymentOn11.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("nextPaymentOn"));
        table11.setItems(list);

    }
    @FXML
    private TableView paymentTV;

    @FXML
    void payButtonActionListener(ActionEvent event) {
        if(theCustomer.getTaken().isEmpty())
            return;
        else
        {
            Loan loan = table11.getSelectionModel().getSelectedItem();
            if(loan == null)
                return;
            else
                loan.payout();
        }

    }

    @FXML
    void closeLoanButtonActionListener(ActionEvent event) {
        if(theCustomer.getTaken().isEmpty())
            return;
        else
        {
            Loan loan = table11.getSelectionModel().getSelectedItem();
            if(loan == null)
                return;
            if(theCustomer.getBalance() >= loan.getDebt())
            {
                while(loan.getStatus() != Loan.loanStatus.FINISHED)
                    loan.payout();
            }
        }


    }

    public Customer getTheCustomer() {
        return theCustomer;
    }

    public void setLLtableController(LoanerLoansTableController LLtableController) {
        this.LLtableController = LLtableController;
    }
    @FXML
    private ListView<String> notificationListView;

    private void initializeNotifications(){
        int size = theCustomer.getNotifications().size();
        for (int i = size-1; i >= 0; i--)
        {
            notificationListView.getItems().add(theCustomer.getNotifications().get(i));
        }
    }

}
