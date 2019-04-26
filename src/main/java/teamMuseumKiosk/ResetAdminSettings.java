package teamMuseumKiosk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public interface ResetAdminSettings {

    String settings = "settings.txt";


    // If settings.txt fields are deleted, system will write default settings so game doesn't crash
    default void ifSettingsFileIsEmpty(String missingField) throws IOException {

        String missingLine = "";
        // get missing field
        if (missingField.equals("strikeNum")) {
            // set to default of 3 strikes
            missingLine = "strikeNum: 3";
        }
        else if (missingField.equals("maxQuestions")) {
            // set to default of 10 questions
            missingLine = "maxQuestions: 10";
        }
        else if (missingField.equals("highScores")) {
            // set to default of rotating scores
            missingLine = "highScores: rotate";
        }
        else if (missingField.equals("username")) {
            // set to default of museumAdmin
            missingLine = "username: museumAdmin";
        }
        else if (missingField.equals("password")) {
            // set to default of museumKiosk19
            missingLine = "password: museumKiosk19";
        }

        // set up of settings.txt:
        //        strikeNum: num
        //        maxQuestions: num
        //        highScores: rotate
        //        username: string
        //        password: string

        String newSettings = "";

        List<String> settingsList = Files.lines(Paths.get(settings)).collect(Collectors.toList());

        for (String line : settingsList) {
            if (missingField.equals("strikeNum") && line.contains(missingField)) {
                newSettings = newSettings + missingLine;
            }
            else if (line.contains(missingField)) {
                newSettings = newSettings + "\n" + missingLine;
            }
            // for other settings - just copy what's there
            else
            {
                newSettings = newSettings + "\n" + line;
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(settings, false));
        writer.write(newSettings);
        writer.close();

    }
}
