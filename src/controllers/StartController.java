package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import main.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private TextField name;

    public StartController(){}

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        User newUser = new User(name.getText(), 0);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/question.fxml"));
        Parent root = loader.load();

        QuestionController controller = loader.getController();
        controller.setUser(newUser);
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public void goToAdminPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/adminOverviewScreen.fxml"));
        Parent root = loader.load();

        AdminOverviewController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
