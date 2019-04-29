package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {
    private Stage stage;

    @FXML
    Label result;
    @FXML
    Button button;

    // only for deleting advertisements pop up
    @FXML
    Button deleteAdButton;

    boolean deleteAdvertisement = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Automatically closes the popup after 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished( event -> closeStage() );
        delay.play();
    }

    public void onButtonClick(ActionEvent actionEvent) {
        closeStage();
    }

    public void setButtonText(String text) { button.setText(text); }

    public void setDeleteAdButton(String text) {
        deleteAdButton.setText(text);
        deleteAdButton.setVisible(true);
    }

    public Button getButton() { return this.button; }

    public Button getDeleteAdButton() { return this.deleteAdButton; }

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
     * for deleting advertisements only
     * if user selects 'yes' - delete file
     * else, just close window
     */
    public void deleteAdvertisement(ActionEvent actionevent) {
        deleteAdvertisement = true;
        closeStage();
    }

    public boolean isDeleteAdFile() {
        return deleteAdvertisement;
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
