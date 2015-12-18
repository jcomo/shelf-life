package me.jcomo.foodie.services;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.UsersDAO;

import java.util.Optional;

public class RegistrationService {
    private final UsersDAO users;

    public RegistrationService(UsersDAO users) {
        this.users = users;
    }

    public Optional<User> register(String username, String password) {
        final User existingUser = users.findByUsername(username);
        if (existingUser != null) {
            return Optional.empty();
        } else {
            return Optional.of(createNewUser(username, password));
        }
    }

    private User createNewUser(String username, String password) {
        final User newUser = new User(username, password);
        users.create(newUser);
        return newUser;
    }
}
