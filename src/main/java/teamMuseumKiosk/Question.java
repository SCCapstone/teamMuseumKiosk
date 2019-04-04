package teamMuseumKiosk;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Question {
    private String prompt;
    private String correct;
    private List<String> questionAnswers;
    private int Difficulty;
    private Image Img;

    public Question(String prompt, List<String> questionAnswers,String difficulty) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
        setDifficulty(Integer.parseInt(difficulty));
    }
    public Question(String prompt, List<String> questionAnswers,String difficulty, String img) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
        setDifficulty(Integer.parseInt(difficulty));
        setImg(img);
    }

    public  Image getImg(){return Img;}

    public void  setImg(String img) { Img = new Image(img); }

    public  int getDifficulty() {return Difficulty;}

    public void setDifficulty(int difficulty) { Difficulty = difficulty; }

    public String getPrompt() { return prompt; }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public List<String> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<String> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}

