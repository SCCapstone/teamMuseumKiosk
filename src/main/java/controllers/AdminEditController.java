package controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import teamMuseumKiosk.ResetAdminSettings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminEditController extends AdminController implements ResetAdminSettings {

    @FXML
    private ImageView imageView;

    @FXML
    private Slider numOfQuestionsSlider;

    @FXML
    private Label numOfQuestionsLabel;

    @FXML
    private ToggleGroup numOfStrikesOptions, highScoresOptions;

    @FXML
    private RadioButton numOfStrikes2Btn, numOfStrikes3Btn,
            dailyHighScoresBtn, weeklyHighScoresBtn, monthlyHighScoresBtn, rotateHighScoresBtn;

    @FXML
    private CheckBox rotateHighScoresChk;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField, repeatPasswordField;

    @FXML
    private Text missingInfoText;

    String settings = "settings.txt";

    public void initialize() {

        /**
         * Setting up the Slider for questions: set default number to 10 for now.
         */
        numOfQuestionsSlider.setMaxWidth(150);
        numOfQuestionsSlider.setMinWidth(150);
        numOfQuestionsSlider.setMin(5);
        numOfQuestionsSlider.setMax(15);
        numOfQuestionsSlider.setValue(10);
        numOfQuestionsSlider.setBlockIncrement(1.0);
        try {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
            for (String line : settingsList)
            {
                if (line.contains("maxQuestions"))
                {
                    String[] temp = line.split(" ");

                    // check for if settings.txt is incomplete
                    if (temp.length <= 1) {
                        ifSettingsFileIsEmpty("maxQuestions");
                    }
                    else {
                        numOfQuestionsSlider.setValue(Double.parseDouble(temp[1]));
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Cannot locate settings file");
        }

        numOfQuestionsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
                    String newSettings = "";
                    for(String line : settingsList) {
                       if(line.contains("maxQuestions"))
                       {
                           newSettings = newSettings + "\n" + "maxQuestions: " + newValue.intValue();
                       }
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
                }
                catch (IOException e)
                {
                    System.out.println("Cannot locate settings file");
                }
                //numOfQuestionsLabel.setText("Max number of Questions: " + Integer.toString(newValue.intValue()));
            }
        });

        try {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
            for(String line: settingsList){
                if(line.equals("strikeNum: 2"))
                    numOfStrikes2Btn.setSelected(true);
                else
                    numOfStrikes3Btn.setSelected(true);
            }
        } catch (IOException e) {}

        /**
         * Setting up the high scores displayed options to show last choice by admin
         * TODO: Save current display option, probably to file.
         * Setting high score display option to Daily for now.
         */
        //rotateHighScoresBtn.setSelected(true);
        try {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
            for (String line : settingsList)
            {

                if (line.contains("highScores")) //setting up high score buttons
                {
                    if (line.contains("daily"))
                    {
                        dailyHighScoresBtn.setSelected(true);
                    }
                    else if (line.contains("weekly"))
                    {
                        weeklyHighScoresBtn.setSelected(true);
                    }
                    else if (line.contains("monthly"))
                    {
                        monthlyHighScoresBtn.setSelected(true);
                    }
                    else //rotate
                    {
                        rotateHighScoresBtn.setSelected(true);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Cannot locate settings file");
        }
    }

    /**
     * If 'rotate through all of them' checkbox is selected, disable the radiobuttons.
     */

    public void strikeNumButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
            String strikeNum = null;
            for(String line : settingsList) {
                if(line.contains("strikeNum"))
                    strikeNum = line;
            }

            if(numOfStrikes3Btn.isSelected() == true){
                strikeNum = "strikeNum: 3";
            }
            else if(numOfStrikes2Btn.isSelected() == true){
                strikeNum = "strikeNum: 2";
            }

            String newSettings = "";
            for(String line : settingsList) {
                if(line.contains("strikeNum"))
                {
                    newSettings = newSettings + strikeNum;
                }
                else
                {
                    newSettings = newSettings + "\n" + line;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(settings, false));
            writer.write(newSettings);
            writer.close();

        } catch (IOException e){
            // reset strikeNum settings
            ifSettingsFileIsEmpty("strikeNum");
            System.out.println("Cannot locate settings file");
        }
    }

    @FXML
    public void rotateHighScores(ActionEvent actionEvent) throws IOException {

        try
        {
            List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
            String newSettings = "";
            for (String line : settingsList)
            {
                if (line.contains("highScores"))
                {
                    if (dailyHighScoresBtn.isSelected())
                    {
                        newSettings = newSettings + "\n" + "highScores: daily";
                    }
                    else if (weeklyHighScoresBtn.isSelected())
                    {
                        newSettings = newSettings + "\n" + "highScores: weekly";
                    }
                    else if (monthlyHighScoresBtn.isSelected())
                    {
                        newSettings = newSettings + "\n" + "highScores: monthly";
                    }
                    else //rotating
                    {
                        newSettings = newSettings + "\n" + "highScores: rotate";
                    }
                }
                else if (line.contains("strikeNum")) {
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
        }
        catch (IOException e)
        {
            // reset strikeNum settings
            ifSettingsFileIsEmpty("highScores");
            System.out.println("Cannot find settings file");
        }
    }

    public void changeUsernamePassword(ActionEvent actionEvent) throws IOException {
        String usernameKey = "";
        String passwordKey = "";

        String newUsernameString = "";
        String newPasswordString = "";
        String newSettings = "";

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
            }
        }

    }

    public void exitPage() {
        imageView.getScene().getWindow().hide();
    }
}
