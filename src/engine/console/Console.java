package engine.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

	private BufferedReader reader;
	
	public Console() {
		
		if (System.console() == null) {
			// If no console exists rely on System.in
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		else {
			reader = new BufferedReader(System.console().reader());
		}
		
	}
	
	public void pollCommands() {
		try {
			if (reader.ready()) {
				StringBuilder sb = new StringBuilder();
			
				while (reader.ready()) {
					sb.append((char)reader.read());
				}
			
				System.out.println(sb.toString());
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
