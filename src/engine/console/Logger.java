package engine.console;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private PrintStream output;
	private SimpleDateFormat format;
	private Date date;
	
	public Logger(PrintStream output, SimpleDateFormat format) {
		this.output = output;
		this.format = format;
		date = new Date();
	}
	
	public Logger(PrintStream output) {
		this(output, new SimpleDateFormat("[HH:mm:ss]"));
	}
	
	public void log(String message) {
		date.setTime(System.currentTimeMillis());
		output.println(format.format(date) + " " + message);
	}
	
	public void log(int i) {
		log(String.valueOf(i));
	}
	
	public void log(long l) {
		log(String.valueOf(l));
	}
	
	public void log(float f) {
		log(String.valueOf(f));
	}
	
	public void log(double d) {
		log(String.valueOf(d));
	}
	
	public void log(char c) {
		log(String.valueOf(c));
	}
	
	public void log(boolean b) {
		log(String.valueOf(b));
	}
}
