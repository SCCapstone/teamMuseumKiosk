package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.User;

import java.net.URL;

import static org.junit.Assert.*;

public class EndControllerTest extends ApplicationTest implements LoadScene {
    private User user;
    private EndController controller;

    @Before
    public void setUp() {
        user = new User("name", 10, "test@email.com");
    }

    @Override
    public void start(Stage stage) throws Exception {
        setUp();
        URL url = new URL(getClass().getResource("/design/endScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        controller = loader.getController();
        controller.setUser(user);
        controller.setText();
        controller.setStage(stage);
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void setTextTest(){
        interact(() -> {
            controller.setText();
        });

        assertEquals("Thank you for playing, name!", lookup("#end").<Label>query().getText());
        assertEquals("name's Final Score: 10\nPlay again?",lookup("#score").<Label>query().getText());
    }

}