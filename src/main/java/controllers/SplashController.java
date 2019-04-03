package controllers;

import com.sun.jmx.snmp.tasks.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import teamMuseumKiosk.LoadScene;
import javafx.scene.image.ImageView ;
import javafx.scene.image.Image ;
import javafx.scene.image.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SplashController extends Thread implements LoadScene {
    @FXML
    private ImageView image = new ImageView();
    private ArrayList<Image> ads = new ArrayList<>();
    private Thread t;

    public void  initialize(){
        try {
            loadAdvertisment();
            slideShow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buttonClick(ActionEvent actionEvent) throws IOException {
        stopThread();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        loadStartScene(stage);
    }

    private void loadAdvertisment() throws IOException{
       File path = new File("src/main/resources/images/Advertisments/");
       //creates an the total path for the file
       String absPath = path.getAbsolutePath();
        //creates file array for the ads directory and array list for the ads
        File[] imgs = new File(absPath).listFiles();
        //goes through each file puts it in the list
        if (imgs != null) {
            for (File file : imgs) {
                if (file.isFile()) {
                    //changeing the file to an image
                    Image temp = new Image(file.toURI().toURL().toString());
                    ads.add(temp);
                }
            }
        }

    }

    private void slideShow() {
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
        int i = 0;
        Thread thread = Thread.currentThread();
        //does the slideshow until clicked
        while (t == thread){
            image.setImage(ads.get(i));
            //image.setManaged(true);
            //image.setVisible(true);
            try {
                //the amount of time the pictrues stay
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("inturpted");
            }
            i++;
            if (i >= ads.size()) i = 0;

        }
    }

}

