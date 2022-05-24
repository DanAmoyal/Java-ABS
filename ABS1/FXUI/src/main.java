import classes.Customer;
import controllers.CustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.initializer;


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Alternative Banking System Interface");

        Parent load = FXMLLoader.load(getClass().getResource("controllers/AdminController.fxml"));
        Scene scene = new Scene(load, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[]args) {
        launch(args);
    }

}
