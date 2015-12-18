package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(User.Mapper.class)
public interface UsersDAO {
    @SqlUpdate("insert into users (username, password_hash) values (:username, :passwordHash)")
    void create(@BindBean User user);

    @SqlUpdate("update users set session_id = :sessionId where username = :username")
    void updateSession(@BindBean User user);

    @SqlQuery("select * from users where username = :username")
    User findByUsername(@Bind("username") String username);
}
