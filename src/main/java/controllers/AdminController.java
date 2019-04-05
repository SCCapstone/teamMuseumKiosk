package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class AdminController {
    public void goToOverviewPage(ActionEvent actionEvent) throws IOException {
        //TODO
    }
    public void goToUpdatePage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        URL url = new URL(getClass().getResource("/design/adminUpdateScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminUpdateController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    public void goToEditPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        URL url = new URL(getClass().getResource("/design/adminEditScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminEditController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Return to start screen
     * @param actionEvent
     * @throws IOException
     */
    public void exitPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
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
}
