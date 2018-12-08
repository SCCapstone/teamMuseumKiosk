package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Created by Jacob Cox on 12/7/2018.
 */
public class AddQuestionController {

    @FXML
    private TextArea question, wrong1, wrong2, wrong3, correct;

    @FXML
    private Button submitButton;

    public void addQuestion(ActionEvent actionEvent) throws IOException {
        String csvString = buildCSVString();
        BufferedWriter writer = null;

        try {
            writer  = new BufferedWriter(new FileWriter("TriviaQuestions.csv", true));
            writer.write(csvString);
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

        question.getScene().getWindow().hide();
    }

    public String buildCSVString() {
        StringBuilder csvString = new StringBuilder();
        ArrayList<String> strings = new ArrayList<String>();
        strings.add(question.getText());
        strings.add(wrong1.getText());
        strings.add(wrong2.getText());
        strings.add(wrong3.getText());

        for(String i : strings) {
            csvString.append(i);
            csvString.append(",");
        }
        csvString.append(correct.getText());
        return csvString.toString();
    }
}
