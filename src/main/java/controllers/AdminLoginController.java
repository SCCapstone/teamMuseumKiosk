package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminLoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text missingInfoText;


    public void buttonClick(ActionEvent actionEvent) throws IOException {
        if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            missingInfoText.setText("Please enter the username and password");
            return;
        }

        missingInfoText.setText(username.getText() + " " + password.getText());

//        TODO: change this to check username/password set by admin, not hard coded!
        if (!username.getText().trim().equals("museumAdmin") || !password.getText().trim().equals("museumKiosk19")) {
            missingInfoText.setText("Incorrect Admin login. Please return to game or contact your admin.");
            return;
        }

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

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        URL url = new URL(getClass().getResource("/design/startScreen.fxml").toExternalForm());

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        StartController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

}