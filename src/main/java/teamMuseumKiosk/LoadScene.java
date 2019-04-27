package teamMuseumKiosk;

import controllers.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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
        controller.setStage(stage);
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    default void loadSplashScene(Stage stage) throws IOException {
        URL url = new URL(getClass().getResource("/design/splashScreen.fxml").toExternalForm());
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
                loadSplashScene(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    default void loadEndScene(Stage stage, User user)throws IOException {
        URL url = new URL(getClass().getResource("/design/endScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        EndController controller = loader.getController();
        controller.setUser(user);
        controller.setText();
        controller.setStage(stage);
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

    default void loadQuestionScene(Stage stage, User user) throws IOException {
        URL url = new URL(getClass().getResource("/design/question.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        QuestionController controller = loader.getController();
        controller.setUser(user);
        controller.setStage(stage);
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    default void loadPopupScene(Stage stage, String text) throws IOException {
        URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        // initializing the controller
        PopupController popupController = loader.getController();
        loader.setController(popupController);
        Scene scene = new Scene(root, 200, 250);
        Stage popupStage = new Stage();

        // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
        popupController.setStage(popupStage);
        //Set text to say correct or incorrect
        popupController.setText(text);

        if(stage!=null) {
            popupStage.initOwner(stage);
        }
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
