package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.String.valueOf;

public class ResultsController {

    @FXML
    Label showText;

    @FXML
    Button resultButton;

    @FXML
    private Button exit;

    @FXML
    private void exitPress(ActionEvent mouseClick) throws IOException {
        Node source = (Node) mouseClick.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void resultClicker(ActionEvent mouseClick) throws IOException{
        showText.setText("Name: " + Main.searchResult.getName() + " Priority: " + Main.searchResult.getReqs());
    }
}