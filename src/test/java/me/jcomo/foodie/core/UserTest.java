package me.jcomo.foodie.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private final User user = new User("jonathan", "password");

    @Test
    public void testPasswordHashes() throws Exception {
        final User similarUser = new User("peter", "password");

        assertThat(user.getPasswordHash()).doesNotMatch("password");
        assertThat(user.getPasswordHash()).doesNotMatch(similarUser.getPasswordHash());
    }

    @Test
    public void testPasswordMatchesReturnsTrueForMatch() throws Exception {
        assertThat(user.passwordMatches("password")).isTrue();
    }

    @Test
    public void testPasswordMatchesReturnsFalseForNoMatch() throws Exception {
        assertThat(user.passwordMatches("something else")).isFalse();
    }
}