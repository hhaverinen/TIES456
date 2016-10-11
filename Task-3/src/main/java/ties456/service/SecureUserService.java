package ties456.service;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import ties456.data.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.XmlEnum;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
public class SecureUserService {
    private static final SecureUserService instance = new SecureUserService();
    public static SecureUserService getInstance() {return instance;}
    
    private static final Gson GSON = new Gson();
    
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
    
    /**
     * Decrypts JWT Token from given string
     * @param jwtToken String for JWT Token
     * @return JWTToken or null if any errors occurred
     */
    public JwtToken decryptJwtToken(String jwtToken) {
        String[] parts = StringUtils.split(jwtToken, '.'); //Strings own split uses regexp and . causes some headache....
        if(parts.length != 3) return null;
        String h = decode64(parts[0]);
        String p = decode64(parts[1]);
        String s = decode64(parts[2]);
        
        JwtHeader header = GSON.fromJson(h, JwtHeader.class); //Safe to use GSON here
        JwtPayload payload = GSON.fromJson(p, JwtPayload.class);
    
        JwtToken token = new JwtToken(header, payload, s);
        if(!token.validate()) 
            return null;
        
        return token;
    }
    
    /**
     * Creates JWT Token for user
     * @param user user
     * @return token
     */
    public JwtToken createJwtToken(String user) {
        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload();
        payload.setUser(user);
        payload.setPermission(getUserPermission(user));
        payload.setExp(getExpirationMin(180)); //3h expiration time
        
        String unsignedToken = encode64(header.toJson())+"."+encode64(payload.toJson());
        String signature;
        try {
            signature = encodeHS256(unsignedToken);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
        return new JwtToken(header, payload, signature);   
    }
    
    /*
     * Below Some Helper Methods for Encoding/Decoding with SHA256 and Base64 
     */
    private static final String SHA256 = "HmacSHA256";
    private static String encodeHS256(String str) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hs256 = Mac.getInstance(SHA256);
        SecretKeySpec keySpec = new SecretKeySpec(JWT_SECRET.getBytes(), SHA256);
        hs256.init(keySpec);
        return new String(hs256.doFinal(str.getBytes(Charset.defaultCharset())));
    }
    
    private static String decode64(String base64Str) {
        return new String(Base64.getDecoder().decode(base64Str));
    }
    
    private static String encode64(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }
    
    private static long getExpirationMin(int minutes) {
        return System.currentTimeMillis() + (minutes * 60000);
    }
    
    /*
     * JWT Token Classes Below 
     */
    private static final String JWT_SECRET = "omena"; //This is only for example purposes, real secret should be better
    public static class JwtToken {
        private final JwtHeader header;
        private final JwtPayload payload;
        private final String signature;
        
        public JwtToken(JwtHeader header, JwtPayload payload, String signature) {
            this.header = header; this.payload = payload; this.signature = signature;
        }
    
        /**
         * Creates JWT Token String to pass to user
         * @return JWT Token
         */
        public String toTokenString() {
            String h = encode64(header.toJson());
            String p = encode64(payload.toJson());
            String s = encode64(signature);
            return h+"."+p+"."+s;
        }
        
        public boolean validate() {
            long now = System.currentTimeMillis();
            if(!isSignatureValid()) {
                System.out.println("Signature not Valid!");
                return false;
            }
            if(payload.getExp() <= now) {
                System.out.println("Token has Expired!");
                return false;
            }
            return true;
        }
        
        private boolean isSignatureValid() {
            try {
                String uSigToken = encode64(header.toJson()) + "." + encode64(payload.toJson());
                String sig = encodeHS256(uSigToken);
                
                if(!sig.equals(signature)) {
                    System.out.println("Signature is not valid!");
                    return false; //Signatures did not match!
                }
                return true;
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
                return false;
            }
        }
    
        public JwtHeader getHeader() {
            return header;
        }
    
        public JwtPayload getPayload() {
            return payload;
        }
    
        public String getSignature() {
            return signature;
        }
    }
    
    public static class JwtHeader {
        private final String alg = "HS256";
        private final String typ = "JWT";
        
        public String toJson() {
            return GSON.toJson(this);
            //return  "{\"alg\":\""+alg+"\",\"typ\":\""+typ+"\"}";
        }
    }
    
    public static class JwtPayload {
        private String user;
        private Permission permission;
        private long exp, iat;
        
        public JwtPayload() {
            iat = System.currentTimeMillis();
        }
    
        public String getUser() {
            return user;
        }
    
        public void setUser(String user) {
            this.user = user;
        }
    
        public Permission getPermission() {
            return permission;
        }
    
        public void setPermission(Permission permission) {
            this.permission = permission;
        }
    
        public long getExp() {
            return exp;
        }
    
        public void setExp(long exp) {
            this.exp = exp;
        }
    
        public long getIat() {
            return iat;
        }
        
        public String toJson() {
            return GSON.toJson(this);
            //return "{\"user\":\""+user+"\",\"permission\":\""+permission+"\",\"exp\":"+exp+",\"iat\":"+iat+"}";
        }
    }
}
