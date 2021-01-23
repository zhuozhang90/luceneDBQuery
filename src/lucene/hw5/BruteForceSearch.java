package lucene.hw5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BruteForceSearch {
	// prints out lines that match given regex
		public static int regexSearch(String filePath, String regex) throws IOException {
			
			// read all lines of file into a list
			List<String> lines = Files.readAllLines(Paths.get(filePath));
						
			// compile regex to search within file
		    Pattern p = Pattern.compile(regex);
		    
		    // return num of founds
		    return lines.stream()
		    	.filter(line -> p.matcher(line).find())
		    	.collect(Collectors.toList())
		    	.size();
		}
}
