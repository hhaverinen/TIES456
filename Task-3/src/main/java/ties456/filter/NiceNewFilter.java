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
    private static final String AUTH_BASIC_PREFIX = "Basic ";
    
    SecureUserService security = SecureUserService.getInstance();
    
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String method = crc.getMethod();
        
        if(!method.equals(HttpMethod.GET)) {
            String auth = crc.getHeaderString(AUTH_HEADER);
            if (auth == null) {
                System.out.println("No Authorization Provided!");
                notOk(crc);
                return;
            }
            auth = auth.replace(AUTH_BASIC_PREFIX , "");
            
            String authCreds = new String(Base64.getDecoder().decode(auth));//Supposed to be username:password
            System.out.println("New Auth: "+authCreds);
            int index = authCreds.indexOf(':');
    
            if (index == -1 || index + 1 >= authCreds.length()) {
                System.out.println("Odd Credentials: "+authCreds);
                notOk(crc);
                return;
            }
    
            String username = authCreds.substring(0, index);
            String password = authCreds.substring(index + 1);
    
            if (!security.isUserAuth(username, password)) {
                System.out.println("Not Authorized: "+authCreds);
                notOk(crc);
                return;
            }
            
            SecureUserService.Permission userPermission = security.getUserPermission(username);
            if(userPermission == SecureUserService.Permission.QUEST) {
                notOk(crc);
                System.out.println("User Permission Denied: "+username+" : "+userPermission);
                return;
            }
            if(method.equals(HttpMethod.DELETE) && userPermission != SecureUserService.Permission.ADMIN) {
                notOk(crc);
                System.out.println("User Permission Denied: "+username+" : "+userPermission);
                return;
            }
        }
        System.out.println("Everything went better then exceptions!");
    }
    
    private void notOk(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }
    
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        
    }
}
