package controllers;

import java.awt.*;
import java.awt.event.ActionEvent;

public class KeyboardController {
    private String[] keyboardArray = new String[]{"0","1","2","3","4","5","6","7","8","9","-","=","back","q","w","e","r","t","y","u",

            "i","o","p","[","]","back slash","a","s","d","f","g","h","j","k","l",";","'","enter","z","x","c","v","b","n","m",

            ",",".","/","shift"};
    //"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    //"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",};

    public void setButtons(){





    }
    public void buttonClick(ActionEvent event){
        String temp = null;
        Button button = (Button) event.getSource();
        String butText = button.getName();
        String[] parse = butText.split("");
        for (int i = 0; i <parse.length;i++){
            if(parse[i] != "b") {
                temp += parse[i];
            }
        }
        int buttonResult = Integer.parseInt(temp);
        String result = keyboardArray[buttonResult];
        if (result == "back" || result == "enter" || result =="backslash" || result == "shift"){
            if (result == "back") System.out.println(result);
            if (result == "enter") System.out.println(result);
            if (result =="backslash") System.out.println(result);
            if (result == "shift") System.out.println(result);

        }
        else System.out.println(result);
    }
}
