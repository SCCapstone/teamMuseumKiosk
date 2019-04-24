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
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminEditController extends AdminController {

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
                    numOfQuestionsSlider.setValue(Double.parseDouble(temp[1]));
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
            System.out.println("Cannot find settings file");
        }
    }

    public void exitPage() {
        imageView.getScene().getWindow().hide();
    }
}
