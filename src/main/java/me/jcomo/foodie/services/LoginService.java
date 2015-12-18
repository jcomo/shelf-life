package me.jcomo.foodie.services;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.SessionsDAO;
import me.jcomo.foodie.db.UsersDAO;

import java.util.Optional;

public class LoginService {
    private final UsersDAO users;
    private final SessionsDAO sessions;

    public LoginService(UsersDAO users, SessionsDAO sessions) {
        this.users = users;
        this.sessions = sessions;
    }

    public Optional<String> login(String username, String password) {
        User user = users.findByUsername(username);
        if (user != null) {
            if (user.passwordMatches(password)) {
                return Optional.of(createOrFindSession(user));
            }
        }

        return Optional.empty();
    }

    private String createOrFindSession(User user) {
        String existingSessionId = user.getSessionId();
        User existingSession = sessions.findByToken(user.getSessionId());
        if (existingSession != null) {
            return existingSessionId;
        } else {
            return createNewSession(user);
        }
    }

    private String createNewSession(User user) {
        String sessionId = sessions.create(user);
        user.setSessionId(sessionId);
        users.updateSession(user);
        return sessionId;
    }

    public boolean logout(User user) {
        if (null == user) {
            return false;
        } else {
            sessions.remove(user);
            return true;
        }
    }
}
