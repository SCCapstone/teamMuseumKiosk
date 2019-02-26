package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Created by Jacob Cox on 12/7/2018.
 */
public class AddQuestionController {

    @FXML
    private TextArea question, wrong1, wrong2, wrong3, correct, difficulty;

    @FXML
    private Button submitButton;

    private FileChooser fileChooser;
    private File filePath;

    public void addQuestion(ActionEvent actionEvent) throws IOException {
        String csvString = buildCSVString();
        BufferedWriter writer = null;

        try {
            writer  = new BufferedWriter(new FileWriter("TriviaQuestions.csv", true));
            writer.write(csvString);
            //writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
            }
        }

        question.getScene().getWindow().hide();
    }

    public String buildCSVString() {
        StringBuilder csvString = new StringBuilder();
        ArrayList<String> strings = new ArrayList<String>();
        strings.add(question.getText());
        strings.add(wrong1.getText());
        strings.add(wrong2.getText());
        strings.add(wrong3.getText());
        strings.add(correct.getText());
        if (filePath ==null) {
            for (String i : strings) {
                csvString.append(i);
                csvString.append(",");
            }
            csvString.append(difficulty.getText());

        }
        else {
            strings.add(difficulty.getText());
            for (String i : strings) {
                csvString.append(i);
                csvString.append(",");
            }
            csvString.append(question.getText()+".jpg");

        }
        csvString.append("\n");
        return csvString.toString();
    }

    public void addImage(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");

        String dir =System.getProperty("user.home");
        File userdir = new File(dir);
        if(!userdir.canRead())
            userdir = new File("C:/");

        fileChooser.setInitialDirectory(userdir);
        filePath = fileChooser.showOpenDialog(stage);

        try {
            Path path = FileSystems.getDefault().getPath(question.getText()+".jpg");
            Files.copy(filePath.toPath(),path);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
