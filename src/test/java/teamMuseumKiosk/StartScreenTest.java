package teamMuseumKiosk;

import controllers.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.api.FxToolkit;


import java.net.URL;

public class StartScreenTest extends ApplicationTest {

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
        clickOn("#button");
    }

    @Test
    public void openAndCloseAdminPage() {
        clickOn("#adminButton");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        clickOn("#exitAdminButton");
    }
}
