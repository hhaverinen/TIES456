package ties456.service;

import ties456.data.User;

import javax.xml.bind.annotation.XmlEnum;
import java.util.*;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
public class SecureUserService {
    private static final SecureUserService instance = new SecureUserService();
    public static SecureUserService getInstance() {return instance;}
    
    
    private final Map<String, Permission> userPermission = new HashMap<>();
    private final Map<String, String> userPasswordDB = new HashMap<>();
    private final Map<String, OAuth> accessTokens = new HashMap<>();
    
    private SecureUserService() {
        userPasswordDB.put("kissa","12345");
        userPasswordDB.put("koira","00000");
        userPasswordDB.put("admin","admin");
        
        userPermission.put("koira", Permission.USER);
        userPermission.put("admin", Permission.ADMIN);
    }
    
    public boolean isUserAuth(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) return false;
        return password.equals(userPasswordDB.get(username));
    }
    
    public Permission getUserPermission(String username) {
        Permission permission = userPermission.get(username);
        return permission == null ? Permission.QUEST : permission;
    }
    
    /**
     * Returns Access Token if it hasn't expired
     * @param user user
     * @return access token or null if expired
     */
    public String getAccessToken(String user) {
        OAuth oAuth = accessTokens.get(user);
        if(oAuth == null) return null;
        
        if(isValid(oAuth))
            return oAuth.accessToken;
        
        //Access Token has expired
        accessTokens.remove(user);
        return null;
    }
    
    public boolean isAuthorized(String authToken, Permission permission) {
        //TODO Hash this
        Optional<OAuth> tokenOpt = accessTokens.values().stream().filter(token -> token.accessToken.equals(authToken)).findFirst();
        if(!tokenOpt.isPresent() || !isValid(tokenOpt.get()))
            return false;
        return tokenOpt.get().accessPermission == permission;
    }
    
    /**
     * Creates new access token for user and returns it<br>
     * Replaces any tokens set for user
     * @param user user
     * @return access token
     */
    public OAuth requestAccessToken(String user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 3);
        OAuth oAuth = new OAuth(UUID.randomUUID().toString().replace("-",""),  calendar.getTime(), getUserPermission(user));
        accessTokens.put(user, oAuth);
        return oAuth;
    }
    
    private boolean isValid(OAuth oAuth) {
        return new Date().before(oAuth.expires);
    }
    
    @XmlEnum
    public enum Permission {
        QUEST,
        USER,
        ADMIN
    }
    
    public static class OAuth {
        private final Permission accessPermission;
        private final String accessToken;
        private final Date expires;
        
        public OAuth(String token, Date expires, Permission permission) {
            this.accessToken = token;
            this.expires = expires;
            this.accessPermission = permission;
        }
    
        public String getAccessToken() {
            return accessToken;
        }
    
        public Date getExpires() {
            return expires;
        }
    
        public Permission getAccessPermission() {
            return accessPermission;
        }
    }

    /**
     * Creates a new user
     * @param user user
     * @return returns user object if user creation was successful, returns null otherwise
     */
    public User addUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty())
            return null;

        if (userPasswordDB.get(user.getUsername()) != null)
            return null;

        userPasswordDB.put(user.getUsername(), user.getPassword());
        if (user.getPermission() != null) userPermission.put(user.getUsername(), user.getPermission());

        return user;
    }
}
