package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Label mikeLabel = new Label("Welcome to our trivia game!");
        Label phoebeLabel = new Label(":)");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.BASELINE_CENTER);
        layout.getChildren().addAll(mikeLabel);
        layout.getChildren().addAll(phoebeLabel);

        primaryStage.setTitle("South Carolina State Museum");
        primaryStage.setScene(new Scene(layout, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
