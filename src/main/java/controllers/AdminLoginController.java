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
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.ResetAdminSettings;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AdminLoginController extends AdminController implements LoadScene, ResetAdminSettings {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text missingInfoText;

    String settings = "settings.txt";


    public void buttonClick(ActionEvent actionEvent) throws IOException {

        missingInfoText.setText(username.getText() + " " + password.getText());

        // reading settings.txt for saved username/password
        List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
        String usernameKey = "";
        String passwordKey = "";

        for (String line : settingsList) {
            if (line.contains("username")) {
                String[] usernameLine = line.split(" ");

                if (usernameLine.length  <= 1) {
                    // settings.txt is incomplete
                    missingInfoText.setText("Default Username has been reset due to a system error.");
                    ifSettingsFileIsEmpty("username");
                    return;
                }
                else {
                    usernameKey = usernameLine[1];
                }

            }
            else if (line.contains("password")) {
                String[] passwordLine = line.split(" ");

                if (passwordLine.length <= 1) {
                    missingInfoText.setText("Default Password has been reset due to a system error.");
                    // settings.txt is incomplete
                    ifSettingsFileIsEmpty("password");
                    return;
                }
                else {
                    passwordKey = passwordLine[1];
                }

            }
         }

        if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            missingInfoText.setText("Please enter the username and password");
            System.out.println("hey");
            return;
        }

        // checking user input against saved login credentials
        if(!username.getText().trim().equals(usernameKey) || !password.getText().trim().equals(passwordKey)) {
            missingInfoText.setText("Incorrect Admin login. Please return to game or contact your admin.");
            return;
        }

        // login credentials were correct
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        loadAdminUpdateScene(stage);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        loadStartScene(stage);
    }

}