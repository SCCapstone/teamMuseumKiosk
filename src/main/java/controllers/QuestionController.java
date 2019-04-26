package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.Question;
import teamMuseumKiosk.ResetAdminSettings;
import teamMuseumKiosk.User;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionController implements Initializable, LoadScene, ResetAdminSettings {
    public User user;
    private Stage stage;
    private ArrayList<Question> questions;
    private Question currentQuestion;
    private boolean correct = false;
    private int scoreValue, num, questionNumber, strikesNum, maxStrikes, maxQuestions;
    @FXML
    private Label prompt, score, questionNum;
    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private ImageView img, strike1, strike2, strike3;
    @FXML
    private GridPane quizButtons;
    @FXML
    private MediaView mediaView;

    @FXML
    private Button nextQuestion;

    private static String strikeImg= "/images/strike.png";

    public void setUser(User user) {
        this.user = user;
    }
    public void setStage(Stage stage) {this.stage = stage; }
    public void setTimer() { timerToSplashScene(this.stage,6);}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            this.questions = loadData("TriviaQuestions.csv");
            this.num = questions.size();
            this.strikesNum = 0;
            this.scoreValue = 0;
            this.questionNumber = 1;
            this.mediaView.setManaged(false);
            this.img.setManaged((false));
            //Checks for max strikes and questions
            List<String> settingsList = Files.lines(Paths.get("settings.txt")).collect(Collectors.toList());
            for(String line: settingsList){

                if (line.contains("strikeNum")) {
                    String[] strikes = line.split(" ");
                    // check if settings.txt is incomplete
                    if (strikes.length <= 1) {
                        System.out.println("HIIIIIII");
                        ifSettingsFileIsEmpty("strikeNum");
                        // setting strikeNum as default 3
                        maxStrikes = 3;
                    }
                    else if(line.equals("strikeNum: 2"))
                        maxStrikes = 2;
                    else if(line.equals("strikeNum: 3"))
                        maxStrikes = 3;
                }
                else if(line.contains("maxQuestions"))
                {
                    String[] words = line.split(" ");

                    // check if settings.txt is incomplete
                    if (words.length <= 1) {
                        // missing number of max questions
                        ifSettingsFileIsEmpty("maxQuestions");
                        // setting maxQuestions as default 10
                        maxQuestions = 10;
                    }
                    else {
                        maxQuestions = Integer.parseInt(words[1]);
                    }
                }
            }

            //Loads first question
            newQuestion(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newQuestion(ActionEvent event) throws IOException {
        if (num > 0 && strikesNum < maxStrikes && questionNumber <= maxQuestions) {
            currentQuestion = retrieveNextQuestion();
            displayQuestion(currentQuestion);
            setPicture(currentQuestion);

            // enable answer buttons
            button1.setDisable(false);
            button2.setDisable(false);
            button3.setDisable(false);
            button4.setDisable(false);

            // set Next Question button to invisible
            nextQuestion.setVisible(false);

            setButtons(currentQuestion, button1, button2, button3, button4);
            questionNum.setText("Question " + (questionNumber++));
        } else {
            user.setScore(scoreValue);
            quizEnd(event);
        }

        questions.remove(currentQuestion);
        num--;
    }

    public void displayQuestion(Question question) {
        if (questions != null) {
            prompt.setText(question.getPrompt());
            prompt.setWrapText(true);
            prompt.setTextAlignment(TextAlignment.JUSTIFY);;
        }
    }

    private void setPicture(Question currentQuestion) {
        if(currentQuestion.getImg()!= null) {
            img.setImage(currentQuestion.getImg());
            quizButtons.getRowConstraints().get(0).setPrefHeight(255);
            img.setManaged(true);
            img.setVisible(true);
        }else {
            quizButtons.getRowConstraints().get(0).setPrefHeight(0);
            img.setVisible(false);
            img.setManaged(false);
        }

        if(currentQuestion.getVideo() != null) {
            MediaPlayer player = new MediaPlayer(currentQuestion.getVideo());
            mediaView.setMediaPlayer(player);
            player.play();
        }
    }

    public void setButtons(Question question, Button button1, Button button2, Button button3, Button button4) {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        //scrambles order
        Collections.shuffle(question.getQuestionAnswers());
        //sets text of each button
        for(Button b : buttons) {
            b.setText(question.getQuestionAnswers().get(buttons.indexOf(b)));
        }
    }

    public void buttonClick(ActionEvent event) throws IOException {
        if(mediaView.getMediaPlayer() != null) {
            mediaView.getMediaPlayer().dispose();
        }

        //get text of button
        Button button = (Button) event.getSource();
        String text = button.getText();
        //get stage
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(text.equals("Quit")) {
            user.setScore(scoreValue);
            quizEnd(event);
        }
        //if text of button matches correct answer of question, increases user's score and displays check mark
        else if (text.equals(currentQuestion.getCorrect())) {
            //alert user that they got it correct
            correct = true;
            String url = getClass().getResource("/audio/ding.wav").toExternalForm();
            AudioClip correctSound = new AudioClip(url);
            correctSound.play();
            img.setImage(new Image("/images/check.png"));
            quizButtons.getRowConstraints().get(0).setPrefHeight(255);
            img.setManaged(true);
            img.setVisible(true);

            scoreValue++;
            score.setText("Score: " + scoreValue);

        } else {
            //if not correct answer, does not increase score, displays red X
            //TODO: make correct button font color green, and currently selected button as red
            img.setImage(new Image("/images/Red_X.png"));
            quizButtons.getRowConstraints().get(0).setPrefHeight(255);
            img.setManaged(true);
            img.setVisible(true);

            //add strike
            strikesNum++;
            addStrike();

            //sets overall score
            score.setText("Score: " + scoreValue);

        }

        // disables answer buttons so user cannot change answer before going to the next question
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);

        // display Next Question button
        nextQuestion.setVisible(true);

        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    stage.getScene().removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
                    newQuestion(event);
                }
                catch(IOException error) {}
            }
        });
    }

    private void addStrike() {
        if (strikesNum == 1) {
            strike1.setImage(new Image(strikeImg));
            strike1.isVisible();
        } else if(strikesNum == 2) {
            strike2.setImage(new Image((strikeImg)));
            strike2.isVisible();
        } else {
            strike3.setImage(new Image(strikeImg));
            strike3.isVisible();
        }
    }

    private ArrayList<Question> loadData(String fileName) throws IOException{
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //Opens TriviaQuestions.csv and filters out any empty lines
            Stream<String> stream = Files.lines(Paths.get(fileName));
            List<String> lines = stream.filter(line -> !line.equals(""))
                    .collect(Collectors.toList());

            List<String> data;
            for(String line : lines) {
                data = Arrays.asList(line.split(","));
                if(data.size() == 6) {
                    Question question = new Question(data.get(0), data.subList(1, 5), data.get(5));
                    questions.add(question);
                }else if (data.size() == 7){
                    Question question = new Question(data.get(0), data.subList(1, 5), data.get(5),data.get(6));
                    questions.add(question);
                }
            }

        } catch (Exception e) {
            Stage stage = new Stage();
            showPopupWindow(stage, e.toString());
            e.printStackTrace();
            loadStartScene(stage);
        }

        return questions;
    }

    private Question retrieveNextQuestion() {
        //sets the question based on the difficulty
        //grabs random question of the difficulty selected
        Random random = new Random();
        int diff = 0;
        if (currentQuestion != null)
        {
            diff = currentQuestion.getDifficulty();
        }
        ArrayList<Question> possibleQuestions = new ArrayList<>();
        if (diff == 0)
        {
            for (int i=0;i<questions.size();i++)
            {
                //get questions with one difficulty
                if (questions.get(i).getDifficulty() == 1)
                {
                    possibleQuestions.add(questions.get(i));
                }
            }
            int newQ = random.nextInt(possibleQuestions.size());
            return possibleQuestions.get(newQ);
        }
        if (correct)
        {
            //add a chance to get same difficulty again
            int num = random.nextInt(3);
            if (num == 0)
            {
                for (int i=0;i<questions.size();i++)
                {
                    //get questions with one greater difficulty
                    if (questions.get(i).getDifficulty() == diff+1)
                    {
                        possibleQuestions.add(questions.get(i));
                    }
                }
                if (possibleQuestions.size() == 0)
                {
                    for (int i=0;i<questions.size();i++)
                    {
                        //get questions with same difficulty
                        if (questions.get(i).getDifficulty() == diff)
                        {
                            possibleQuestions.add(questions.get(i));
                        }
                    }

                }
                int newQ = random.nextInt(possibleQuestions.size());
                return possibleQuestions.get(newQ);
            }
            else
            {
                //same difficulty
                for (int i=0;i<questions.size();i++)
                {
                    //get questions with one greater difficulty
                    if (questions.get(i).getDifficulty() == diff)
                    {
                        possibleQuestions.add(questions.get(i));
                    }
                }
                if (possibleQuestions.size() == 0)
                {
                    for (int i=0;i<questions.size();i++)
                    {
                        //get questions with one greater difficulty
                        if (questions.get(i).getDifficulty() == diff-1)
                        {
                            possibleQuestions.add(questions.get(i));
                        }
                    }

                }
                int newQ = random.nextInt(possibleQuestions.size());
                return possibleQuestions.get(newQ);
            }

        }
        else
        {
            if (diff > 1)
            {
                for (int i=0;i<questions.size();i++)
                {
                    //get questions with one less difficulty
                    if (questions.get(i).getDifficulty() == diff-1)
                    {
                        possibleQuestions.add(questions.get(i));
                    }
                }
                int newQ = random.nextInt(possibleQuestions.size());
                return possibleQuestions.get(newQ);
            }
            else
            {
                for (int i=0;i<questions.size();i++)
                {
                    //get questions with one difficulty
                    if (questions.get(i).getDifficulty() == 1)
                    {
                        possibleQuestions.add(questions.get(i));
                    }
                }
                int newQ = random.nextInt(possibleQuestions.size());
                return possibleQuestions.get(newQ);
            }

        }

    }

    private void quizEnd(ActionEvent event) throws IOException {
        //go to end screen
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        loadEndScene(stage, this.user);
    }

    private void showPopupWindow(Stage stage, String text) throws IOException { loadPopupScene(stage, text); }
}