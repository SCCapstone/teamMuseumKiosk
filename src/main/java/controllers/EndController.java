package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import teamMuseumKiosk.HighScore;
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EndController implements Initializable, LoadScene {
    public User user;
    private Stage stage;
    @FXML
    Label end, score, highscore, high1, high2, high3, high4, high5, high6, high7, high8, high9, high10;
    @FXML
    Button button;

    public EndController() {}

    public void setUser(User user) {
        this.user = user;
    }
    public void setStage(Stage stage) { this.stage = stage; }

    public void setText() {
        try {
            if (!user.getName().equals("")) {
                end.setText("Thank you for playing, " + user.getName() + "!");
                score.setText(user.getName() + "'s Final Score: " + user.getScore());
                writeScore();
            } else {
                end.setText("Thank you for playing!");
                score.setText("Final Score: " + user.getScore());
                writeScore();
            }
            //set high scores
            highscore.setText("High Scores:");
            HighScore highscore = new HighScore("month");
            ArrayList<String> scores = highscore.getHighScores();
            high1.setText(scores.get(0));
            high2.setText(scores.get(1));
            high3.setText(scores.get(2));
            high4.setText(scores.get(3));
            high5.setText(scores.get(4));
            high6.setText(scores.get(5));
            high7.setText(scores.get(6));
            high8.setText(scores.get(7));
            high9.setText(scores.get(8));
            high10.setText(scores.get(9));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeScore() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("EmailList.csv", true));
            String userLine = user.getEmail() + "," + user.getName() + "," + user.getScore() + "," + java.time.LocalDate.now() + System.lineSeparator();
            writer.write(userLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Automatically goes back to splash screen after 2 minutes
        timerToSplashScene(stage,2);
    }

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Stage currentStage = (Stage) button.getScene().getWindow();
        loadStartScene(currentStage);
    }


}
