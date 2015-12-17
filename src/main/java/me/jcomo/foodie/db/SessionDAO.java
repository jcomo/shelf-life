package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.wrapper.Cache;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class SessionDAO {
    private static final int TTL = 60 * 60 * 24;
    private static final Random SOURCE = new SecureRandom();

    private final Cache<String, String> cache;
    private final UserDAO users;

    public SessionDAO(Cache<String, String> cache, UserDAO users) {
        this.cache = cache;
        this.users = users;
    }

    public User findByToken(String token) {
        String username = cache.get(token);
        if (username != null) {
            return users.findByUsername(username);
        }

        return null;
    }

    public String create(User user) {
        String sessionId = nextSessionId();
        cache.set(sessionId, user.getName(), TTL);
        return sessionId;
    }

    private String nextSessionId() {
        return new BigInteger(130, SOURCE).toString(32);
    }
}
