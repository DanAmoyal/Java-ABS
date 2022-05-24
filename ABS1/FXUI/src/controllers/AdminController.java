package controllers;

import classes.Loan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

import classes.Customer;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.*;
import resources.SchemaJAXB;

import java.io.File;
import java.net.URL;

public class AdminController {

    @FXML
    private HBox AdminBottomHBox;

    @FXML
    private TableView<Loan> AdminLoanTable;

    @FXML
    private CustomerController customerController;

    @FXML
    private TableView<Customer> AdminCustomerTable;
    @FXML
    private BorderPane mainBorderPane;

    private Node loanTableView;

    private Node customerTableView;

    @FXML
    private Label CurrentYazLabel;

    @FXML
    private Label FilePathLabel;

    @FXML
    private ComboBox<String> customersCB;

    @FXML
    void LoadFileButtonListener(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("XML files", "*.xml");
        fileChooser.getExtensionFilters().add(fileExtension);
        fileChooser.setTitle("Choose an XML file");
        File xmlFile = fileChooser.showOpenDialog(new Stage());
        if (xmlFile != null) {
           startUp(xmlFile);

        }
        else
            FilePathLabel.setText("null XML file");
    }

    @FXML
    private Button YazButton;

    @FXML
    private Button LoadFileButton;

    @FXML
    void YazButtonActionListener(ActionEvent event) {
        if (CurrentYazLabel.getText() != "Load new XML")
        {
            timeline.nextMonth();
            CurrentYazLabel.setText("Current Yaz: " + timeline.getCurrentDate());
        }
        else
        {
            FilePathLabel.setText("You cannot advance time!");
            CurrentYazLabel.setText("Load new XML");
        }
    }

    @FXML
    void CustomersCBActionListener(ActionEvent event) throws Exception {

        String choice = customersCB.getValue();
        if(!(choice == "Admin")){
            customerController = new CustomerController();
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/controllers/CustomerController.fxml");
            loader.setLocation(url);
            BorderPane customerBP = loader.load(url.openStream());
            customerController = loader.getController();
            setAdminForChildControllers();
            customerController.initialize(choice);

            customerTableView = mainBorderPane.getCenter();
            loanTableView = mainBorderPane.getBottom();

            displayCustomer(customerBP);

        }
        else{
            displayAdmin();
            loanInfo(loanInfoCB.getValue());
        }

    }

    void displayCustomer(BorderPane bp)
    {
        mainBorderPane.setCenter(bp);
        mainBorderPane.setBottom(new Pane());
        mainBorderPane.setLeft(new Pane());
        mainBorderPane.setRight(new Pane());
    }

    void displayAdmin(){
        mainBorderPane.setCenter(AdminLoanTable);
        mainBorderPane.setBottom(AdminBottomHBox);
        mainBorderPane.setLeft(YazButton);
        mainBorderPane.setRight(LoadFileButton);
    }

    void initializeComboBox() {
        customersCB.getItems().clear();
        customersCB.getItems().add("Admin");

        loanInfoCB.getItems().clear();

        for(Customer c : initializer.customerList)
        {
            customersCB.getItems().add(c.getName());
            loanInfoCB.getItems().add(c.getName());
        }
    }

    void startUp(File xmlFile){
        setAdminForChildControllers();
        SchemaJAXB.getDescList(xmlFile.getAbsolutePath());
        initializer init = new initializer(SchemaJAXB.descList);
        XMLerrorCheck checker = new XMLerrorCheck(initializer.loanList, initializer.customerList, initializer.categories.getCategories());
        String checkStr = checker.check();
        if(!(checkStr=="True")) {
            FilePathLabel.setText("Invalid: " + checkStr);
            CurrentYazLabel.setText("Load new XML");
        }
        else{
            setAdminForChildControllers();
            timeline.resetCurrentDate();
            CurrentYazLabel.setText("Current Yaz: " + timeline.getCurrentDate());
            FilePathLabel.setText(xmlFile.getAbsolutePath());
            initializeComboBox();
            AdminLoanTable.refresh();
            AdminCustomerTable.refresh();
        }
    }

    void setAdminForChildControllers(){
        if(customerController != null){
            customerController.setAdminController(this);
        }
    }

/////////////////////////////////////////////////////// LOAN INFO ///////////////////////////////////

    @FXML
    private ComboBox<String> loanInfoCB;

    @FXML
    private Label newLoansLabel;

    @FXML
    private Label givenPendingLabel;

    @FXML
    private Label takenPendingLabel;

    @FXML
    private Label givenActiveLabel;

    @FXML
    private Label takenActiveLabel;

    @FXML
    private Label takenRiskLabel;

    @FXML
    private Label givenRiskLabel;

    @FXML
    private Label givenFinishedLabel;

    @FXML
    private Label takenFinishedLabel;

    @FXML
    void loanInfoCBActionListener(ActionEvent event) {
        String choice = loanInfoCB.getValue();
        loanInfo(choice);
    }



    void loanInfo(String name) {
        Customer customer = null;
        int newLoansCount = 0;
        int givenPendingCount = 0;
        int takenPendingCount = 0;
        int givenActiveCount = 0;
        int takenActiveCount = 0;
        int givenRiskCount = 0;
        int takenRiskCount = 0;
        int givenFinishedCount = 0;
        int takenFinishedCount = 0;

        for (Customer c : initializer.customerList) {
            if (c.getName().equals(name)) {
                customer = c;
                break;
            }
        }
        if (customer == null)
            System.out.println("ERROR NO CUSTOMER");
        for (Loan loan : initializer.loanList) {
            if (loan.getOwnerName().equals(name)) {
                switch (loan.getStatus()) {
                    case NEWLOAN:
                        newLoansCount++;
                        break;
                    case PENDING:
                        takenPendingCount++;
                        break;
                    case ACTIVE:
                        takenActiveCount++;
                        break;
                    case RISK:
                        takenRiskCount++;
                        break;
                    case FINISHED:
                        takenFinishedCount++;
                        break;
                }
            } else if (loan.getInvolvedCustomers().containsKey(customer)) {
                switch (loan.getStatus()) {
                    case PENDING:
                        givenPendingCount++;
                        break;
                    case ACTIVE:
                        givenActiveCount++;
                        break;
                    case RISK:
                        givenRiskCount++;
                        break;
                    case FINISHED:
                        givenFinishedCount++;
                        break;
                }
            }

        }
       newLoansLabel.setText(Integer.toString(newLoansCount));

        givenPendingLabel.setText(Integer.toString(givenPendingCount));
        takenPendingLabel.setText(Integer.toString(takenPendingCount));

        givenActiveLabel.setText(Integer.toString(givenActiveCount));
        takenActiveLabel.setText(Integer.toString(takenActiveCount));

        givenRiskLabel.setText(Integer.toString(givenRiskCount));
        takenRiskLabel.setText(Integer.toString(takenRiskCount));

        givenFinishedLabel.setText(Integer.toString(givenFinishedCount));
        takenFinishedLabel.setText(Integer.toString(takenFinishedCount));
    }
}
