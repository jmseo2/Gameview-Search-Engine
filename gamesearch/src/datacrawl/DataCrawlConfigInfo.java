package datacrawl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataCrawlConfigInfo {
    public static String[] webpageRoots;
    public static int numberOfCrawlers;
    public static String crawlStorageFolder;
    public static int politenessDelay;
    public static int maxDepthOfCrawling;
    public static int maxPagesToFetch;
    public static boolean includeBinaryContentInFiltering;
    public static boolean resumableCrawling;

    static {
        try {
            System.out.println("[INFO] Reading crawl-config-info.txt file...");
            BufferedReader in = new BufferedReader(new FileReader(
                    "../crawl-config-info.txt"));
            String line = null;
            while (true) {
                line = in.readLine();
                if (line == null)
                    break;
                String[] tokens = line.split(" ");

                if (tokens[0].equals("webpage-roots")) {
                    webpageRoots = new String[tokens.length - 1];
                    for (int i = 1; i < tokens.length; i++) {
                        webpageRoots[i - 1] = tokens[i];
                    }
                } else if (tokens[0].equals("number-of-crawlers")) {
                    numberOfCrawlers = Integer.parseInt(tokens[1]);
                } else if (tokens[0].equals("crawl-storage-folder")) {
                    crawlStorageFolder = tokens[1];
                } else if (tokens[0].equals("politeness-delay")) {
                    politenessDelay = Integer.parseInt(tokens[1]);
                } else if (tokens[0].equals("max-depth-of-crawling")) {
                    maxDepthOfCrawling = Integer.parseInt(tokens[1]);
                } else if (tokens[0].equals("max-pages-to-fetch")) {
                    maxPagesToFetch = Integer.parseInt(tokens[1]);
                } else if (tokens[0]
                        .equals("include-binary-content-in-filtering")) {
                    if (tokens[1].equals("true")) {
                        includeBinaryContentInFiltering = true;
                    } else {
                        includeBinaryContentInFiltering = false;
                    }
                } else if (tokens[0].equals("resumable-crawling")) {
                    if (tokens[1].equals("true")) {
                        resumableCrawling = true;
                    } else {
                        resumableCrawling = false;
                    }
                } else {
                    System.out.println("[ERROR] Unknown config info");
                }
            }
            in.close();
            System.out
                    .println("[INFO] Done reading crawl-config-info.txt file...");
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Data crawl config info file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
