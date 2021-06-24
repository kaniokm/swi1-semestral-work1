package cz.osu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("controllers/MainWindow.fxml"));
        primaryStage.setTitle("Database");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest((event) -> {
            System.out.println("Closing Stage"); // sadly works only when closed via "X" button
        });




    }


    public static void main(String[] args) {
        launch(args);
    }
}
