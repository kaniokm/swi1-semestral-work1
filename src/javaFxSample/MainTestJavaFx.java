package javaFxSample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTestJavaFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("dbWindow.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("rezervationWindow.fxml"));
        primaryStage.setTitle("Database");
        //primaryStage.setScene(new Scene(root, 600, 275));
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
