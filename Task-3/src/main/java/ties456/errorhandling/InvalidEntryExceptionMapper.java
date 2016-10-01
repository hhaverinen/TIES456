/**
 * 
 */
package ties456.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jonna
 * ExceptionMapper Invalid Entry -virheelle (400)
 */
@Provider
public class InvalidEntryExceptionMapper implements ExceptionMapper<InvalidEntryException> {
	
	@Override
	public Response toResponse(InvalidEntryException ex) {
		
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),400,"www???www");
		return Response.status(Status.BAD_REQUEST)
				.entity(errorMessage)
				.build();
	}

}
