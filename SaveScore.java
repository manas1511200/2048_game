

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveScore {

    private int attempt_no = 1;
    private final File file = new File("d2048.txt");
    private final File attempt = new File("Attempt.txt");

    public void saveScore(int size, int score) {
        try {
            if (!attempt.exists()) attempt.createNewFile();
            if (!file.exists()) file.createNewFile();

            Path att = Paths.get("Attempt.txt");
            if (attempt.exists()) {
                String attemptNumber = Files.readString(att).trim();
                attempt_no = attemptNumber.isEmpty() ? 1 : Integer.parseInt(attemptNumber) + 1;
                Files.write(att, String.valueOf(attempt_no).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }

            Path path = file.toPath();
            String entry = "Attempt " + attempt_no + " | Size: " + size + " | Score: " + score + "\n";
            Files.write(path, entry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
        }
    }


    public List<String> loadScores() {
        List<String> scores = new ArrayList<>();
        try {
            scores = Files.readAllLines(Paths.get("d2048.txt"));
            Collections.reverse(scores); // Show latest scores first
        } catch (IOException ignored) {
        }
        return scores;
    }


}
