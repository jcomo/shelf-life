package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;

public interface SessionsDAO {
    User findByToken(String token);
    String create(User user);
    void remove(User user);
}
