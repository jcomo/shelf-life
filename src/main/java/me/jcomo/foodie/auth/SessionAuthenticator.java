package me.jcomo.foodie.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.SessionsDAO;


public class SessionAuthenticator implements Authenticator<String, User> {
    private SessionsDAO sessions;

    public SessionAuthenticator(SessionsDAO sessions) {
        this.sessions = sessions;
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return Optional.fromNullable(sessions.findByToken(token));
    }
}

