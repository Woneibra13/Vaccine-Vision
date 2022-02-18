package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class MyResultController {
  
  @FXML
  private TextArea resultText;
  
  @FXML
  private Label resultLabel;
  
  public void initialize(String msg) {
    resultText.setText(msg);
    resultText.setEditable(false);
   // resultLabel.setText(msg);
  }
  
}
