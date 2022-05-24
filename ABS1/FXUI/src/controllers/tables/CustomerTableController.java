package controllers.tables;

import classes.Customer;
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

public class CustomerTableController implements Initializable {

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, Double> balance;

    @FXML
    private TableColumn<Customer, String> name;



    ObservableList<Customer> list = FXCollections.observableList(initializer.customerList);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        balance.setCellValueFactory(new PropertyValueFactory<Customer, Double>("balance"));

        table.setItems(list);
    }


}
