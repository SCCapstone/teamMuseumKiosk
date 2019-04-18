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
            dailyHighScoresBtn, weeklyHighScoresBtn, monthlyHighScoresBtn;

    @FXML
    private CheckBox rotateHighScoresChk;

    String settings = "settings.txt";

    public void initialize() {

        /**
         * Setting up the Slider for questions: set default number to 8 for now.
         */
        numOfQuestionsSlider.setMaxWidth(150);
        numOfQuestionsSlider.setMinWidth(150);
        numOfQuestionsSlider.setMin(5);
        numOfQuestionsSlider.setMax(10);
        numOfQuestionsSlider.setValue(8);
        numOfQuestionsSlider.setBlockIncrement(1.0);

        numOfQuestionsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numOfQuestionsLabel.setText("Max number of Questions: " + Integer.toString(newValue.intValue()));
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
        dailyHighScoresBtn.setSelected(true);
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

            BufferedWriter writer = new BufferedWriter(new FileWriter(settings, false));
            writer.write(strikeNum);
            writer.close();

        } catch (IOException e){
            System.out.println("Cannot locate settings file");
        }
    }

    @FXML
    public void rotateHighScores(ActionEvent actionEvent) throws IOException {

        if (rotateHighScoresChk.isSelected()) {

            dailyHighScoresBtn.setDisable(true);
            dailyHighScoresBtn.setSelected(false);

            weeklyHighScoresBtn.setDisable(true);
            weeklyHighScoresBtn.setSelected(false);

            monthlyHighScoresBtn.setSelected(false);
            monthlyHighScoresBtn.setDisable(true);
        }
    }

    public void exitPage() {
        imageView.getScene().getWindow().hide();
    }
}
