package me.jcomo.foodie.services;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.SessionDAO;
import me.jcomo.foodie.db.UserDAO;

import java.util.Optional;

public class LoginService {
    private final UserDAO users;
    private final SessionDAO sessions;

    public LoginService(UserDAO users, SessionDAO sessions) {
        this.users = users;
        this.sessions = sessions;
    }

    public Optional<String> login(String username, String password) {
        User user = users.findByUsername(username);
        if (user != null) {
            if (user.passwordMatches(password)) {
                // TODO: if someone continues to login, this will create many session tokens (bad)
                return Optional.of(sessions.create(user));
            }
        }

        return Optional.empty();
    }
}
