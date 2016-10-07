package ties456.resource;

import ties456.data.User;
import ties456.errorhandling.InvalidEntryException;
import ties456.filter.SecurityFilter;
import ties456.service.SecureUserService;
import ties456.service.SecureUserService.Permission;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * @author Henri Haverinen
 * @version 05/10/16.
 */
@Path("/users")
public class UserResource {

    SecureUserService secureUserService = SecureUserService.getInstance();

	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user, @Context UriInfo uriInfo)
    {
    	if(secureUserService.getUserPermission(SecurityFilter.user) != Permission.ADMIN)
    		throw new WebApplicationException("Not authorized user: " + SecurityFilter.user, 401); 
    	
        User newUser = secureUserService.addUser(user);
        if (newUser == null) throw new InvalidEntryException("Could not add a new user. Check your entry");
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).entity(newUser).build();
    }
}
