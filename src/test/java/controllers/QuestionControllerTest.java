package controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import teamMuseumKiosk.Question;
import teamMuseumKiosk.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class QuestionControllerTest {
    private User testUser;
    private QuestionController controller;
    private static String testCSV = "/testTriviaQuestions.csv";

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() {
        testUser = new User("testName", 0, "testEmail@test.com");
        controller = new QuestionController();
        controller.setUser(testUser);
    }


    @Test
    public void loadDataTest() {
        try {
            ArrayList<Question> actual;
            ArrayList<Question> expected = new ArrayList<>();

            //set up for expected
            ArrayList<String> inputList = new ArrayList<>();
            inputList.add("Which artist from SC is best known for his abstract expressionist paintings of targets flags and numbers?,Pearl Fryar,Willen de Koonig, Andy Warhol,Jasper Johns,3");
            inputList.add("Where does the name 'Pee Dee' come from?,Color of the Pee Dee river,Because that's how people say 'Petey' in South Carolina,Original name of the state,Native American Tribe,2,/video/testvideo2.mp4");
            inputList.add("When was USC founded?,1901,1876,1791,1801,1,/images/UofSCLogo.png");

            List<String> data;
            for(String line : inputList) {
                data = Arrays.asList(line.split(","));
                if(data.size() == 6) {
                    Question question = new Question(data.get(0), data.subList(1, 5), data.get(5));
                    expected.add(question);
                }else if (data.size() == 7){
                    Question question = new Question(data.get(0), data.subList(1, 5), data.get(5),data.get(6));
                    expected.add(question);
                }
            }

            //actual
            URL url = this.getClass().getResource(testCSV);
            actual = controller.loadData(url.getFile());


            //assertion
            Assert.assertTrue(actual.toString().equals(expected.toString()));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}