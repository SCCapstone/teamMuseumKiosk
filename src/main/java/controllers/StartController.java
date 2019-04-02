package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javafx.stage.Modality;
import javafx.stage.Stage;
import teamMuseumKiosk.User;

import javax.swing.*;
import java.io.*;

import java.net.URL;
import java.util.ArrayList;

public class StartController {
    @FXML
    private TextField name, email;
    
    @FXML
    private Text missingInfoText;

    @FXML
    private ImageView imageView;

    private URL image = null;

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("TriviaQuestions.csv"));
        } catch (IOException e)
        {
            missingInfoText.setText("'TriviaQuestions.csv' file is not in the directory. Please add it before continuing.");
            return;
        }

        if(name.getText().trim().isEmpty() || email.getText().trim().isEmpty()){
	            missingInfoText.setText("Please enter your initials and email");
	            return;
	    }

        if(name.getText().trim().length() > 3) {
	            missingInfoText.setText("Initials cannot be longer than 3 characters");
	            return;
	    }

        //TODO: verify email is in an appropriate format. May use regex for this.
        String userEmail = email.getText().toLowerCase();
        if(!(userEmail.contains("@") && (userEmail.contains(".com")
                || userEmail.contains(".net") || userEmail.contains(".org")
                || userEmail.contains(".edu") || userEmail.contains(".co.uk")
                || userEmail.contains(".de") || userEmail.contains(".cn")
                || userEmail.contains(".kr") || userEmail.contains(".jp")
                || userEmail.contains(".mx") || userEmail.contains(".ru")))) {
            missingInfoText.setText("Please enter a valid email address");
            return;
        }
      
        User newUser = new User(name.getText().toUpperCase().trim(), 0, email.getText().trim());

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        URL url = new URL(getClass().getResource("/design/question.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
	    Parent root = loader.load();

        QuestionController controller = loader.getController();
	    controller.setUser(newUser);
	    loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
	    stage.setScene(scene);
	    stage.show();
    }

    public void goToAdminPage(ActionEvent actionEvent) throws IOException {
        //Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
	    
        URL url = new URL(getClass().getResource("/design/adminLoginScreen.fxml").toExternalForm());

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminLoginController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.showAndWait();

//        if(controller.image != null){
//            image = controller.image;
//            imageView.setImage(new Image(controller.image.toExternalForm()));
//        }
    }

    @FXML
    public void initialize() {
        // Make name input be uppercase
        name.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        // Sets advertisement
        if(image != null){
            imageView.setImage(new Image(image.toExternalForm()));
        }
    }
}
