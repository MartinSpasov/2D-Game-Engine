package engine.resource;

public class InvalidValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidValueException(String attribute, String value) {
		super("Invalid value \"" + value + "\" for attribute \"" + attribute + "\"");
	}
}
