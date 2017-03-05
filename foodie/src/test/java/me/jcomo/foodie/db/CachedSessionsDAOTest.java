package me.jcomo.foodie.db;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.wrapper.Cache;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CachedSessionsDAOTest {
    private final User user = new User("jonathan", "password");

    private final UsersDAO dao = mock(UsersDAO.class);

    @SuppressWarnings("unchecked")
    private final Cache<String, String> cache = mock(Cache.class);

    private final CachedSessionsDAO sessions = new CachedSessionsDAO(cache, dao);

    @After
    public void tearDown() throws Exception {
        reset(cache, dao);
    }

    @Test
    public void testFindByTokenReturnsNothingWhenTokenDoesntExist() throws Exception {
        when(cache.get(anyString())).thenReturn(null);
        User user = sessions.findByToken("token");

        assertThat(user).isNull();
        verify(cache).get(eq("token"));
        verify(dao, never()).findByUsername(anyString());
    }

    @Test
    public void testFindByTokenReturnsUserWhenTokenExists() throws Exception {
        when(cache.get(eq("token"))).thenReturn("jonathan");
        when(dao.findByUsername(eq("jonathan"))).thenReturn(user);

        User foundUser = sessions.findByToken("token");

        assertThat(foundUser).isEqualTo(user);
        verify(cache).get(eq("token"));
        verify(dao).findByUsername("jonathan");
    }

    @Test
    public void testCreateAddsNewSessionToCache() throws Exception {
        String sessionId = sessions.create(user);

        assertThat(sessionId).isNotEmpty();
        verify(cache).set(eq(sessionId), eq("jonathan"), anyInt());
    }

    @Test
    public void testRemoveTakesSessionOutOfCache() throws Exception {
        user.setSessionId("session");

        sessions.remove(user);
        verify(cache).delete(eq("session"));
    }
}