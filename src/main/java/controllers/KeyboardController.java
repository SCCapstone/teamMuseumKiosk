package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
   /* @FXML
    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24,
    b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48;*/

    private Stage stage;
    public String letter;

    @Override
    public void initialize(URL url, ResourceBundle rb){


    }
    public String getLetter(){return letter;}

    public void buttonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        letter = button.getText();
        System.out.println(letter);
    }
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
