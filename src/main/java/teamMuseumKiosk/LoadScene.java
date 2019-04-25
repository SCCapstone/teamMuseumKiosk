package teamMuseumKiosk;

import controllers.AdminUpdateController;
import controllers.EndController;
import controllers.SplashController;
import controllers.StartController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public interface LoadScene {

    default void loadStartScene(Stage stage) throws IOException {
        URL url = new URL(getClass().getResource("/design/startScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        StartController controller = loader.getController();
        controller.setTimer(stage);
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    default void loadSplashScene(Stage stage, String fxmlURL) throws IOException {
        URL url = new URL(getClass().getResource(fxmlURL).toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        SplashController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    default void timerToSplashScene(Stage stage, int minutes) {
        //Automatically goes back to splash screen after 2 minutes
        PauseTransition delay = new PauseTransition(Duration.minutes(minutes));
        delay.setOnFinished( event -> {
            try {
                loadSplashScene(stage,"/design/splashScreen.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    default void loadEndScene(Stage stage)throws IOException {
        URL url = new URL(getClass().getResource("/design/endScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        EndController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    default void loadAdminUpdateScene(Stage stage) throws IOException {
        URL url = new URL(getClass().getResource("/design/adminUpdateScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminUpdateController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }
}
