package controllers;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.geometry.Rectangle2D;
import javafx.stage.*;
import javafx.stage.Popup;
import javafx.util.Duration;
import teamMuseumKiosk.HighScore;
import teamMuseumKiosk.LoadScene;
import teamMuseumKiosk.User;

import javax.swing.*;
import java.io.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StartController extends Thread implements LoadScene, Initializable {
    @FXML
    private TextField name, email;
    
    @FXML
    private Text missingInfoText;

    private Stage stage;
    private Thread t;
    //private String lettersTyped;
    private TextField textField;
    private Stage keyboardStage = new Stage();
    private KeyboardController keyboard;
    private String temp;
    private final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    public void setStage(Stage stage) { this.stage = stage; }
    public void setTimer(){
        //Automatically goes back to splash screen after 2 minutes
        //timerToSplashScene(stage,2);
    }
    String settings = "settings.txt";
    private int i;
    @FXML
    Label highscoreText, high1, high2, high3, high4, high5, high6, high7, high8, high9, high10,offset;


    public void buttonClick(ActionEvent actionEvent) throws IOException {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("TriviaQuestions.csv"));
        } catch (IOException e)
        {
            missingInfoText.setText("'TriviaQuestions.csv' file is not in the directory. Please add it before continuing.");
            return;
        }

        if(name.getText().trim().isEmpty() || email.getText().trim().isEmpty()){
	            missingInfoText.setText("Please enter your initials and email");
	            return;
	    }

        if(name.getText().trim().length() > 3) {
	            missingInfoText.setText("Initials cannot be longer than 3 characters");
	            return;
	    }

        String userEmail = email.getText().toLowerCase();

        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");

        if(!emailRegex.matcher(userEmail).matches()) {
            missingInfoText.setText("Please enter a valid email address");
            return;
        }

        User newUser = new User(name.getText().toUpperCase().trim(), 0, email.getText().trim());
        loadQuestionScene(this.stage, newUser);
    }

    public void goToAdminPage(ActionEvent actionEvent) throws IOException {
        URL url = new URL(getClass().getResource("/design/adminLoginScreen.fxml").toExternalForm());

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminLoginController controller = loader.getController();
        loader.setController(controller);

        Scene scene = new Scene(root, 1440,900);
        this.stage.setScene(scene);
        this.stage.show();

//        if(controller.image != null){
//            image = controller.image;
//            imageView.setImage(new Image(controller.image.toExternalForm()));
//        }
    }

    public void nameClick(){
        this.textField = name;
        if(keyboardStage.isShowing()){keyboardStage.close();}
        showKeyboard();
        getTypedLetters();
    }
    public void emailClick(){
        this.textField = email;
        if(keyboardStage.isShowing()){keyboardStage.close();}
        showKeyboard();
        getTypedLetters();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set high scores
        highscoreText.setText("High Scores:");
        offset.setText("");
        List<String> settingsList = null;
        try {
            settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : settingsList)
        {
            if (line.contains("highScores"))
            {
                String[] asdf = line.split(" ");
                String highscoreTime = asdf[1];
                HighScore highscore = new HighScore();
                if (highscoreTime.equals("daily"))
                {
                    highscoreText.setText("Today's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(0);
                    setHighScores(scores);
                }
                else if (highscoreTime.equals("weekly"))
                {
                    highscoreText.setText("This Week's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(1);
                    setHighScores(scores);
                }
                else if (highscoreTime.equals("monthly"))
                {
                    highscoreText.setText("This Month's High Scores:");
                    ArrayList<String> scores = highscore.getHighScores().get(2);
                    setHighScores(scores);
                }
                else //rotate
                {
                    //display on rotation
                    AnimationTimer timer = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            HighScore highscore = new HighScore();
                            if (i%1500 > 0 && i%1500 < 500)
                            {
                                highscoreText.setText("This Month's High Scores:");
                                ArrayList<String> scores = highscore.getHighScores().get(2);
                                setHighScores(scores);
                            }
                            else if (i%1500 > 500 && i%1500 < 1000)
                            {
                                highscoreText.setText("This Week's High Scores:");
                                ArrayList<String> scores = highscore.getHighScores().get(1);
                                setHighScores(scores);
                            }
                            else if (i%1500 > 1000 && i%1500 < 1499)
                            {
                                highscoreText.setText("Today's High Scores:");
                                ArrayList<String> scores = highscore.getHighScores().get(0);
                                setHighScores(scores);
                            }
                            i++;
                        }
                    };
                    timer.start();
                }
            }
        }

        // Make name input be uppercase
        this.name.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        // Restricting name initials to length 3
        this.name.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null)
                    return;
                if (!newValue.matches("\\sa-zA-Z*"))
                    name.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                if (newValue.length() > 3) {
                    name.setText(newValue.substring(0, 3));
                }
            }
        });

        try {
            URL url = new URL(getClass().getResource("/design/keyboard.fxml").toExternalForm());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            keyboard = loader.getController();
            loader.setController(keyboard);
            Scene scene = new Scene(root,1130,353);


            keyboard.setStage(keyboardStage);

            if(stage!=null)keyboardStage.initOwner(stage);

            keyboardStage.initModality(Modality.NONE);
            //keyboardStage.initStyle(StageStyle.UNDECORATED);
            keyboardStage.setTitle("Keyboard");
            keyboardStage.setResizable(false);
            keyboardStage.setScene(scene);
            //fulsize screen for every screen
            keyboardStage.setX(primaryScreenBounds.getWidth()-primaryScreenBounds.getMaxX()/1.30);
            keyboardStage.setY(primaryScreenBounds.getHeight()-primaryScreenBounds.getMaxY()/2.5);
        }catch (IOException e){}
        this.name.setOnMouseClicked(event -> nameClick());
        this.email.setOnMouseClicked(event -> emailClick());

    }

    private void setHighScores(ArrayList<String> scores) {
        if (scores.size() >= 1)
        {
            high1.setText(scores.get(0));
            if (scores.size() >= 2)
            {
                high2.setText(scores.get(1));
                if (scores.size() >= 3)
                {
                    high3.setText(scores.get(2));
                    if (scores.size() >= 4)
                    {
                        high4.setText(scores.get(3));
                        if (scores.size() >= 5)
                        {
                            high5.setText(scores.get(4));
                            if (scores.size() >= 6)
                            {
                                high6.setText(scores.get(5));
                                if (scores.size() >= 7)
                                {
                                    high7.setText(scores.get(6));
                                    if (scores.size() >= 8)
                                    {
                                        high8.setText(scores.get(7));
                                        if (scores.size() >= 9)
                                        {
                                            high9.setText(scores.get(8));
                                            if (scores.size() >= 10)
                                            {
                                                high10.setText(scores.get(9));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void showKeyboard() {
            keyboardStage.show();
            keyboard.start();

    }
    public void closeKeyboard(){
        //making sure when the keyboard closes the letters dont go away
        if (!keyboard.getNameArray().isEmpty()){
            textField.setText(String.join("",keyboard.getNameArray()));
        }
        //closing the keyboard
        if(keyboardStage.isShowing()){keyboardStage.close();}
        keyboard.clearNameArray();
    }

    private void getTypedLetters(){
        //creates a new thread so the program isnt halted when waiting
        t = new Thread(this,"t2");
        t.setDaemon(true);
        t.start();

    }public void stopThread(){
        t = null;
    }

    @Override
    public void run() {
        temp = "";
        Thread thread = Thread.currentThread();
        while (t == thread) {
            //must have so we can acctually get info
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}
            //checking to see if there are any new keys pressed to add
            if (keyboard.getType() == true) {

                temp = keyboard.getTyped();
                if(textField.getId() == "nameField" && keyboard.getNameArray().size() == 3 ){
                    System.out.println("hello");
                    keyboard.deleteLastNameArray(keyboard.getNameArray().size()-1);
                    continue;
                }
                //checking to see if enter has been pressed
                if(temp.toLowerCase().contains("enter")){

                    //this is magic. thread cant close the keyboard so the main process has to close it
                    Platform.runLater(this::closeKeyboard);
                    //setting the enter to nothing
                    keyboard.setTyped("");
                    temp ="";
                    //deleting the enter that came through from keyboard controller
                    keyboard.deleteLastNameArray(keyboard.getNameArray().size()-1);
                    //stopping both threads
                    keyboard.stopThread();
                    stopThread();

                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
                //lettersTyped = temp;
                //adding to the text field
                if(!keyboard.getNameArray().isEmpty()) {
                    textField.setText(String.join("", keyboard.getNameArray()));

                    }
                }
            }
        }
    }




