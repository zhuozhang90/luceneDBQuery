package lucene.query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

@SuppressWarnings("deprecation")

public class LuceneSearch {
		
	// search field for query in Directory index
	public static int indexSearch (Directory index, String field, String query) throws ParseException, IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        
        String s = query.replace(" ", "");
        Query q = new QueryParser(field, analyzer).parse(s);
        
        int hitsPerPage = 10000;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
//        
//        for(int i=0;i<hits.length;++i) {
//            int docId = hits[i].doc;
//            Document d = searcher.doc(docId);
//            System.out.println(d);
//        }        
        int numHits = hits.length;
        reader.close();
		
		return numHits;
	}
	
	// return indexWriter
	public static Directory createLightDoc(String filePath) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        
        ArrayList<String> input = readFile(filePath);
        
        for (String each : input) {
        	String s = each.replace(" ", "");
        	String[] values = s.split(",");
        	addLightDoc(w, values[0].trim(), values[1].trim(), values[2].trim(), 
        			values[3].trim(), values[4].trim());
        }
        
        w.close();
        
		return index;
	}
	
	public static Directory createHeartRateDoc(String filePath) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        
        ArrayList<String> input = readFile(filePath);
        
        for (String each : input) {
        	String[] values = each.split(",");
        	addHeartRateDoc(w, values[0], values[1], values[2]);
        }
        
        w.close();
        
		return index;
	}
	
	public static Directory createActivityDoc(String filePath) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        
        ArrayList<String> input = readFile(filePath);
        
        for (String each : input) {
        	String[] values = each.split(",");
        	addActivityDoc(w, values[0], values[1], values[2], values[3], values[4]);
        }
        
        w.close();
        
		return index;
	}
	
	private static ArrayList<String> readFile(String filePath) throws IOException {
		
	    ArrayList<String> tmpContent = new ArrayList<>();
	    
    	Stream<String> stream = Files.lines(Paths.get(filePath));

	    try { 
	    	stream.forEach(s -> tmpContent.add(s)); 
	    } finally {
	    	stream.close();
	    }
	     	    
	    return tmpContent; 
	}
	
    private static void addLightDoc(IndexWriter w, String sensor, String lux, String date,
    		String brightness, String hour) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("sensor", sensor, Field.Store.YES));
        doc.add(new StringField("lux", lux, Field.Store.YES));
        doc.add(new StringField("date", date, Field.Store.YES));
        doc.add(new StringField("brightness", brightness, Field.Store.YES));
        doc.add(new StringField("hour", hour, Field.Store.YES));
        w.addDocument(doc);
    }
    
    private static void addHeartRateDoc(IndexWriter w, String sensor, String date, String bmp) 
    		throws IOException {
        Document doc = new Document();
        doc.add(new StringField("sensor", sensor, Field.Store.YES));
        doc.add(new StringField("date", date, Field.Store.YES));
        doc.add(new StringField("bmp", bmp, Field.Store.YES));
        w.addDocument(doc);
    }
    
    private static void addActivityDoc(IndexWriter w, String sensor, String startTime, String endTime,
    		String activity, String duration) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("sensor", sensor, Field.Store.YES));
        doc.add(new StringField("startTime", startTime, Field.Store.YES));
        doc.add(new StringField("endTime", endTime, Field.Store.YES));
        doc.add(new StringField("activity", activity, Field.Store.YES));
        doc.add(new StringField("duration", duration, Field.Store.YES));
        w.addDocument(doc);
    }
    
    public static void main(String[] args) throws IOException, ParseException {
    	Directory index = createLightDoc("light1.txt");
    	int num = indexSearch(index, "brightness", "lessbright");
    	System.out.println(num);
    }
    
}
