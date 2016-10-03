package ties456.errorhandling;

/**
 * @author Jonna
 * Virheviesti luokka Invalid Entry virheille (400)
 */

public class InvalidEntryException extends RuntimeException {
	private static final long serialVersionUID = -4468817549503603383L;
	
	public InvalidEntryException(String message){
		super(message);
	}
}
