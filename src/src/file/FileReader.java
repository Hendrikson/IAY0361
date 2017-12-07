package file;

import java.io.BufferedReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReader {
    private final String inputUrl = Paths.get("src\\input.txt").toAbsolutePath().toString();
    private final String outputUrlBase = Paths.get("src").toAbsolutePath().toString();

    public String readCityFromInput() {
        String cityName;

        BufferedReader br;
        java.io.FileReader fr;
        try {
            fr = new java.io.FileReader(inputUrl);
            br = new BufferedReader(fr);

            String currentLine;
            if ((currentLine = br.readLine()) != null) {
                cityName = currentLine;
            } else {
                throw new RuntimeException("File is empty! Path used : " + inputUrl);
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + inputUrl);
        }
        return cityName;
    }

    public ArrayList<String> readCitiesFromInput() {
        ArrayList<String> cityNames = new ArrayList<>();

        BufferedReader br;
        java.io.FileReader fr;
        try {
            fr = new java.io.FileReader(inputUrl);
            br = new BufferedReader(fr);

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                cityNames.add(currentLine);
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + inputUrl);
        }
        return cityNames;
    }

    public String readLinesFromOutput(String cityName) {
        String outputUrl = outputUrlBase + "\\" + cityName + ".txt";
        StringBuilder outputLines = new StringBuilder();

        BufferedReader br;
        java.io.FileReader fr;
        try {
            fr = new java.io.FileReader(outputUrl);
            br = new BufferedReader(fr);

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                outputLines.append(currentLine).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + outputUrl);
        }
        return outputLines.toString();
    }

}
