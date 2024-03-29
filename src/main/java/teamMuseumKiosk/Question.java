package teamMuseumKiosk;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {
    private String prompt;
    private String correct;
    private List<String> questionAnswers;
    private int Difficulty;
    private Image Img;
    private Media video;

    public Question(String prompt, List<String> questionAnswers,String difficulty) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
        setDifficulty(Integer.parseInt(difficulty));
    }
    public Question(String prompt, List<String> questionAnswers,String difficulty, String file) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
        setDifficulty(Integer.parseInt(difficulty));
        if(file.contains("mp4") || file.contains("wav")) {
            try {
                setVideo(file);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                setImg(file);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public  Image getImg(){return Img;}

    public void  setImg(String img) throws MalformedURLException {
        File file = new File(img);
        if (file.isFile())
        {
            Image temp = new Image(file.toURI().toURL().toString());
            Img = temp;
        }
    }

    public Media getVideo() {return video;}

    public void setVideo(String vid) throws MalformedURLException {
        File file = new File(vid);
        if (file.isFile())
        {
            Media temp = new Media(file.toURI().toURL().toString());
            video = temp;
        }
    }

    public  int getDifficulty() {return Difficulty;}

    public void setDifficulty(int difficulty) { Difficulty = difficulty; }

    public String getPrompt() { return prompt; }

    public void setPrompt(String prompt) {
        if (prompt.contains("`"))
        {
            this.prompt = splitFormatter(prompt);
        }
        this.prompt = prompt;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        if (correct.contains("`"))
        {
            this.correct = splitFormatter(correct);
        }
        this.correct = correct;
    }

    public List<String> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<String> questionAnswers) {
        List<String> formattedAnswers = new ArrayList<>();
        for (String ans : questionAnswers)
        {
            if (ans.contains("`"))
            {
                ans = splitFormatter(ans);
            }
            formattedAnswers.add(ans);
        }
        this.questionAnswers = formattedAnswers;
    }

    private String splitFormatter(String ans){
        String temp = ans;
        List<String> data = Arrays.asList(temp.split("`"));
        String formatted = null;
        for (String x : data)
        {
            formatted = formatted + x + ",";
        }
        formatted = formatted.substring(4,formatted.length() - 1);
        return formatted;
    }

    @Override
    public String toString() {
        return this.getPrompt() + ", " + this.getCorrect() + ", " + this.getQuestionAnswers().toString() + ", " + this.getDifficulty();
    }

}

