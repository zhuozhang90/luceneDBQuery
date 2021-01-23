package lucene.hw5;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

public class Run {
	
	public static void bfSearch(int days) throws IOException {
		String light = "light" + days + ".txt";
		String heartRate = "heartrate" + days + ".txt";
		String activity = "activity" + days + ".txt";
		
		long startLight = System.nanoTime();
		BruteForceSearch.regexSearch(light, "less bright");
		long finishLight = System.nanoTime();
	    long timeElapsedLight = (finishLight - startLight); 

		long startHeart = System.nanoTime();
		
		BruteForceSearch.regexSearch(heartRate, "100");
		long finishHeart = System.nanoTime();
	    long timeElapsedHeart = (finishHeart - startHeart); 
		
		long startActivity = System.nanoTime();
		BruteForceSearch.regexSearch(activity, "running");
		long finishActivity = System.nanoTime();	 
	    long timeElapsedActivity = (finishActivity - startActivity); 
	    
	    System.out.println(days + ": ");
	    System.out.println("light: " + timeElapsedLight);
	    System.out.println("heartrate: " + timeElapsedHeart);
	    System.out.println("activity: " + timeElapsedActivity);
	}
	
	public static void lcSearch(int days) throws ParseException, IOException {
		String light = "light" + days + ".txt";
		String heartRate = "heartrate" + days + ".txt";
		String activity = "activity" + days + ".txt";
		
		Directory index = LuceneSearch.createLightDoc(light);
		Directory index1 = LuceneSearch.createHeartRateDoc(heartRate);
		Directory index2 = LuceneSearch.createActivityDoc(activity);
		
		long startLight = System.nanoTime();
		LuceneSearch.indexSearch(index, "brightness", "lessbright");
    	long finishLight = System.nanoTime();
	    long timeElapsedLight = (finishLight - startLight); 

		long startHeart = System.nanoTime();
    	LuceneSearch.indexSearch(index1, "bpm", "100");
    	long finishHeart = System.nanoTime();
	    long timeElapsedHeart = (finishHeart - startHeart); 
	    
		long startActivity = System.nanoTime();
    	LuceneSearch.indexSearch(index2, "activity", "running");
    	long finishActivity = System.nanoTime();
	    long timeElapsedActivity = (finishActivity - startActivity); 
	    
	    System.out.println(days + ": ");
	    System.out.println("light: " + timeElapsedLight);
	    System.out.println("heartrate: " + timeElapsedHeart);
	    System.out.println("activity: " + timeElapsedActivity);
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		System.out.println("BruteForce Search: ");
		bfSearch(1);
		bfSearch(10);
		bfSearch(20);
		bfSearch(30);
		bfSearch(40);
		bfSearch(50);
		bfSearch(60);
		bfSearch(70);
		bfSearch(80);
		bfSearch(90);
		bfSearch(100);
		
		System.out.println("\nLucene Search: ");
		lcSearch(1);
		lcSearch(10);
		lcSearch(20);
		lcSearch(30);
		lcSearch(40);
		lcSearch(50);
		lcSearch(60);
		lcSearch(70);
		lcSearch(80);
		lcSearch(90);
		lcSearch(100);
		

	}
}
