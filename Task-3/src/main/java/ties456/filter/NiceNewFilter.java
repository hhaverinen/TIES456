package ties456.filter;

import ties456.service.SecureUserService;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
@Provider
public class NiceNewFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String AUTH_HEADER = "Authorization";
    
    SecureUserService security = SecureUserService.getInstance();
    
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String method = crc.getMethod();
        
        if(!method.equals(HttpMethod.GET)) {
            String auth = crc.getHeaderString(AUTH_HEADER);
            if (auth == null) {
                notOk(crc);
                return;
            }
    
            String authCreds = new String(Base64.getDecoder().decode(auth));//Supposed to be username:password
            int index = authCreds.indexOf(':');
    
            if (index == -1 || index + 1 >= authCreds.length()) {
                notOk(crc);
                return;
            }
    
            String username = authCreds.substring(0, index);
            String password = authCreds.substring(index + 1);
    
            if (!security.isUserAuth(username, password)) {
                notOk(crc);
                return;
            }
            SecureUserService.Permission userPermission = security.getUserPermission(username);
            if(userPermission == SecureUserService.Permission.QUEST) {
                notOk(crc);
                return;
            }
            if(method.equals(HttpMethod.DELETE) && userPermission != SecureUserService.Permission.ADMIN) {
                notOk(crc);
                return;
            }
        }
    }
    
    private void notOk(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(new FilterResponse("No Authorization Provided!")).build());
    }
    
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        
    }
}
