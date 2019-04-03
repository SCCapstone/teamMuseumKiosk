package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AdminUpdateController extends AdminController {

    @FXML
    private ImageView imageView;
    public URL image = null;
    public void initialize() {}

    /**
     * TODO: implement functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void exportEmails(ActionEvent actionEvent) throws IOException {

    }

    /**
     * TODO: implement functionality
     *
     * @param actionEvent
     * @throws IOException
     */
  /*  public void uploadQuestions(ActionEvent actionEvent) throws IOException {
        FileChooser choose = new FileChooser();
        File select = choose.showOpenDialog(null);
        if (select != null) {
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/src/" + select.getName()));
            Files.copy(select.toPath(), Paths.get(System.getProperty("user.dir")+"/src/"+select.getName()));
            //TODO need to refresh the program to reload questions
        }

    }*/

    public void uploadQuestions(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        URL url = new URL(getClass().getResource("/design/addQuestionScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AddQuestionController controller = loader.getController();
        controller.setTabKey();
        loader.setController(controller);

        Scene scene = new Scene(root, 600,485);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * TODO: implement functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void uploadAdvertisements(ActionEvent actionEvent) throws IOException {
        FileChooser choose = new FileChooser();
        File select = choose.showOpenDialog(null);
        if (select != null) {
            //Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/src/" + select.getName()));
           // Files.copy(select.toPath(), Paths.get(System.getProperty("user.dir")+"/"+select.getName()));
            //TODO need to refresh the program to reload advertisments
            try {
                image = select.toURI().toURL();
                System.out.println(image.toString());
            } catch (MalformedURLException e) {

            }
        }

    }

    public void exitPage() {
        imageView.getScene().getWindow().hide();
    }

    /**
     * TODO: implement functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void selectAdvertisements(ActionEvent actionEvent) throws IOException {

    }

}