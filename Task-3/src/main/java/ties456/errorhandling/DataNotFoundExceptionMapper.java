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
 * ExceptionMapper DataNotFound-virheelle (404)
 */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),404,"Please check your entry!");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	
	}
}
