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
    private ArrayList<String> topTenMonth;
    private ArrayList<String> topTenWeek;
    private ArrayList<String> topTenDay;
    private int lowestHighMonth;
    private int lowestHighWeek;
    private int lowestHighDay;

    public HighScore() {
        topTenMonth = new ArrayList<>();
        topTenWeek = new ArrayList<>();
        topTenDay = new ArrayList<>();
        lowestHighMonth = 0;
        lowestHighWeek = 0;
        lowestHighDay = 0;

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
                    if (entryDay+30 > curDay) {
                        if (topTenMonth.size() < 10 || Integer.parseInt(entry[2]) >= lowestHighMonth) {
                            topTenMonth.add(entry[2] + " " + entry[1].toUpperCase());
                            Collections.sort(topTenMonth);
                            Collections.reverse(topTenMonth);
                            if (topTenMonth.size() > 10) {
                                lowestHighMonth = Integer.parseInt(topTenMonth.get(9).split(" ")[0]);
                                topTenMonth.remove(10);
                            }
                        }
                    }
                    if (entryDay+7 > curDay) {
                        if (topTenWeek.size() < 10 || Integer.parseInt(entry[2]) >= lowestHighWeek) {
                            topTenWeek.add(entry[2] + " " + entry[1].toUpperCase());
                            Collections.sort(topTenWeek);
                            Collections.reverse(topTenWeek);
                            if (topTenWeek.size() > 10) {
                                lowestHighWeek = Integer.parseInt(topTenWeek.get(9).split(" ")[0]);
                                topTenWeek.remove(10);
                            }
                        }
                    }
                    if (entryDay+1 > curDay) {
                        if (topTenDay.size() < 10 || Integer.parseInt(entry[2]) >= lowestHighDay) {
                            topTenDay.add(entry[2] + " " + entry[1].toUpperCase());
                            Collections.sort(topTenDay);
                            Collections.reverse(topTenDay);
                            if (topTenDay.size() > 10) {
                                lowestHighDay = Integer.parseInt(topTenDay.get(9).split(" ")[0]);
                                topTenDay.remove(10);
                            }
                        }
                    }
                } else if (Integer.parseInt(currentDate[0]) == Integer.parseInt((1+date[0]))) { //this is for the overlap between first week/month of year
                    if (entryDay+30 > curDay+365) {
                        if (topTenMonth.size() < 10 || Integer.parseInt(entry[2]) >= lowestHighMonth) {
                            topTenMonth.add(entry[2] + " " + entry[1].toUpperCase());
                            Collections.sort(topTenMonth);
                            Collections.reverse(topTenMonth);
                            if (topTenMonth.size() > 10) {
                                lowestHighMonth = Integer.parseInt(topTenMonth.get(9).split(" ")[0]);
                                topTenMonth.remove(10);
                            }
                        }
                    }
                    if (entryDay+7 > curDay+365) {
                        if (topTenWeek.size() < 10 || Integer.parseInt(entry[2]) >= lowestHighWeek) {
                            topTenWeek.add(entry[2] + " " + entry[1].toUpperCase());
                            Collections.sort(topTenWeek);
                            Collections.reverse(topTenWeek);
                            if (topTenWeek.size() > 10) {
                                lowestHighWeek = Integer.parseInt(topTenWeek.get(9).split(" ")[0]);
                                topTenWeek.remove(10);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ArrayList<String>> getHighScores() {
        ArrayList<ArrayList<String>> topTen = new ArrayList<>();
        topTen.add(topTenDay);
        topTen.add(topTenWeek);
        topTen.add(topTenMonth);
        return topTen;
    }

}
