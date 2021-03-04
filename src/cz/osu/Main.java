package cz.osu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import cz.osu.guiJavaFx.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       /* Parent root = FXMLLoader.load(getClass().getResource("CreateReservationWindow.fxml"));
        primaryStage.setTitle("Database");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
*/

        Parent root = FXMLLoader.load(getClass().getResource("guiJavaFx/DbWindow.fxml"));
        primaryStage.setTitle("Database");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
