package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button addPatientButton;
    @FXML
    private Button searchButton;

    /**
     * Opens a new window to add patient info.
     * @param mouseClick User clicks the "Add patient" button.
     */
    @FXML
    private void openAddPatientWindow(ActionEvent mouseClick){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/newPatient.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Patient Info");
            stage.setScene(new Scene(root, 640, 400));
            stage.show();
        }
        catch (IOException exception){ exception.printStackTrace(); }
    }

    @FXML
    private void openSearchWindow(ActionEvent mouseClick){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Search.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Search");
            stage.setScene(new Scene(root, 525, 350));
            stage.show();
        }
        catch (IOException exception) {exception.printStackTrace();}
    }
}