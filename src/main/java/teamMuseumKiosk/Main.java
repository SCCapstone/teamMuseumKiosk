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
        //This creates a directory on the C drive where resources can go
        //this might break mac's

        /*File tempFile = new File("C://MuseumTriviaGame/");
        boolean exists = tempFile.exists();
        if (!exists)
        {
            File dir = new File("C://MuseumTriviaGame/");
            dir.mkdir();
            File img = new File("C://MuseumTriviaGame/Images/");
            img.mkdir();
            File ads = new File("C://MuseumTriviaGame/Images/Advertisements/");
            ads.mkdir();
            File vid = new File("C://MuseumTriviaGame/Videos/");
            vid.mkdir();
        }*/

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
