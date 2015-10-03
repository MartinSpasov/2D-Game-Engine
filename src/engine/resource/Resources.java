package engine.resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Resources {

	// TODO handle errors for all methods
	public static String loadText(String file) {
		StringBuilder string = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(file));
			while (input.hasNextLine()) {
				string.append(input.nextLine() + "\n");
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return string.toString();
	}
}
