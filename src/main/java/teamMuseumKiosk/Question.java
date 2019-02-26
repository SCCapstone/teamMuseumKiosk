package teamMuseumKiosk;

import java.util.List;

public class Question {
    private String prompt;
    private String correct;
    private List<String> questionAnswers;
    private int Difficulty;


    public Question(String prompt, List<String> questionAnswers,String difficulty) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
        setDifficulty(Integer.parseInt(difficulty));
    }

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

