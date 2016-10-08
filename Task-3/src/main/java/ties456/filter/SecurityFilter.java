package ties456.filter;

import ties456.service.SecureUserService;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BASIC_PREFIX = "Basic ";
    private static final String OAUTH_BEARER_PREFIX = "Bearer ";
    
    public static String user;
    
    SecureUserService security = SecureUserService.getInstance();
    
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String method = crc.getMethod();
        
        if(!method.equals(HttpMethod.GET)) {
            String auth = crc.getHeaderString(AUTH_HEADER);
            if (auth == null) {
                System.out.println("No Authorization Provided!");
                notOk(crc, "No Authorization Provided!");
                return;
            }
            if(auth.startsWith(AUTH_BASIC_PREFIX)) {
                if(basicAuth(crc, auth.replace(AUTH_BASIC_PREFIX , "")))
                    return;
            } else if (auth.startsWith(OAUTH_BEARER_PREFIX)) {
                if (oAuth(crc, auth.replace(OAUTH_BEARER_PREFIX, "")))
                    return;
            } else {
                System.out.println("No Authentication method found for: "+auth);
                notOk(crc, "Unsupported Authentication Method!");
                return;
            }

        }
        System.out.println("Everything went better then exceptions!");
    }
    
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        
    }

    /**
     * OAuth implementation
     * @param crc Context
     * @param auth authentication token
     * @return was authentication denied
     */
    private boolean oAuth(ContainerRequestContext crc, String auth) {
        String method = crc.getMethod();
        System.out.println("Auth token: " + auth);

        if ((method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT)) && !security.isAuthorized(auth, SecureUserService.Permission.USER)) {
            notOk(crc, "No permission with token: " + auth);
            return true;
        }
        if (method.equals(HttpMethod.DELETE) && !security.isAuthorized(auth, SecureUserService.Permission.ADMIN)) {
            notOk(crc, "No permission with token: " + auth);
            return true;
        }

        return false;

    }

    
    /**
     * Basic Authentication Implementation
     * @param crc Contenxt
     * @param auth authentication credentials username:password
     * @return was authentication denied
     */
    private boolean basicAuth(ContainerRequestContext crc, String auth) {
        String method = crc.getMethod();
        
        String authCreds = new String(Base64.getDecoder().decode(auth));
        
        //Supposed to be username:password
        System.out.println("New Auth: "+authCreds);
        int index = authCreds.indexOf(':');
        
        if (index == -1 || index + 1 >= authCreds.length()) {
            System.out.println("Odd Credentials: "+authCreds);
            notOk(crc, "Bad Credentials!");
            return true;
        }
        
        String username = authCreds.substring(0, index);
        String password = authCreds.substring(index + 1);
        
        
        if(method.equals(HttpMethod.POST)) user = username;
        
        if (!security.isUserAuth(username, password)) {
            System.out.println("Not Authorized: "+authCreds);
            notOk(crc, "Wrong Password!");
            return true;
        }
        
        SecureUserService.Permission userPermission = security.getUserPermission(username);
        
        if(userPermission == SecureUserService.Permission.QUEST) {
            notOk(crc, "Permission Denied!");
            System.out.println("User Permission Denied: "+username+" : "+userPermission);
            return true;
        }
        
        if(method.equals(HttpMethod.DELETE) && userPermission != SecureUserService.Permission.ADMIN) {
            notOk(crc, "Permission Denied!");
            System.out.println("User Permission Denied: "+username+" : "+userPermission);
            return true;
        }
        
        return false;
    }
    
    private void notOk(ContainerRequestContext containerRequestContext, String message) {
        containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"message\":\""+message+"\"}") //TODO Use FilterResponse with MessageBodyWriter Implementation
                .build());
    }
}
