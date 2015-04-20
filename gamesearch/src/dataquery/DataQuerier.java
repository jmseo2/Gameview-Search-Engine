package dataquery;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class DataQuerier {
    private String queryStr;
    private String indexDir;
    private Term[] queryTerms;

    public DataQuerier setIndexDirectory(String indexDirectory) {
        indexDir = indexDirectory;
        return this;
    }

    public DataQuerier setQuery(String queryString) {
        queryStr = queryString;
        String[] tokens = queryStr.split(" ");
        queryTerms = new Term[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            queryTerms[i] = new Term("body", tokens[i]);
        }
        return this;
    }

    public Document[] getRetrievedDocs(int hitsPerPage) {
        Document[] resDocs = new Document[hitsPerPage];
        try {
            // open index
            File path = new File(indexDir);
            Directory dir = FSDirectory.open(path.toPath());
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            PhraseQuery query = new PhraseQuery();
            for (int i = 0; i < queryTerms.length; i++) {
                query.add(queryTerms[i]);
            }

            TopDocs tops = searcher.search(query, hitsPerPage);
            ScoreDoc[] scoreDocs = tops.scoreDocs;

            for (int i = 0; i < scoreDocs.length; i++) {
                int docid = scoreDocs[i].doc;
                resDocs[i] = searcher.doc(docid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resDocs;
    }
}
