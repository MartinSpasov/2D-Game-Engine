package engine.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

public class Console implements Runnable {

	private volatile boolean closed;
	
	private ConcurrentHashMap<String, Command> commands;
	
	public Console() {
		commands = new ConcurrentHashMap<String, Command>();
		
		// Add engine commands
		commands.put("echo", new Command() {

			@Override
			public boolean execute(String command, String[] args) {
				StringBuilder message = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					message.append(args[i]);
					
					if (i < args.length - 1) {
						message.append(' ');
					}
				}
				System.out.println(message);
				return true;
			}
			
		});
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			while (!closed) {
				
				if (input.ready()) {
					String[] rawCommand = input.readLine().split("\\s+");
					Command command  = commands.get(rawCommand[0]);
					
					if (command != null) {
						
						String[] args = new String[rawCommand.length - 1];
						for (int i = 0; i < args.length; i++) {
							args[i] = rawCommand[i + 1];
						}
						
						command.execute(rawCommand[0], args);
					}
					else {
						// TODO Print error message
						System.err.println("NO SUCH COMMAND");
					}
				}
				
			}
		
			input.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCommand(String name, String[] aliases, Command command) {
		// TODO handle if command already exists
		commands.put(name, command);
	}
	
	public void removeCommand(String name)  {
		commands.remove(name);
	}
	
	public void destroy() {
		closed = true;
	}

}
