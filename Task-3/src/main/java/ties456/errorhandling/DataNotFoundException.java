package ties456.errorhandling;
/**
 * @author Jonna
 * Virheviesti luokka DataNotFound virheille (400)
 */
public class DataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6672553621676928689L;
	public DataNotFoundException(String message){
		super(message);
	}


}
