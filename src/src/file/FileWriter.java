package file;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class FileWriter {
    private final String inputUrl = Paths.get("src\\input.txt").toAbsolutePath().toString();
    private final String outputUrlBase = Paths.get("src").toAbsolutePath().toString();
    private String outputUrl;

    public FileWriter(String cityName) {
        outputUrl = outputUrlBase + "\\" + cityName + ".txt";
    }

    public void setCity(String cityName) {
        outputUrl = outputUrlBase + "\\" + cityName + ".txt";
    }

    public void writeDataIntoInputFile(String cityName){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputUrl), "utf-8"))){
            writer.write(cityName);
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + inputUrl);
        }
    }

    public void writeDataIntoOutputFile(String data){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl), "utf-8"))){
            String[] outputPieces = data.split("\n");
            for (String outputPiece : outputPieces) {
                writer.write(outputPiece + "\r\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + outputUrl);
        }
    }

}
