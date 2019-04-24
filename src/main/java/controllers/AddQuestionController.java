package controllers;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jacob Cox on 12/7/2018.
 */
public class AddQuestionController implements Initializable {

    @FXML
    private TextArea question, wrong1, wrong2, wrong3, correct;
    @FXML
    private ComboBox difficultyChoices;

    @FXML
    private Button submitButton;
    private FileChooser fileChooser;
    private File filePath = null;

    private Button cancelButton;
    private String difficulty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        ObservableList obList = FXCollections.observableList(list);
        difficultyChoices.getItems().clear();
        difficultyChoices.setItems(obList);
        difficulty = "1";
    }


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
                || correct.getText().equals("")) {
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

                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                showPopupWindow(stage, "Question Added");
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
        //csvString.append("\n");
        String questionText = question.getText();
        if (questionText.contains(","))
        {
            List<String> data = Arrays.asList(questionText.split(","));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + "`";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            questionText = formatted;
        }
        strings.add(questionText);
        String wrong1Text = wrong1.getText();
        if (wrong1Text.contains(","))
        {
            List<String> data = Arrays.asList(wrong1Text.split(","));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + "`";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            wrong1Text = formatted;
        }
        strings.add(wrong1Text);
        String wrong2Text = wrong2.getText();
        if (wrong2Text.contains(","))
        {
            List<String> data = Arrays.asList(wrong2Text.split(","));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + "`";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            wrong2Text = formatted;
        }
        strings.add(wrong2Text);
        String wrong3Text = wrong3.getText();
        if (wrong3Text.contains(","))
        {
            List<String> data = Arrays.asList(wrong3Text.split(","));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + "`";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            wrong3Text = formatted;
        }
        strings.add(wrong3Text);
        String correctText = correct.getText();
        if (correctText.contains(","))
        {
            List<String> data = Arrays.asList(correctText.split(","));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + "`";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            correctText = formatted;
        }
        strings.add(correctText);

        if (filePath ==null) {
            for (String i : strings) {
                csvString.append(i);
                csvString.append(",");
            }
            csvString.append(difficulty);

        }
        else {
            strings.add(difficulty);
            for (String i : strings) {
                csvString.append(i);
                csvString.append(",");
            }
            csvString.append("./videos/"+filePath.getName());

        }

        //csvString.append(difficulty);

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
            //TODO put in the images folder
            //File path = new File("src/main/resources/images/"+filePath.getName());
            //Path path = FileSystems.getDefault().getPath(question.getText()+".jpg");
            //Files.copy(filePath.toPath(),path.toPath());
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

    public void chooseDifficulty(ActionEvent actionEvent)
    {
        difficulty = "1";
        difficulty = difficultyChoices.getValue().toString();
    }

    public void exitPage(ActionEvent actionEvent) {
        question.getScene().getWindow().hide();
    }

    private void showPopupWindow(Stage stage, String text) {
        try {
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // initializing the controller
            PopupController popupController = loader.getController();
            loader.setController(popupController);

            Scene scene = new Scene(root, 300, 250);
            Stage popupStage = new Stage();

            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            popupController.setStage(popupStage);
            //Set text to correct
            popupController.setText(text);

            if(stage!=null) {
                popupStage.initOwner(stage);
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

