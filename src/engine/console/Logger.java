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
	
}
