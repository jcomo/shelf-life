package me.jcomo.stilltasty.core;

import org.junit.Test;

import static me.jcomo.stilltasty.core.Humanize.capitalize;
import static me.jcomo.stilltasty.core.Humanize.titleize;
import static org.assertj.core.api.Assertions.assertThat;

public class HumanizeTest {
    @Test
    public void testTitleize() throws Exception {
        assertThat(titleize("WATERMELON - FRESH, CUT")).isEqualTo("Watermelon - Fresh, Cut");
        assertThat(titleize("one day not long ago")).isEqualTo("One Day Not Long Ago");
        assertThat(titleize("history of the world")).isEqualTo("History of the World");
    }

    @Test
    public void testCapitalize() throws Exception {
        assertThat(capitalize("something")).isEqualTo("Something");
        assertThat(capitalize("two words")).isEqualTo("Two words");
        assertThat(capitalize("Already done")).isEqualTo("Already done");
    }
}