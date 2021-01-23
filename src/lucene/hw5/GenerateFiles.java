package lucene.hw5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GenerateFiles {
	
	// create file of num of dates given
	public static void createFile(ArrayList<String> input, String outFilePath, int numDays) throws IOException {
		StringBuilder contentToWrite = new StringBuilder();
		for (int i = 0; i < numDays; i++) {
			for (String each : input) {
				contentToWrite.append(each).append("\n");
			}
		}
		File outFile = new File(outFilePath);
		BufferedWriter outFileWriter = new BufferedWriter(new FileWriter(outFile));
		try{
			// try block with resources, no need to close file
		    outFileWriter.write(contentToWrite.toString()); // write the string in string builder to file
		    outFilePath = outFile.getAbsolutePath();
		} catch(IOException e){
		    e.printStackTrace();
		} finally {
			outFileWriter.close();
		}
		
	}
	// parses json to csv file
	public static ArrayList<String> parseActivityRecord(ArrayList<String> records){
			JSONParser parser = new JSONParser();
			JSONObject json;
			ArrayList<String> csvRecords = new ArrayList<>();
			try {
				for (String record : records) {
					json = (JSONObject) parser.parse(record);
					// get values from json 
					String sensorName = (String) json.get("sensor_name");
					String startTime = (String) ((JSONObject)json.get("timestamp")).get("start_time");
					String endTime = (String) ((JSONObject)json.get("timestamp")).get("end_time");	
					String activity = (String) ((JSONObject)json.get("sensor_data")).get("activity");		
					long duration = (long) ((JSONObject)json.get("sensor_data")).get("duration");
					// add csv format record to records
					String csvRecord = sensorName + "," + startTime + "," + endTime + "," + activity + "," + duration;
					csvRecords.add(csvRecord);
				}
			} catch (ParseException e) {
				System.out.println(e + ", skip entry.");
			}
			return csvRecords;
	}
	// parses json to csv file
	public static ArrayList<String> parseHeartRateRecord(ArrayList<String> records){
			JSONParser parser = new JSONParser();
			JSONObject json;
			ArrayList<String> csvRecords = new ArrayList<>();
			try {
				for (String record : records) {
					json = (JSONObject) parser.parse(record);
					// get values from json 
					String sensorName = (String) json.get("sensor_name");
					String time = (String) json.get("timestamp");
					long bpm = (long) ((JSONObject)json.get("sensor_data")).get("bpm");
					// add csv format record to records
					String csvRecord = sensorName + "," + time + "," + bpm;
					csvRecords.add(csvRecord);
				}
			} catch (ParseException e) {
				System.out.println(e + ", skip entry.");
			}
			return csvRecords;
	}
	
	// read file into StringBuilder to process
	public static ArrayList<String> readFile(String filePath) throws IOException {
	    ArrayList<String> fileContent = new ArrayList<>();
	    
	    try (Stream<String> stream = Files.lines(Paths.get(filePath))){ 
	        stream.forEach(s -> fileContent.add(s)); // append each line to fileContent then new line
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return fileContent; 
	}
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> lightFile = readFile("lucence/light.txt");
		for (int i = 1; i <=100; i++) {
			if (i == 1 || i % 10 == 0) {
				String fileName = "light" + i + ".txt";
				createFile(lightFile, fileName, i);
			}
		}

		ArrayList<String> activityFile = parseActivityRecord(readFile("lucence/activity.txt"));
		for (int i = 1; i <=100; i++) {
			if (i == 1 || i % 10 == 0) {
				String fileName = "activity" + i + ".txt";
				createFile(activityFile, fileName, i);
			}
		}
		
		ArrayList<String> heartRateFile = parseHeartRateRecord(readFile("lucence/heartrate.txt"));
		for (int i = 1; i <=100; i++) {
			if (i == 1 || i % 10 == 0) {
				String fileName = "heartrate" + i + ".txt";
				createFile(heartRateFile, fileName, i);
			}

		}

	}
	
}
