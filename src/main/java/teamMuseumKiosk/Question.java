package teamMuseumKiosk;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            //setVideo(file);
        }
        else {
            setImg(file);
        }
    }

    public  Image getImg(){return Img;}

    public void  setImg(String img) { Img = new Image(img); }

    public Media getVideo() {return video;}

    public void setVideo(String file) {
        String url = getClass().getResource(file).toExternalForm();
        video = new Media(url);
    }

    public  int getDifficulty() {return Difficulty;}

    public void setDifficulty(int difficulty) { Difficulty = difficulty; }

    public String getPrompt() { return prompt; }

    public void setPrompt(String prompt) {
        if (prompt.contains("`"))
        {
            String temp = prompt;
            List<String> data = Arrays.asList(temp.split("`"));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + ",";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            prompt = formatted;
        }
        this.prompt = prompt;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        if (correct.contains("`"))
        {
            String temp = correct;
            List<String> data = Arrays.asList(temp.split("`"));
            String formatted = null;
            for (String x : data)
            {
                formatted = formatted + x + ",";
            }
            formatted = formatted.substring(4,formatted.length() - 1);
            correct = formatted;
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
                String temp = ans;
                List<String> data = Arrays.asList(temp.split("`"));
                String formatted = null;
                for (String x : data)
                {
                    formatted = formatted + x + ",";
                }
                formatted = formatted.substring(4,formatted.length() - 1);
                ans = formatted;
            }
            formattedAnswers.add(ans);
        }
        this.questionAnswers = formattedAnswers;
    }
}

