package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private TextField name, email;
    @FXML
    private Text missingInfoText;

    public StartController(){}

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        if(name.getText().trim().isEmpty() || email.getText().trim().isEmpty()){
            missingInfoText.setText("Please enter your initials and email");
            return;
        }
        User newUser = new User(name.getText(), 0, email.getText());

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/question.fxml"));
        Parent root = loader.load();

        QuestionController controller = loader.getController();
        controller.setUser(newUser);
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 550);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}