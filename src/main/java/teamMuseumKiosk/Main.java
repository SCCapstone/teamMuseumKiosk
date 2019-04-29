package teamMuseumKiosk;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application implements LoadScene{
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

        //creates idle timer
        timerToSplashScene(primaryStage, 5);
        //loads splash scene
        loadSplashScene(primaryStage);
    }


}
