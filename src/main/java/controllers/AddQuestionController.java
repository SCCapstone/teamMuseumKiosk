package controllers;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void setTabKey() {
        question.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong1.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong2.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong3.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        correct.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
    }

    public void addQuestion(ActionEvent actionEvent) throws IOException {
        String csvString = buildCSVString();
        BufferedWriter writer = null;

        if (question.getText().equals("")
                || wrong1.getText().equals("")
                || wrong2.getText().equals("")
                || wrong3.getText().equals("")
                || correct.getText().equals("")
                || difficulty.getText().equals("")) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(0.75);
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            PopupController controller = loader.getController();
            loader.setController(controller);
            controller.setText("You must fill out form to add a question.");
            controller.setButtonText("OK");
            controller.setStage(stage);

            Scene scene = new Scene(root, 600, 485);
            stage.setScene(scene);
            stage.showAndWait();

        } else {
            try {
                writer = new BufferedWriter(new FileWriter("TriviaQuestions.csv", true));
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
    }

    public String buildCSVString() {
        StringBuilder csvString = new StringBuilder();
        ArrayList<String> strings = new ArrayList<String>();
        csvString.append("\n");
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


    private class TabKeyEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.TAB)) {
                Node node = (Node) event.getSource();
                if (node instanceof TextArea) {
                    TextAreaSkin skin = (TextAreaSkin) ((TextArea)node).getSkin();
                    if (!event.isControlDown()) {
                        // Tab or shift-tab => navigational action
                        if (event.isShiftDown()) {
                            skin.getBehavior().traversePrevious();
                        } else {
                            skin.getBehavior().traverseNext();
                        }
                    } else {
                        // Ctrl-Tab => insert a tab character in the text area
                        TextArea textArea = (TextArea) node;
                        textArea.replaceSelection("\t");
                    }
                    event.consume();
                }
            }
        }
    }
}