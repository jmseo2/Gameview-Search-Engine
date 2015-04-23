package dataindex;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.*;

import util.Utility;

/*
 * Core class that indexes text files located at directory directory
 */

public class DataIndexer {
    private String dataDirectory;
    private List<String> fieldTypes;
    private List<String> fieldNames;
    private Directory index;
    private Analyzer analyzer;
    private IndexWriterConfig config;
    private IndexWriter writer;
    private int counter;

    public DataIndexer() {
        fieldTypes = new ArrayList<String>();
        fieldNames = new ArrayList<String>();
        counter = 0;
    }

    public DataIndexer createIndex(String directory) {
        try {
            File file = new File(directory);
            index = new SimpleFSDirectory(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public DataIndexer setAnalyzer(Analyzer s) {
        analyzer = s;
        return this;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public DataIndexer setDataDirectory(String directory) {
        dataDirectory = directory;
        return this;
    }
    
    public DataIndexer setup() throws IOException {
        config = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(index, config);
        return this;
    }

    public DataIndexer addField(String fieldType, String name) {
        fieldTypes.add(fieldType);
        fieldNames.add(name);
        return this;
    }

    public void indexData() throws IOException {
        System.out.println("[INFO] Setting up index writer configuration...");
        System.out.println("[INFO] Indexing data located at "
                    + dataDirectory + "...");
        File folder = new File(dataDirectory);
        for (File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                indexFile(writer, fileEntry);
            }
        }
        writer.close();
        System.out.println("[INFO] Total of " + counter + " documents indexed...");
        System.out.println("[INFO] Indexing done...");
    }

    public void indexFile(IndexWriter writer, File file) throws IOException {
        String[] data = Utility.readWebpageTextData(file.getAbsolutePath());
        // Don't index non-review document
        if (data[2] == null) {
            return;
        }
        counter++;
        System.out.println("[INFO] Indexing document with doc id " + data[0]);
        Document doc = new Document();
        if (data.length == fieldTypes.size()) {
            for (int i = 0; i < fieldTypes.size(); i++) {
                String fieldType = fieldTypes.get(i);
                String fieldName = fieldNames.get(i);
                if (fieldType.equals("STRING_FIELD")) {
                    doc.add(new StringField(fieldName, data[i], Field.Store.YES));
                } else if (fieldType.equalsIgnoreCase("TEXT_FIELD")) {
                    doc.add(new TextField(fieldName, data[i], Field.Store.YES));
                }
            }
        } else {
            System.out.println("[ERROR] Data in wrong format... ignoring...");
        }
        writer.addDocument(doc);
    }
}
