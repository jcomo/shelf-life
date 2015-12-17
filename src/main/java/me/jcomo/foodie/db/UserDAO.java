package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDAO {
    private final Map<String, User> users;

    public UserDAO() {
        this.users = new ConcurrentHashMap<>();
    }

    public void create(User user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
