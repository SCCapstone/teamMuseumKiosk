package controllers;

import javafx.event.ActionEvent;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminOverviewController {

    public void initialize() {
    }

    /**
     * TODO: implement functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void exitProgram(ActionEvent actionEvent) throws IOException {

        /**
         * TODO: should probably have a popup box warning "Are you sure you want to exit the program?
         *  You will need to manually open the program to restart it."
         */

    }

    public void goToUpdatePage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/adminUpdateScreen.fxml"));
        Parent root = loader.load();

        AdminUpdateController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public void goToEditPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/adminEditScreen.fxml"));
        Parent root = loader.load();

        AdminEditController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/startScreen.fxml"));
        Parent root = loader.load();

        StartController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
        stage.setScene(scene);
        stage.show();
    }
}
