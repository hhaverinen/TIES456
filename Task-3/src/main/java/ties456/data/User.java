package ties456.data;

import ties456.service.SecureUserService;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Henri Haverinen
 * @version 05/10/16.
 */
@XmlRootElement
public class User {

    private String username, password;
    private SecureUserService.Permission permission;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SecureUserService.Permission getPermission() {
        return permission;
    }

    public void setPermission(SecureUserService.Permission permission) {
        this.permission = permission;
    }

}
