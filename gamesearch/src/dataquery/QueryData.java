package dataquery;

import java.io.IOException;

import org.apache.lucene.document.Document;

public class QueryData {
    public static void main(String[] args) {
        try {
            String query = "dota";
            DataQuerier querier 
                = new DataQuerier()
                    .setIndexDirectory("../data/index/")
                    .setFieldType("title")
                    .setQuery(query)
                    .setup();
            Document[] docs = querier.getRetrievedDocs(10);
            for (Document doc : docs) {
                System.out.println(doc.get("title"));
                System.out.println(doc.get("url"));
                System.out.println("");
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
