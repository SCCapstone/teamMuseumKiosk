package teamMuseumKiosk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore {
    private ArrayList<String> topTen;
    private int lowestHigh;

    public HighScore(String time) { //Accepted 'time' values: "month" "week" "day"
        topTen = new ArrayList<>();
        lowestHigh = 0;

        BufferedReader reader  = null;

        try
        {
            reader = new BufferedReader(new FileReader(new File("EmailList.csv")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(",");
                String[] date = entry[3].split("-");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate entryDate = LocalDate.parse(entry[3], format);
                int entryDay = entryDate.getDayOfYear();
                LocalDate curDate = LocalDate.now();
                int curDay = curDate.getDayOfYear();
                String currentDateString = curDate.toString();
                String[] currentDate = currentDateString.split("-");
                if (Integer.parseInt(currentDate[0]) == Integer.parseInt((date[0]))) { //same year
                    if (time == "month") {
                        if (entryDay+30 < curDay) {
                            continue;
                        }
                    } else if (time == "week") {
                        if (entryDay+7 < curDay) {
                            continue;
                        }
                    } else if (time == "day") {
                        if (entryDay < curDay) {
                            continue;
                        }
                    }
                } else if (Integer.parseInt(currentDate[0]) == Integer.parseInt((1+date[0]))) { //this is for the overlap between first week/month of year
                    if (time == "month") {
                        if (entryDay+30 < curDay+365) {
                            continue;
                        }
                    } else if (time == "week") {
                        if (entryDay+7 < curDay+365) {
                            continue;
                        }
                    }
                }
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
