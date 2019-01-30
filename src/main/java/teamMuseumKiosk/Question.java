package teamMuseumKiosk;

import java.util.List;

public class Question {
    private String prompt;
    private String correct;
    private List<String> questionAnswers;


    public Question(String prompt, List<String> questionAnswers) {
        setPrompt(prompt);
        setQuestionAnswers(questionAnswers);
        String temp = questionAnswers.get(3);
        setCorrect(temp);
    }

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

