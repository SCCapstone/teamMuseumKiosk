package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public boolean click, type, shift = false;
    private ArrayList<String> nameArray = new ArrayList<>();
    @FXML
    private Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;

    public void start(){
        //starting the thread
        getText();

    }
    public String getLetter(){
        click = false;
        return letter;
    }
    public String getTyped(){type = false;return Typed;}

    public void setTyped(String a){Typed = a;}

    public ArrayList<String> getNameArray(){return nameArray;}

    public void deleteLastNameArray(int i){nameArray.remove(i);}
    public void clearNameArray(){nameArray.clear();}

    public boolean getType(){return type;}

    public boolean getClick(){return click;}

    public void buttonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        //if the shift button is pressed then switch the important keys
        //TODO a touppercase and tolowercase with the shift
        if(button.getText().contains("Shift") && !shift){
            b0.setText("!");
            b1.setText("@");
            b2.setText("#");
            b3.setText("$");
            b4.setText("%");
            b5.setText("^");
            b6.setText("&");
            b7.setText("*");
            b8.setText("(");
            b9.setText(")");
            b10.setText("_");
            b11.setText("+");
            shift = true;
            result = "";
            //sets the buttons back to the way they were
        }else if (button.getText().contains("Shift")&& shift){
            b0.setText("1");
            b1.setText("2");
            b2.setText("3");
            b3.setText("4");
            b4.setText("5");
            b5.setText("6");
            b6.setText("7");
            b7.setText("8");
            b8.setText("9");
            b9.setText("0");
            b10.setText("-");
            b11.setText("=");
            shift = false;
            result = "";
        }
        //getting the button text to decide what letter to write
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
        if(!nameArray.isEmpty() && nameArray.get(nameArray.size()-1).contains("Enter")){
            nameArray.remove(nameArray.size()-1);

        }
        //checks the keyboard
        while (t == thread){
            if (this.getClick()){//if there has been a button clicked
                //setting the letter to another vairiable
                result = this.getLetter();
                /*try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    System.out.println(e);}*/
                if(result.contains("Enter")){//if enter is hit start controller takes care of that
                    //stopThread();
                    //result = "";
                }else if(result.contains("Shift")){// shift is taken care of at the top
                    //set to empty string to take it out later
                    result = "";

                }else if(result.contains("Back")){//if back it removes the last string from the array
                    if(nameArray.size()>0) {
                        nameArray.remove(nameArray.size() - 1);
                    }
                    //result to empty agin
                        result = "";

                }

                //putting the final result in the array

                if(result != ""){nameArray.add(result);}
                //putting everything in the array list into a complete string
                Typed = String.join("",nameArray);
                //setting type to true so we know the program has more letters to display
                type = true;
            }
            //sleeping to not break shit
            try {
                Thread.sleep(10);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }


}
