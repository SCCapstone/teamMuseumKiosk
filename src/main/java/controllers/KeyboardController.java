package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KeyboardController extends Thread {

    private Stage stage;
    private Thread t;
    public String letter, Typed,result;
    public boolean click, type = false;

    public void start(){
        getText();
        if (result == "enter"){stopThread();}

    }
    public String getLetter(){
        click = false;
        return letter;
    }
    public String getTyped(){type = false;return Typed;}

    public boolean getType(){return type;}

    public boolean getClick(){return click;}

    public void buttonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        letter = button.getText();
        click = true;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        try {
            if (stage != null)
                stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getText(){
        //creates a new thread so the program isnt halted when waiting
        t = new Thread(this,"t1");
        t.setDaemon(true);
        t.start();

    }
    public void stopThread(){
        t = null;
    }
    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        ArrayList<String> nameArray = new ArrayList<>();
        //checks the keyboard
        while (t == thread){
            if (this.getClick()){
                //System.out.println(letter);
                result = this.getLetter();
                nameArray.add(result);
                Typed = String.join("",nameArray);
                type = true;
                //System.out.println(Typed);
            }

            try {
                Thread.sleep(10);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }


}
