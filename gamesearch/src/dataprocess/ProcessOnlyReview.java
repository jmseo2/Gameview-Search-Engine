package dataprocess;

import java.io.*;
import util.Utility;

public class ProcessOnlyReview {
    public static void processFiles(File inputDir, File outputDir) throws IOException {
        File [] inputFiles = inputDir.listFiles();
        for (File file : inputFiles) {
            if (!file.isFile())
                continue;
            String [] data = Utility.readWebpageTextData(file.getAbsolutePath());
            data[3] = Utility.getOnlyReviewText(data[3]);
            PrintWriter writer = new PrintWriter(new FileWriter(outputDir.getAbsolutePath() + "/" + file.getName()));
            writer.println(data[0]);
            writer.println(data[1]);
            writer.println(data[2]);
            writer.println(data[3]);
            writer.close();
        }
    }
    
    public static void main(String [] args) {
        File inputDir = new File("../data/crawledpages/");
        File outputDir = new File("../data/reviewonly/");
        try {
            processFiles(inputDir, outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
