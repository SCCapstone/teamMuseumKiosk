package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import teamMuseumKiosk.LoadScene;

import java.io.IOException;

public class SplashController implements LoadScene {

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        loadStartScene(stage);
    }
}
