package controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;

import java.io.IOException;

public class AdminEditController extends AdminController {


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

        /**
         * Setting up the number of strikes options to show last choice by admin
         * TODO: Save current num of strikes choice, probably to file.
         * Setting num of strikes to 3 by default for now.
         */
        numOfStrikes3Btn.setSelected(true);

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

}
