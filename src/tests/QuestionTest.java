package tests;

import main.Question;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {
    String prompt;
    List<String> answers;
    Question question;

    @Before
    public void initialize() {
        prompt = "This is a test question";
        answers = new ArrayList<>();
        answers.add("answer 1");
        answers.add("answer 2");
        answers.add("answer 3");
        answers.add("answer 4");
        question = new Question(prompt, answers);
    }

    @Test
    public void getPrompt() {
        Assert.assertEquals("This is a test question", question.getPrompt());
    }

    @Test
    public void setPrompt() {
        question.setPrompt("I changed the prompt");
        Assert.assertEquals("I changed the prompt", question.getPrompt());
    }

    @Test
    public void getCorrect() {
        Assert.assertEquals("answer 4", question.getCorrect());
    }

    @Test
    public void setCorrect() {
        String expected = "correct";
        question.setCorrect("correct");
        Assert.assertEquals(expected, question.getCorrect());
    }

    @Test
    public void getQuestionAnswers() {
        Assert.assertEquals(answers, question.getQuestionAnswers());
    }

    @Test
    public void setQuestionAnswers() {
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("4");
        question.setQuestionAnswers(expected);
        Assert.assertEquals(expected, question.getQuestionAnswers());
    }
}