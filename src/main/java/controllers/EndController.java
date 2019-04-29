package controllers;

import javafx.animation.AnimationTimer;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EndController implements Initializable, LoadScene {
    public User user;
    private int i;
    private Stage stage;
    @FXML
    Label end, score, highscoreText, high1, high2, high3, high4, high5, high6, high7, high8, high9, high10;
    @FXML
    Button yes,no;

    String settings = "settings.txt";

    public EndController() {}

    public void setUser(User user) {
        this.user = user;
    }
    public void setStage(Stage stage) { this.stage = stage; }

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

            //decipher settings.txt for high scores
            writeHighScores();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeHighScores() throws IOException {
        List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
        for (String line : settingsList)
        {
            if (line.contains("highScores"))
            {
                String[] asdf = line.split(" ");
                String highscoreTime = asdf[1];
                HighScore highscore = new HighScore();
                if (highscoreTime.equals("daily"))
                {
                    highscoreText.setText("Today's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(0);
                    setHighScores(scores);
                }
                else if (highscoreTime.equals("weekly"))
                {
                    highscoreText.setText("This Week's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(1);
                    setHighScores(scores);
                }
                else if (highscoreTime.equals("monthly"))
                {
                    highscoreText.setText("This Month's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(2);
                    setHighScores(scores);
                }
            }
            else //rotate
            {
                rotateHighScores();
            }
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

    private void setHighScores(ArrayList<String> scores) {
        if (scores.size() >= 1)
        {
            high1.setText(scores.get(0));
            if (scores.size() >= 2)
            {
                high2.setText(scores.get(1));
                if (scores.size() >= 3)
                {
                    high3.setText(scores.get(2));
                    if (scores.size() >= 4)
                    {
                        high4.setText(scores.get(3));
                        if (scores.size() >= 5)
                        {
                            high5.setText(scores.get(4));
                            if (scores.size() >= 6)
                            {
                                high6.setText(scores.get(5));
                                if (scores.size() >= 7)
                                {
                                    high7.setText(scores.get(6));
                                    if (scores.size() >= 8)
                                    {
                                        high8.setText(scores.get(7));
                                        if (scores.size() >= 9)
                                        {
                                            high9.setText(scores.get(8));
                                            if (scores.size() >= 10)
                                            {
                                                high10.setText(scores.get(9));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void rotateHighScores() {
        //display on rotation
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                HighScore highscore = new HighScore();
                if (i%1500 > 0 && i%1500 < 500)
                {
                    highscoreText.setText("This Month's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(2);
                    setHighScores(scores);
                }
                else if (i%1500 > 500 && i%1500 < 1000)
                {
                    highscoreText.setText("This Week's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(1);
                    setHighScores(scores);
                }
                else if (i%1500 > 1000 && i%1500 < 1499)
                {
                    highscoreText.setText("Today's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(0);
                    setHighScores(scores);
                }
                i++;
            }
        };
        timer.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        if (button.getText().equals("No"))
            loadStartScene(this.stage);
        else {
            loadQuestionScene(this.stage, this.user);
        }

    }

}
