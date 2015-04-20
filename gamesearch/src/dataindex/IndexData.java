package dataindex;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Query;

/*
 * This is the main class that indexes crawled data
 */

public class IndexData {
	public static void main (String [] args) {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		DataIndexer indexer = new DataIndexer()
								.createIndex("../data/index/")
								.setAnalyzer(analyzer)
								.setDataDirectory("../data/crawledpages/")
								.addField("STRING_FIELD", "docid")
								.addField("STRING_FIELD", "url")
								.addField("TEXT_FIELD", "title")
								.addField("TEXT_FIELD", "body");
		
		indexer.indexData();
		
	}
}