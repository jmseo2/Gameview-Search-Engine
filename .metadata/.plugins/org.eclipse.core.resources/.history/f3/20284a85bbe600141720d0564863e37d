package datacrawl;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.*;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlData {
	public static void main (String [] args) {
		CrawlConfig crawlConfig = new CrawlConfig();
		crawlConfig.setCrawlStorageFolder("../crawledpages/");
		System.out.println(crawlConfig.toString());
		PageFetcher pageFetcher = new PageFetcher(crawlConfig);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		try {
			CrawlController crawlController = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
			
		} catch (Exception e) {
			
		}
	}
}
