package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewQuestionsController extends AdminController implements Initializable {

    private final TableView<Question> tableView = new TableView<>();

    @FXML
    private Group questionGroup;

    private final ObservableList<Question> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn columnQuestion = new TableColumn("Question");
        columnQuestion.setCellValueFactory(new PropertyValueFactory<>("prompt"));

        TableColumn columnAnswer1 = new TableColumn("Answer 1");
        columnAnswer1.setCellValueFactory(new PropertyValueFactory<>("answer1"));

        TableColumn columnAnswer2 = new TableColumn("Answer 2");
        columnAnswer2.setCellValueFactory(new PropertyValueFactory<>("answer2"));

        TableColumn columnAnswer3 = new TableColumn("Answer 3");
        columnAnswer3.setCellValueFactory(new PropertyValueFactory<>("answer3"));

        TableColumn columnCorrectAnswer = new TableColumn("Correct Answer");
        columnCorrectAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        TableColumn columnDifficulty = new TableColumn("Difficulty");
        columnDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        tableView.setItems(dataList);
        tableView.getColumns().addAll(
                columnQuestion, columnAnswer1, columnAnswer2, columnAnswer3, columnCorrectAnswer, columnDifficulty);

        // table view size
        tableView.setMinWidth(1200);
        tableView.setMinHeight(600);


        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.getChildren().add(tableView);

        questionGroup.getChildren().add(vBox);

        // getting questions from csv file
        readCSV();

        // grabbing user's selected question from table
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = tableView.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                int questionRow = tablePosition.getRow();

                try {
                    goToEditQuestion(questionRow);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //  for displaying list of questions
    public class Question {
        // each question has prompt, 4 answers (last answer is correct one), and difficulty
        private SimpleStringProperty prompt, answer1, answer2, answer3, correctAnswer, difficulty;

        public String getPrompt() { return prompt.get(); }
        public String getAnswer1() { return answer1.get(); }
        public String getAnswer2() { return answer2.get(); }
        public String getAnswer3() { return answer3.get(); }
        public String getCorrectAnswer() { return correctAnswer.get(); }
        public String getDifficulty() { return difficulty.get(); }

        Question(String prompt, String answer1, String answer2, String answer3,
                 String correctAnswer, String difficulty) {
            this.prompt = new SimpleStringProperty(prompt);
            this.answer1 = new SimpleStringProperty(answer1);
            this.answer2 = new SimpleStringProperty(answer2);
            this.answer3 = new SimpleStringProperty(answer3);
            this.correctAnswer = new SimpleStringProperty(correctAnswer);
            this.difficulty = new SimpleStringProperty(difficulty);
        }
    }

    private void goToEditQuestion (int questionRow) throws IOException {

        // grabbing each component of the question
        String questionTemp = dataList.get(questionRow).prompt.getValue();
        String answer1Temp = dataList.get(questionRow).answer1.getValue();
        String answer2Temp = dataList.get(questionRow).answer2.getValue();
        String answer3Temp = dataList.get(questionRow).answer3.getValue();
        String correctAnswerTemp = dataList.get(questionRow).correctAnswer.getValue();
        String difficultyTemp = dataList.get(questionRow).difficulty.getValue();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        URL url = new URL(getClass().getResource("/design/editQuestionScreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        EditQuestionController controller = loader.getController();
        loader.setController(controller);

        // passing to edit question controller
        controller.setOrigQuestion(questionTemp);
        controller.setOrigAnswer1(answer1Temp);
        controller.setOrigAnswer2(answer2Temp);
        controller.setOrigAnswer3(answer3Temp);
        controller.setOrigCorrect(correctAnswerTemp);
        controller.setOrigDifficulty(difficultyTemp);

        Scene scene = new Scene(root, 600,485);
        stage.setScene(scene);
        stage.show();

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

                Question question = new Question(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                dataList.add(question);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminUpdateController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminUpdateController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

}