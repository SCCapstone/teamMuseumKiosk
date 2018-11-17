package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import main.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

        if(name.getText().trim().length() > 3) {
	            missingInfoText.setText("Initials cannot be longer than 3 characters");
	            return;
	    }


	    //TODO: verify email is in an appropriate format. May use regex for this.
	    collectEmail(email.getText().trim());

        User newUser = new User(name.getText().trim(), 0, email.getText().trim());

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

    public void goToAdminPage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/adminOverviewScreen.fxml"));
        Parent root = loader.load();

        AdminOverviewController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 600, 1000);
        stage.setScene(scene);
        stage.show();
    }
    
    //Creates a buffered writer to append email to csv file
	private void collectEmail(String email) {
	    BufferedWriter writer = null;

        try {
	        writer = new BufferedWriter(new FileWriter("EmailList.csv", true));
	        writer.write(email);
	        writer.newLine();
	        writer.flush();
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
}
