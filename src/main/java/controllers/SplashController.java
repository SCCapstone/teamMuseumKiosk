package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import teamMuseumKiosk.LoadScene;
import javafx.scene.image.Image ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SplashController extends Thread implements LoadScene, Initializable {
    @FXML
    private ImageView image = new ImageView();
    private ArrayList<Image> ads = new ArrayList<>();
    private Thread t;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            loadAdvertisement();
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

    private void loadAdvertisement() throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("images/Advertisements/NationalParksAdventure.jpg");
        BufferedImage bufImage = ImageIO.read(input);
        int x = bufImage.getMinX(),y = bufImage.getMinY(),width=bufImage.getWidth(),height = bufImage.getHeight();
        BufferedImage tempBuf = bufImage.getSubimage(x,y,width,height);
        Image image = SwingFXUtils.toFXImage(tempBuf,null);
        ads.add(image);

        /*File path = new File("src/main/resources/images/Advertisements/");
        //creates an the total path for the file
        String absPath = path.getAbsolutePath();
        //creates file array for the ads directory and array list for the ads
        File[] imgs = new File(absPath).listFiles();
        //goes through each file puts it in the list
        if (imgs!= null) {
            for (File file : imgs) {
                if (file.isFile()) {
                    //changing the file to an image
                    Image temp = new Image(file.toURI().toURL().toString());
                    ads.add(temp);
                }
            }
        }*/

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
                //the amount of time the pictures stay
                Thread.sleep(3000);
            }catch (InterruptedException e){
                System.out.println("Interrupted");
            }
            i++;
            if (i >= ads.size()) i = 0;

        }
    }
}

