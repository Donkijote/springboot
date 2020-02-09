package api.web.mappings;

import api.web.models.Users;

/**
 * Signup
 */
public class Signup extends Users {

    public Signup(String name, String lastName, String userName, String email, String password) {
        super(name, lastName, userName, email, password);
    }

}