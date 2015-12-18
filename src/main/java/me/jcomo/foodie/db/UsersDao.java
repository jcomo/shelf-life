package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;

public interface UsersDAO {
    void create(User user);
    User findByUsername(String username);
}
