package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class NewPatientController {
  
  @FXML
  private Label nameLbl;
  @FXML
  private TextField nameField;
  @FXML
  private Label ageLbl;
  @FXML
  private TextField ageField;
  @FXML
  private Label regionLbl;
  @FXML
  private TextField regionField;
  @FXML
  private Button finish;
  @FXML
  private Label emailLbl;
  @FXML
  private TextField emailField;
  @FXML
  private Label healthConditionsLbl;
  @FXML
  private CheckBox healthConditionsYes;
  @FXML
  private CheckBox healthConditionsNo;
  @FXML
  private CheckBox traveledYes;
  @FXML
  private CheckBox traveledNo;
  @FXML
  private CheckBox alreadyVaccinatedYes;
  @FXML
  private CheckBox alreadyVaccinatedNo;
  @FXML
  private Button add;
  
  @FXML
  VBox newPatientVBox;
  
  
  RankingSystem rankingSystem = new RankingSystem();
  
  public String toString(User curr) {
      return curr.getName() + " -> " + rankingSystem.rankOf(curr) + "\n";
  }
  
  @FXML
  private void addPatientWindowFinish(ActionEvent mouseClick) throws IOException {
    Node source = (Node) mouseClick.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
    rankingSystem.wipe();
  }
  
  
  @FXML
  public void addPatientAddButton(ActionEvent mouseClick) throws IOException {
    
    if (alreadyVaccinatedYes.isSelected() || traveledYes.isSelected()) {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/notEligible.fxml"));
      Stage stage = new Stage();
      stage.setTitle("Not Eligible");
      stage.setScene(new Scene(root, 550, 325));
      stage.show();
      
      
    } else {
      User user = new User(nameField.getText(), Integer.parseInt(ageField.getText()), regionField.getText(),
          emailField.getText());
      int conditions = healthConditionsYes.isSelected() ? 5 : 0;
      user.setReqs(user.getAge(), conditions);
      
      // TODO add user to DB
      
      // CODE ERROR DISCUSSED IN CLASS
//      Parent root = FXMLLoader.load(getClass().getResource("/fxml/NewResults.fxml"));
      
      StringBuilder sb = new StringBuilder();
      rankingSystem.addUser(user);
      
      HashSet<User> userList = new HashSet<User>(rankingSystem.DBtoUserArrayList());
      HashSet<String> lastList = new HashSet<String>();
      for (User u : userList )
      {
    	  sb.append(toString(u));
      }

      System.out.println(sb.toString());
      
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewResults.fxml"));
      Parent textarea = loader.load();
      Scene scene = new Scene(textarea, 550, 325);
      
      
      
      MyResultController cntrl = (MyResultController) loader.getController();
      cntrl.initialize(sb.toString());
     
//      cntrl.initialize(user.getName() +" "+ Integer.toString(rank));
//      cntrl.initialize("Hello Iba");
//      textarea.setDisable(true);
      
      Stage stage2 = new Stage();
      stage2.setTitle("Vaccination Order");
      stage2.setScene(scene);
      stage2.show();
      
    }
    
  }
}
