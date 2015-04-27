package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.sf.classifier4J.summariser.SimpleSummariser;

public class UtilityTest {
    public static void main(String [] args) {
        String [] data = Utility.readWebpageTextData("../data/crawledpages/gamespot_review791.txt");
        String review = Utility.getOnlyReivewText(data[3]);
        //SimpleSummariser summarizer = new SimpleSummariser();
        //String res = summarizer.summarise(review, 5);
        String title = Utility.getTitle(data[3]);
        System.out.println(title);
    }
    
}
