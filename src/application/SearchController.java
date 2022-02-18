package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class SearchController {

    @FXML
    private Label nameLbl;
    @FXML
    private TextField nameField;
    @FXML
    private Button find;
    @FXML
    private Button exit;

    @FXML
    private void findButton(ActionEvent mouseClick) throws IOException {
        User target = Main.storeData.searchByName(nameField.getText());
        if(target == null){
            target = new User();
            target.setName("User not found");
            target.setReqs(-1, 0);
        }
        Main.searchResult = target;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Results.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Results");
        stage.setScene(new Scene(root, 640, 400));
        stage.show();
    }

    @FXML
    private void exitButton (ActionEvent mouseClick) throws IOException {
        Node source = (Node) mouseClick.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}