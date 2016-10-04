package ties456.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
public class SecureUserService {
    private static final SecureUserService instance = new SecureUserService();
    public static SecureUserService getInstance() {return instance;}
    
    
    private final Map<String, Permission> userPermission = new HashMap<>();
    private final Map<String, String> userPasswordDB = new HashMap<>();
    
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
    
    
    public enum Permission {
        QUEST,
        USER,
        ADMIN;
    }
}
