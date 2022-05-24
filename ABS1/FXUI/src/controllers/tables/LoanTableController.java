package controllers.tables;

import classes.Loan;
import javafx.beans.value.ObservableSetValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.initializer;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanTableController implements Initializable {
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

    ObservableList<Loan> list = FXCollections.observableList(initializer.loanList);

    public void initialize(URL url, ResourceBundle Resources) {


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
    public void runRefresh(){
        table.refresh();
    }

}
