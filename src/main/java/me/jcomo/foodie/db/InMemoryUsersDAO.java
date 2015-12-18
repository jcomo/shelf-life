package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUsersDAO implements UsersDAO {
    private final Map<String, User> users;

    public InMemoryUsersDAO() {
        this.users = new ConcurrentHashMap<>();
    }

    public void create(User user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    public void updateSession(User user) {
        // noop - the user reference will be updated in the map automatically
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
