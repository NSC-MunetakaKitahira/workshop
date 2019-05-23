package carrot.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput {

	private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Enter
	 */
	public static void waitEnter() {
		try {
			READER.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
