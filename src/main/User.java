package main;

public class User {
    String name, email;
    int score;

    public User(String name, int score, String email) {
        setName(name);
        setScore(score);
        setEmail(email);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}

