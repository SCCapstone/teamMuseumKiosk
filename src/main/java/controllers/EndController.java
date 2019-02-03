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
import teamMuseumKiosk.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    public User user;
    @FXML
    Label end, score,high1,high2,high3,high4,high5,high6,high7,high8,high9,high10;
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
                score.setText(user.getName() + "'s Final Score: " + user.getScore());
                writeScore();
            } else {
                end.setText("Thank you for playing!");
                score.setText("Final Score: " + user.getScore());
                writeScore();
            }
            //TODO: this is where scoreboard will be set

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

        URL url = new URL(getClass().getResource("/design/startScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        StartController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        currentStage.setScene(scene);
        currentStage.show();
    }
}
