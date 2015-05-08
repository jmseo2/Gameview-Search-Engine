package dataprocess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import util.Utility;

public class SaveTitles {
    public static void processFiles(File inputDir, File outputFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile.getAbsolutePath()));
        File [] inputFiles = inputDir.listFiles();
        for (File file : inputFiles) {
            if (!file.isFile())
                continue;
            String [] data = Utility.readWebpageTextData(file.getAbsolutePath());
            writer.println(data[2]);
        }
        writer.close();
    }
    
    public static void main(String [] args) {
        File inputDir = new File("../data/crawledpages/");
        File outputFile = new File("../data/titles/titles.txt");
        try {
            processFiles(inputDir, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
