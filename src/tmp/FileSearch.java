package tmp;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class FileSearch {
	
	// prints out lines that match given regex
	public static void regexSearch(String filePath, String regex) 
			throws IOException {
		
		// read all lines of file into a list
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		
		System.out.println("\nsearching in: " + filePath);
		
		// compile regex to search within file
	    Pattern p = Pattern.compile(regex);
	    
	    // loop throuth lines to print matches	    
	    // using stream 
	    lines.stream()
	    	.filter(line -> p.matcher(line).find())
	    	.forEach(System.out::println);
		
	}

}
