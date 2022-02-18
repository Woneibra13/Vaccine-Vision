package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static RankingSystem storeData = new RankingSystem();
    public static User searchResult;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Open the application at the landing page
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        primaryStage.setTitle("Vaccine Vision");
        primaryStage.setScene(new Scene(root, 550, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
