package teamMuseumKiosk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    private String name;
    private String email;
    private User userCorrect;
    private int score;

    @Before
    public void initialize() {
        name = "PAN";
        email = "test@email.com";
        score = 2;

        userCorrect = new User(name, score, email);
    }

    @Test
    public void getName() {
        Assert.assertEquals("PAN", userCorrect.getName());
    }

    @Test
    public void setName() {
        userCorrect.setName("MOP");
        Assert.assertEquals("MOP", userCorrect.getName());
    }

    @Test
    public void getScore() {
        Assert.assertEquals(2, userCorrect.getScore());
    }

    @Test
    public void setScore() {
        userCorrect.setScore(900);
        Assert.assertEquals(900, userCorrect.getScore());
    }

    @Test
    public void getEmail() {
        Assert.assertEquals("test@email.com", userCorrect.getEmail());
    }

    @Test
    public void setEmail() {
        userCorrect.setEmail("test1@email.com");
        Assert.assertEquals("test1@email.com", userCorrect.getEmail());
    }
}