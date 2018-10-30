package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    public User user;
    @FXML
    Label end, score;
    @FXML
    Button button;

    public EndController() {}

    public void setUser(User user) {
        this.user = user;
    }

    public void setText() {
        try {
            if (!user.getName().equals("")) {
                end.setText("Thank you for playing, " + user.getName() + "!");
                score.setText(user.getName() + "'s Final Score: " + user.getScore() + "/5");
            } else {
                end.setText("Thank you for playing!");
                score.setText("Final Score: " + user.getScore() + "/5");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Stage currentStage = (Stage) button.getScene().getWindow();

        URL url = new URL(getClass().getResource("/design/trivia.fxml").toExternalForm());
        Parent questionPage = FXMLLoader.load(url);
        Scene scene = new Scene(questionPage, 600, 550);

        currentStage.setScene(scene);
        currentStage.show();
    }
}
