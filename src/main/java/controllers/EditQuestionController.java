package controllers;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.beans.property.SimpleStringProperty;
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
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditQuestionController implements Initializable {

    @FXML
    private TextArea question, wrong1, wrong2, wrong3, correct;

    private String difficulty,oldMedia;

    @FXML
    private ComboBox difficultyChoices;

    @FXML
    private Button editButton, deleteButton;
    private FileChooser fileChooser;
    private File filePath;

    private String origQuestion, origAnswer1, origAnswer2, origAnswer3, origCorrect, origDifficulty;

    private final ObservableList<Question> dataList = FXCollections.observableArrayList();

    public String getOrigQuestion() { return origQuestion; }
    public String getOrigAnswer1() { return origAnswer1; }
    public String getOrigAnswer2() { return origAnswer2; }
    public String getOrigAnswer3() { return origAnswer3; }
    public String getOrigCorrect() { return origCorrect; }
    public String getOrigDifficulty() { return origDifficulty; }

    public void setOrigQuestion(String origQuestion) {
        this.origQuestion = origQuestion;
        question.setText(origQuestion);
    }
    public void setOrigAnswer1(String origAnswer1) {
        this.origAnswer1 = origAnswer1;
        wrong1.setText(origAnswer1);
    }
    public void setOrigAnswer2(String origAnswer2) {
        this.origAnswer2 = origAnswer2;
        wrong2.setText(origAnswer2);
    }
    public void setOrigAnswer3(String origAnswer3) {
        this.origAnswer3 = origAnswer3;
        wrong3.setText(origAnswer3);
    }
    public void setOrigCorrect(String origCorrect) {
        this.origCorrect = origCorrect;
        correct.setText(origCorrect);
    }
    public void setOrigDifficulty(String origDifficulty) {
        this.origDifficulty = origDifficulty;
        difficulty = origDifficulty;
        // display original difficulty
        difficultyChoices.setValue(difficulty);
    }

    // getting original datalist questions to check what user edited/deleted
    public class Question {
        // each question has prompt, 4 answers (last answer is correct one), and difficulty
        private SimpleStringProperty prompt, answer1, answer2, answer3, correctAnswer, difficulty, media;

        public String getPrompt() { return prompt.get(); }
        public String getAnswer1() { return answer1.get(); }
        public String getAnswer2() { return answer2.get(); }
        public String getAnswer3() { return answer3.get(); }
        public String getCorrectAnswer() { return correctAnswer.get(); }
        public String getDifficulty() { return difficulty.get(); }
        public String getMedia() {
            if (media != null)
            {
                return media.get();
            }
            else
            {
                return null;
            }
        }

        public void setMedia(String file) { this.media = new SimpleStringProperty(file); }

        Question(String prompt, String answer1, String answer2, String answer3,
                 String correctAnswer, String difficulty) {

            this.prompt = new SimpleStringProperty(prompt);
            this.answer1 = new SimpleStringProperty(answer1);
            this.answer2 = new SimpleStringProperty(answer2);
            this.answer3 = new SimpleStringProperty(answer3);
            this.correctAnswer = new SimpleStringProperty(correctAnswer);
            this.difficulty = new SimpleStringProperty(difficulty);
        }
        Question(String prompt, String answer1, String answer2, String answer3,
                 String correctAnswer, String difficulty, String file) {

            this.prompt = new SimpleStringProperty(prompt);
            this.answer1 = new SimpleStringProperty(answer1);
            this.answer2 = new SimpleStringProperty(answer2);
            this.answer3 = new SimpleStringProperty(answer3);
            this.correctAnswer = new SimpleStringProperty(correctAnswer);
            this.difficulty = new SimpleStringProperty(difficulty);
            this.media = new SimpleStringProperty(file);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        ObservableList obList = FXCollections.observableList(list);
        difficultyChoices.getItems().clear();
        difficultyChoices.setItems(obList);
    }

    public void setTabKey() {
        question.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong1.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong2.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        wrong3.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
        correct.addEventFilter(KeyEvent.KEY_PRESSED, new TabKeyEventHandler());
    }

    public void editQuestion(ActionEvent actionEvent) throws IOException {
        String newQuestionString = buildCSVString();

        String fileName = "";
        if (filePath != null)
        {
            fileName = filePath.getName();
        }
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
        }

//       user didn't change original question
        else if (question.getText().equals(origQuestion)
                && wrong1.getText().equals(origAnswer1)
                && wrong2.getText().equals(origAnswer2)
                && wrong3.getText().equals(origAnswer3)
                && correct.getText().equals(origCorrect)
                && difficulty.equals(origDifficulty)
                && ((!newQuestionString.contains("/Images/"))) || (!newQuestionString.contains("/Videos/"))
                && fileName.equals(oldMedia)) {

            System.out.println("nothing changed.");

            // notifying user.
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(0.75);
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            PopupController controller = loader.getController();
            loader.setController(controller);
            controller.setText("No changes were made.");
            controller.setButtonText("OK");
            controller.setStage(stage);

            Scene scene = new Scene(root, 600, 485);
            stage.setScene(scene);
            stage.showAndWait();

            question.getScene().getWindow().hide();
        }

        // User changed something - replacing old line with new edit
        else {

            // updating csv file
            updateCSV(newQuestionString);

            // notifying user.
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(0.75);
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            PopupController controller = loader.getController();
            loader.setController(controller);
            controller.setText("The question has been updated.");
            controller.setButtonText("OK");
            controller.setStage(stage);

            Scene scene = new Scene(root, 600, 485);
            stage.setScene(scene);
            stage.showAndWait();

            question.getScene().getWindow().hide();
        }
    }

    public void deleteQuestion(ActionEvent actionEvent) throws IOException {
        BufferedWriter writer = null;
        BufferedReader reader = null;

        readCSV();

        int listSize = dataList.size();
        int questionRow = 0;

        // getting row of question
        for (int i=0; i < listSize; i++) {

            if(origQuestion.equals(dataList.get(i).prompt.getValue()))
            {
                // saving index
                questionRow = i;
            }
        }

        // delete question line from datalist
        dataList.remove(questionRow);

        try {
            File original = new File("TriviaQuestions.csv");

            reader = new BufferedReader(new FileReader(original));
            String content = "";
            String oldQuestionString = origQuestion + "," + origAnswer1 + "," + origAnswer2 + "," + origAnswer3 + "," + origCorrect + "," + origDifficulty;

            String line = reader.readLine();
            while (line != null && !line.equals(oldQuestionString))
            {
                content = content + line +System.lineSeparator();
                line = reader.readLine();
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter(original));
            writer.write(content);

            writer.close();

            // notifying user.
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(0.75);
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            PopupController controller = loader.getController();
            loader.setController(controller);
            controller.setText("The question has been deleted.");
            controller.setButtonText("OK");
            controller.setStage(stage);

            Scene scene = new Scene(root, 600, 485);
            stage.setScene(scene);
            stage.showAndWait();

            question.getScene().getWindow().hide();

        } catch (Exception e) {}
    }

    public void updateCSV(String newQuestionString) {
        BufferedWriter writer = null;
        BufferedReader reader = null;

        // grabbing questions list from ViewQuestionsController
        readCSV();

        int listSize = dataList.size();
        int questionRow = 0;

        // getting row of question
        for (int i=0; i < listSize; i++) {

            if(origQuestion.equals(dataList.get(i).prompt.getValue()))
            {
                // saving index
                questionRow = i;
            }
        }

        // creating new Question object
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
        Question newQuestion = new Question(questionText, wrong1Text, wrong2Text, wrong3Text, correctText, difficulty);
        String newNewQuestionString = questionText + "," + wrong1Text + "," + wrong2Text + "," + wrong3Text + "," + correctText + "," + difficulty;
        if (filePath == null) {
            boolean nothing = true;
        }
        else {
            //newNewQuestionString += "," + ("./images/"+filePath.getName());
            //newQuestion.setMedia("./images/"+filePath.getName());
            if(filePath.getName().contains("mp4") || filePath.getName().contains("wav")){
                newNewQuestionString += ",./Videos/"+filePath.getName();
                newQuestion.setMedia("./Videos/"+filePath.getName());
            }
            else {
                newNewQuestionString += ",./Images/" + filePath.getName();
                newQuestion.setMedia("./Images/" + filePath.getName());
            }
        }
        // replace question/answers in datalist with updated version
        dataList.set(questionRow, newQuestion);

        // editing csv file
        try {
            File original = new File("TriviaQuestions.csv");
            // temp file for edits

            reader = new BufferedReader(new FileReader(original));

            String oldContent = "";
            String oldQuestionString = origQuestion + "," + origAnswer1 + "," + origAnswer2 + "," + origAnswer3 + "," + origCorrect + "," + origDifficulty;

            String line = reader.readLine();
            while (line != null)
            {
                if (line.contains(oldQuestionString))
                {
                    oldContent = oldContent + newNewQuestionString + System.lineSeparator();
                }
                else
                {
                    oldContent = oldContent + line +System.lineSeparator();
                }
                line = reader.readLine();
            }
            if (filePath != null)
            {
                //find out if image is already there
            }
            //String oldQuestionString = origQuestion + "," + origAnswer1 + "," + origAnswer2 + "," + origAnswer3 + "," + origCorrect + "," + origDifficulty;
            //String newContent = oldContent.replaceAll(oldQuestionString,newNewQuestionString);

            writer = new BufferedWriter(new FileWriter(original));
            //writer.write(newContent);
            writer.write(oldContent);
            reader.close();
            writer.close();

        } catch (Exception e) {}
    }

    public void readCSV() {

        String csvFile = "TriviaQuestions.csv";
        String fieldDelimiter = ",";
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(csvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(fieldDelimiter, -1);

                // original question before edit/delete
                Question origQuestion = new Question(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                dataList.add(origQuestion);
                if (fields.length == 7)
                {
                    oldMedia = fields[6];
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminUpdateController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminUpdateController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    public String buildCSVString() {
        StringBuilder newQuestionString = new StringBuilder();
        ArrayList<String> strings = new ArrayList<String>();

        strings.add(question.getText());
        strings.add(wrong1.getText());
        strings.add(wrong2.getText());
        strings.add(wrong3.getText());
        strings.add(correct.getText());
        difficulty = difficultyChoices.getValue().toString();

        if (filePath == null) {
            for (String i : strings) {
                newQuestionString.append(i);
                newQuestionString.append(",");
            }

            newQuestionString.append(difficulty);

        }
        else {
            strings.add(difficulty);
            for (String i : strings) {
                newQuestionString.append(i);
                newQuestionString.append(",");
            }
            //newQuestionString.append("./images/"+filePath.getName());
            if(filePath.getName().contains("mp4") || filePath.getName().contains("wav")){
                newQuestionString.append("./Videos/"+filePath.getName());
            }
            else {
                newQuestionString.append("./Images/" + filePath.getName());
            }
        }
        return newQuestionString.toString();

    }

    // add image filename to csv file
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
            //File path = new File("./images/"+filePath.getName());
            //Files.copy(filePath.toPath(),path.toPath());
            if(filePath.getName().contains("mp4") || filePath.getName().contains("wav")){
                File path = new File("./Videos/" + filePath.getName());
                Files.copy(filePath.toPath(), path.toPath());
            }
            else {
                File path = new File("./Images/" + filePath.getName());
                Files.copy(filePath.toPath(), path.toPath());
            }
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
        difficulty = difficultyChoices.getValue().toString();
    }

    public void exitPage(ActionEvent actionEvent) {
        question.getScene().getWindow().hide();
    }
}
