package api.web.mappings;

public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthRequest(String name, String pass) {
        this.setUsername(name);
        this.setPassword(pass);
    }

}