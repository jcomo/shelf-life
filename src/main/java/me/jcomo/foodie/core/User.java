package me.jcomo.foodie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mindrot.jbcrypt.BCrypt;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Principal {
    private int id;

    private String username;

    @JsonIgnore
    private String passwordHash;

    @JsonIgnore
    private String sessionId = "";

    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    public int getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean passwordMatches(String password) {
        return BCrypt.checkpw(password, passwordHash);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public static class Mapper implements ResultSetMapper<User> {
        @Override
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            User u = new User();
            u.id = r.getInt("id");
            u.username = r.getString("username");
            u.passwordHash = r.getString("password_hash");
            u.sessionId = r.getString("session_id");
            return u;
        }
    }
}