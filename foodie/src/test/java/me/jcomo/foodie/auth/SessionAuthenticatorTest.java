package me.jcomo.foodie.auth;

import com.google.common.base.Optional;
import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.SessionsDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SessionAuthenticatorTest {
    private final User user = new User("jonathan", "password");
    private final SessionsDAO dao = mock(SessionsDAO.class);
    private final SessionAuthenticator authenticator = new SessionAuthenticator(dao);

    @Before
    public void setUp() throws Exception {
        when(dao.findByToken("token")).thenReturn(user);
    }

    @After
    public void tearDown() throws Exception {
        reset(dao);
    }

    @Test
    public void testAuthenticateReturnsNothingWhenNoSessionFound() throws Exception {
        assertThat(authenticator.authenticate("nothing")).isEqualTo(Optional.absent());
    }

    @Test
    public void testAuthenticateReturnsUserWhenSessionFound() throws Exception {
        assertThat(authenticator.authenticate("token")).isEqualTo(Optional.of(user));
    }
}