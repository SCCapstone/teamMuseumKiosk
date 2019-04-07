package controllers;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private int i;
    private Stage stage;
    @FXML
    Label end, score, highscoreText, high1, high2, high3, high4, high5, high6, high7, high8, high9, high10;
    @FXML
    Button yes,no;

    public EndController() {}

    public void setUser(User user) {
        this.user = user;
    }
    public void setStage(Stage stage) { this.stage = stage; }
    public void setTimer() { timerToSplashScene(this.stage,2);}

    public void setText() {
        try {
            if (!user.getName().equals("")) {
                end.setText("Thank you for playing, " + user.getName() + "!");
                score.setText(user.getName() + "'s Final Score: " + user.getScore() + "\nPlay again?");
                //write score to email list
                writeScore();
            } else {
                end.setText("Thank you for playing!");
                score.setText("Final Score: " + user.getScore());
                writeScore();
            }
            //set high scores
            highscoreText.setText("High Scores:");
            //2=month 1=week 0=day

            //display on rotation
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    HighScore highscore = new HighScore();
                    if (i%1500 > 0 && i%1500 < 500)
                    {
                        highscoreText.setText("This Month's High Scores:");
                        ArrayList<String> scores = highscore.getHighScores().get(2);
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
                    }
                    else if (i%1500 > 500 && i%1500 < 1000)
                    {
                        highscoreText.setText("This Week's High Scores:");
                        ArrayList<String> scores = highscore.getHighScores().get(1);
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
                    }
                    else if (i%1500 > 1000 && i%1500 < 1499)
                    {
                        highscoreText.setText("Today's High Scores:");
                        ArrayList<String> scores = highscore.getHighScores().get(0);
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
                    }
                    i++;
                }
            };
            timer.start();


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
    public void initialize(URL location, ResourceBundle resources) {}

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Stage currentStage = (Stage) button.getScene().getWindow();
        if (button.getText().equals("No"))
            loadStartScene(currentStage);
        else {
            URL url = new URL(getClass().getResource("/design/question.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            QuestionController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(this.stage);
            controller.setTimer();
            loader.setController(controller);

            Scene scene = new Scene(root, 1440,900);
            this.stage.setScene(scene);
            this.stage.show();
        }

    }

}
