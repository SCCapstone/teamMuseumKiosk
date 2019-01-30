package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {
    private Stage stage;

    @FXML
    Label result;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void onButtonClick(ActionEvent actionEvent) {
        closeStage();
    }


    public void setText(String text) {
        result.setText(text);
    }

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        try {
            if (stage != null)
                stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
