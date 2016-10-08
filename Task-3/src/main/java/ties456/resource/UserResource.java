package ties456.resource;

import ties456.data.User;
import ties456.errorhandling.InvalidEntryException;
import ties456.service.SecureUserService;

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
    public Response addUser(User user, @Context UriInfo uriInfo) {
        User newUser = secureUserService.addUser(user);
        if (newUser == null) throw new InvalidEntryException("Could not add a new user. Check your entry");
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).entity(newUser).build();
    }

    @POST
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authorize(@FormParam("grant_type") String grant_type, @FormParam("username") String username, @FormParam("password") String password)  {
        if (!grant_type.equals("password")) throw new BadRequestException("Wrong grant type!"); // TODO: JSON response
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) throw new BadRequestException("Bad request!");

        SecureUserService.OAuth oauth = secureUserService.requestAccessToken(username);

        return Response.ok(buildAthorizationResponse(oauth)).build();
    }

    //TODO: Use something more sophisticated
    private String buildAthorizationResponse(SecureUserService.OAuth oAuth) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"access_token\":\"" + oAuth.getAccessToken() + "\",");
        sb.append("\"token_type\":\"password\",");
        sb.append("\"expries_in\":\"10800\"}");
        return sb.toString();
    }
}
