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
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.net.URL;

public class StartController {
    @FXML
    private TextField name, email;
    
    @FXML
    private Text missingInfoText;

    @FXML
    private ImageView imageView;

    private URL image = null;

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
        if(!(email.getText().contains("@") && (email.getText().contains(".com")
                || email.getText().contains(".net") || email.getText().contains(".org")
                || email.getText().contains(".edu") || email.getText().contains(".co.uk")
                || email.getText().contains(".de") || email.getText().contains(".cn")
                || email.getText().contains(".kr") || email.getText().contains(".jp")
                || email.getText().contains(".mx") || email.getText().contains(".ru")))) {
            missingInfoText.setText("Please enter a valid email address");
            return;
        }
      
	    //collectEmail(email.getText().trim());
        //TODO: no longer use collectEmail function, need to remove

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
	    
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/adminOverviewScreen.fxml"));	    
	// Goes to the update page for demo purposes
        // TODO: change this back after demo!
        URL url = new URL(getClass().getResource("/design/adminUpdateScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

//         AdminOverviewController controller = loader.getController();
	// Using Update controller for demo purposes
        AdminUpdateController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.showAndWait();

        if(controller.image != null){
            image = controller.image;
            imageView.setImage(new Image(controller.image.toExternalForm()));
        }
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
