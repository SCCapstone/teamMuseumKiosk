package teamMuseumKiosk;

import controllers.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //This creates the directories where resources can go
        File img = new File("./images/");
        img.mkdir();
        File ads = new File("./Advertisements/");
        ads.mkdir();
        File vid = new File("./video/");
        vid.mkdir();

        URL url = new URL(getClass().getResource("/design/splashScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        SplashController controller = new SplashController();
        loader.setController(controller);

        Scene scene = new Scene(root,1440,900);
        primaryStage.setTitle("South Carolina State Museum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
