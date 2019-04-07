package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.User;

import javax.swing.*;
import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class StartController implements LoadScene, Initializable {
    @FXML
    private TextField name, email;
    
    @FXML
    private Text missingInfoText;

    @FXML
    private ImageView imageView;

    private URL image = null;
    private Stage stage;

    public void setTimer(Stage stage){
        this.stage = stage;
        //Automatically goes back to splash screen after 2 minutes
        timerToSplashScene(stage,2);
    }

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

        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");

        if(!emailRegex.matcher(userEmail).matches()) {
            missingInfoText.setText("Please enter a valid email address");
            return;
        }

        /*if(!(userEmail.contains("@") && (userEmail.contains(".com")
                || userEmail.contains(".net") || userEmail.contains(".org")
                || userEmail.contains(".edu") || userEmail.contains(".co.uk")
                || userEmail.contains(".de") || userEmail.contains(".cn")
                || userEmail.contains(".kr") || userEmail.contains(".jp")
                || userEmail.contains(".mx") || userEmail.contains(".ru")))) {
            missingInfoText.setText("Please enter a valid email address");
            return;
        }*/

        User newUser = new User(name.getText().toUpperCase().trim(), 0, email.getText().trim());

        URL url = new URL(getClass().getResource("/design/question.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
	    Parent root = loader.load();

        QuestionController controller = loader.getController();
	    controller.setUser(newUser);
	    controller.setStage(this.stage);
	    controller.setTimer();
	    loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
	    this.stage.setScene(scene);
	    this.stage.show();
    }

    public void goToAdminPage(ActionEvent actionEvent) throws IOException {
        URL url = new URL(getClass().getResource("/design/adminLoginScreen.fxml").toExternalForm());

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminLoginController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        this.stage.setScene(scene);
        this.stage.show();

//        if(controller.image != null){
//            image = controller.image;
//            imageView.setImage(new Image(controller.image.toExternalForm()));
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Make name input be uppercase
        this.name.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        // Sets advertisement
        if(this.image != null){
            this.imageView.setImage(new Image(image.toExternalForm()));
        }
    }


}
