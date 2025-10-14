package org.innowise.example;

import org.innowise.annotation.Autowired;
import org.innowise.annotation.Component;
import org.innowise.lifecycle.InitializingBean;

@Component
public class UserService implements InitializingBean {

    @Autowired
    private UserRepository userRepository;

    public String getUserInfo() {
        return userRepository.findUser() + " обработан через UserService";
    }

    public String getUserInfoById(int id) {
        return userRepository.findUserById(id) + " обработан через UserService";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService инициализирован! Все зависимости внедрены.");
    }
}
