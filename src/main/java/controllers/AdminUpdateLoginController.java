package controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teamMuseumKiosk.ResetAdminSettings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUpdateLoginController extends AdminController implements ResetAdminSettings {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField, repeatPasswordField;

    @FXML
    private Text missingInfoText;

    @FXML
    private Button backButton;

    String settings = "settings.txt";

    public void updateUsernamePassword(ActionEvent actionEvent) throws IOException {

        String usernameKey = "";
        String passwordKey = "";

        String newUsernameString = "";
        String newPasswordString = "";
        String newSettings = "";

        boolean bothChanged = false;

        try {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());

            for (String line : settingsList) {
                if (line.contains("username")) {
                    String[] usernameLine = line.split(" ");

                    if (usernameLine.length == 1 || usernameLine[1].equals("") || usernameLine[1].equals(" ")) {
                        // settings.txt is incomplete
                        ifSettingsFileIsEmpty("username");
                        return;
                    }
                    else {
                        usernameKey = usernameLine[1];
                    }
                }

                if (line.contains("password")) {
                    String [] passwordLine = line.split(" ");

                    if (passwordLine.length == 1 || passwordLine[1].equals("") || passwordLine[1].equals(" ")) {
                        // settings.txt is incomplete
                        ifSettingsFileIsEmpty("password");
                        return;
                    }
                    else {
                        passwordKey = passwordLine[1];
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Cannot find settings file");
        }

        // check if both fields changed (just for pop up message)
        if (!usernameField.getText().trim().equals(usernameKey) && !usernameField.getText().isEmpty() &&
                passwordField.getText().trim().equals(repeatPasswordField.getText().trim()) &&
                !passwordField.getText().trim().equals(passwordKey) && !passwordField.getText().isEmpty()) {
            bothChanged = true;
        }
        else if(usernameField.getText().isEmpty() && passwordField.getText().isEmpty()) {
            missingInfoText.setText("No changes have been made. Enter a new username or password or close this window.");
        }

        // check if username changed & if field isn't empty
        if (!usernameField.getText().trim().equals(usernameKey) && !usernameField.getText().isEmpty()) {
            // update username saved in file
            usernameKey = usernameField.getText().trim();
            newUsernameString = "username: " + usernameKey;
            newSettings = "";

            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());

            for (String line : settingsList) {
                if (line.contains("username")) {
                    newSettings = newSettings + "\n" + newUsernameString;
                }

                // for other settings
                else if (line.contains("strikeNum"))
                {
                    newSettings = newSettings + line;
                }
                else
                {
                    newSettings = newSettings + "\n" + line;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(settings, false));
            writer.write(newSettings);
            writer.close();

            missingInfoText.setText("");

            // if true, pop up will be called when checking password field
            if (bothChanged == false) {
                // show pop up screen
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                showPopupWindow(stage, "Username updated");
                usernameField.getScene().getWindow().hide();
            }

        }
        else if (usernameField.getText().trim().equals(usernameKey)) {
            missingInfoText.setText("Username equals your previous username. No changes have been made.");
        }

        // check if password & repeat password field match first
        if (!passwordField.getText().trim().equals(repeatPasswordField.getText().trim())) {
            missingInfoText.setText("The passwords you entered don't match.");
            return;
        }
        else {
            // check if password changed & if fields aren't empty
            if (!passwordField.getText().trim().equals(passwordKey) && !passwordField.getText().isEmpty()) {
                // update password saved in file
                passwordKey = passwordField.getText().trim();
                newPasswordString = "password: " + passwordKey;
                newSettings = "";

                List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());

                for (String line : settingsList) {
                    if (line.contains("password")) {
                        newSettings = newSettings + "\n" + newPasswordString;
                    }
                    // for other settings
                    else if (line.contains("strikeNum"))
                    {
                        newSettings = newSettings + line;
                    }
                    else
                    {
                        newSettings = newSettings + "\n" + line;
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(settings, false));
                writer.write(newSettings);
                writer.close();

                // erase any error messages in missing text
                missingInfoText.setText("");

                // show pop up screen
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

                if (bothChanged == true) {
                    showPopupWindow(stage, "Username and password updated");
                }
                else {
                    showPopupWindow(stage, "Password updated");
                }
                passwordField.getScene().getWindow().hide();

            }
            else if (passwordField.getText().trim().equals(passwordKey)) {
                missingInfoText.setText("Password equals your previous password. No changes have been made.");
            }
        }
    }

    private void showPopupWindow(Stage stage, String text) throws IOException { loadPopupScene(stage, text); }

    public void closePage(ActionEvent actionEvent) throws IOException {
        passwordField.getScene().getWindow().hide();
    }
}
