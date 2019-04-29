package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.net.URL;

import static org.junit.Assert.*;

public class StartControllerTest extends ApplicationTest {

    @Override
    public void start (Stage primaryStage) throws Exception {
        URL url = new URL(getClass().getResource("/design/startScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        StartController controller = new StartController();
        loader.setController(controller);

        Scene scene = new Scene(root,1440,900);
        primaryStage.setTitle("South Carolina State Museum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void enterInfo() {
        clickOn("#nameField");
        write("abc");
        clickOn("#emailField");
        write("test@email.com");
        clickOn("#playButton");
    }
}