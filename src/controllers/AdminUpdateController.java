package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdminUpdateController extends AdminController {


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
    public void uploadQuestions(ActionEvent actionEvent) throws IOException {
        FileChooser choose = new FileChooser();
        File select = choose.showOpenDialog(null);
        if (select != null) {
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/src/" + select.getName()));
            Files.copy(select.toPath(), Paths.get(System.getProperty("user.dir")+"/src/"+select.getName()));
            //TODO need to refresh the program to reload questions
            
        }
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
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/src/" + select.getName()));
            Files.copy(select.toPath(), Paths.get(System.getProperty("user.dir")+"/src/"+select.getName()));
            //TODO need to refresh the program to reload advertisments
        }
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
