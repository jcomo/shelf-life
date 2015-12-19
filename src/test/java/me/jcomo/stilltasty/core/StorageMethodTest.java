package me.jcomo.stilltasty.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StorageMethodTest {

    public void assertExpirationEquals(String expiration, long time) {
        final StorageMethod method = new StorageMethod("", expiration);
        assertThat(method.getExpirationTime()).isEqualTo(time);
    }

    public void assertDoesNotExpire(String expiration) {
        final StorageMethod method = new StorageMethod("", expiration);
        assertThat(method.getExpirationTime()).isNull();
    }

    @Test
    public void testExpirationTimeParsesCorrectly() throws Exception {
        assertExpirationEquals("1 day", 60 * 60 * 24);
        assertExpirationEquals("2 weeks", 60 * 60 * 24 * 7 * 2);
        assertExpirationEquals("2-3 months", 60 * 60 * 24 * 30 * 2);
        assertExpirationEquals("1 year", 60 * 60 * 24 * 365);
    }

    @Test
    public void testExpiationIsNullWhenNoValidTime() throws Exception {
        assertDoesNotExpire(null);
        assertDoesNotExpire("");
        assertDoesNotExpire("forever");
    }
}