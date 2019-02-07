package teamMuseumKiosk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore {
    private ArrayList<String> topTen;
    private int lowestHigh;

    public HighScore() {
        topTen = new ArrayList<>();
        lowestHigh = 0;

        BufferedReader reader  = null;

        try
        {
            reader = new BufferedReader(new FileReader(new File("EmailList.csv")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(",");
                //TODO: ADD ABILITY TO CHOOSE TIME RANGE
                if (topTen.size() < 10 || Integer.parseInt(entry[2]) >= lowestHigh) {
                    topTen.add(entry[2] + " " + entry[1].toUpperCase());
                    Collections.sort(topTen);
                    Collections.reverse(topTen);
                    if (topTen.size() > 10) {
                        lowestHigh = Integer.parseInt(topTen.get(9).split(" ")[0]);
                        topTen.remove(10);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getHighScores() {
        return topTen;
    }

}
