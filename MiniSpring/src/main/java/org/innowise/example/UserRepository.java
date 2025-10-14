package org.innowise.example;

import org.innowise.annotation.Autowired;
import org.innowise.annotation.Component;
import org.innowise.lifecycle.InitializingBean;

@Component
public class UserRepository {

    public String findUser() {
        return "User: Dayan";
    }

    public String findUserById(int id) {
        return "User #" + id + ": Dayan";
    }
}
