package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.Question;
import main.User;

import java.io.*;
import java.net.URL;
import java.util.*;

public class QuestionController implements Initializable {
    public User user;
    private ArrayList<Question> questions;
    private Question currentQuestion;
    private int scoreValue, num, questionNumber, strikesNum;
    @FXML
    private Label prompt, score, questionNum, strikes;
    @FXML
    private Button button1, button2, button3, button4;

    public QuestionController() {}

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.num = 0;
            this.strikesNum = 0;
            this.scoreValue = 0;
            this.questionNumber = 1;
            this.questions = loadData("/TriviaQuestions.csv");
            newQuestion(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newQuestion(ActionEvent event) throws IOException {
        if (num < questions.size() && strikesNum < 3) {
            currentQuestion = retrieveNextQuestion();
            displayQuestion(currentQuestion);
            setButtons(currentQuestion, button1, button2, button3, button4);
            questionNum.setText("Question " + String.valueOf(questionNumber++));
        } else {
            user.setScore(scoreValue);
            quizEnd(event);
        }
        num++;
    }

    public void displayQuestion(Question question) {
        if (questions != null) {
            prompt.setText(question.getPrompt());
        }
    }

    public void setButtons(Question question, Button button1, Button button2, Button button3, Button button4) {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        Collections.shuffle(question.getQuestionAnswers()); //scramble up that order
        for(Button b : buttons) {
            b.setText(question.getQuestionAnswers().get(buttons.indexOf(b)));
        }

    }

    public void buttonClick(ActionEvent event) throws IOException {
        //get text of button
        Button button = (Button) event.getSource();
        String text = button.getText();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(text.equals("Quit")) {
            quizEnd(event);
        }
        //if text of button matches correct answer of question, increases user's score and goes to next question
        else if (text.equals(currentQuestion.getCorrect())) {
            //alert user that they got it correct
            showPopupWindow(stage, "Correct!");

            scoreValue++;
            score.setText("Score: " + scoreValue);

            //go to next question
            newQuestion(event);

        } else { //if not correct answer, does not increase score but continues to next question
            //alert user that they got it wrong
            showPopupWindow(stage, "Incorrect!");

            strikesNum++;
            if (strikesNum == 1) {
                strikes.setText("Strikes: X");
            }
            else if (strikesNum == 2) {
                strikes.setText("Strikes: XX");
            }
            score.setText("Score: " + scoreValue);

            //go to next question
            newQuestion(event);
        }
    }

    private ArrayList<Question> loadData(String fileName) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //load file
            InputStream stream = QuestionController.class.getResourceAsStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String line;
            List<String> data;
            //get each question from file line
            while ((line = bufferedReader.readLine()) != null) {
                data = Arrays.asList(line.split(","));
                Question question = new Question(data.get(0), data.subList(1,5));
                questions.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    private Question retrieveNextQuestion() {
        return questions.get(num);
    }

    private void quizEnd(ActionEvent event) throws IOException {
        //go to end screen
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        URL url = new URL(getClass().getResource("/design/endscreen.fxml").toExternalForm());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        EndController controller = loader.getController();
        controller.setUser(user);
        controller.setText();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        stage.setScene(scene);
        stage.show();
    }

    private void showPopupWindow(Stage stage, String text) {
        try {
            URL url = new URL(getClass().getResource("/design/popup.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // initializing the controller
            PopupController popupController = loader.getController();
            loader.setController(popupController);

            Scene scene = new Scene(root);
            Stage popupStage = new Stage();

            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            popupController.setStage(popupStage);
            //Set text to say correct or incorrect
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