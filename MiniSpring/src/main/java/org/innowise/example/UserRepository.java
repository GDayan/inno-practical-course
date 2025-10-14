package org.innowise.example;

import org.innowise.annotation.Component;

@Component
public class UserRepository {

    public String findUser() {
        return "User: Dayan";
    }

    public String findUserById(int id) {
        return "User #" + id + ": Dayan";
    }
}
