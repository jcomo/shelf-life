package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.core.StorageMethod;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class StorageGuideParserTest {
    private static final StorageGuideParser PARSER = new StorageGuideParser();

    @Test
    public void testItReturnsNothingWhenNoName() throws Exception {
        assertThat(PARSER.parse("<html></html>")).isEqualTo(Optional.empty());
    }

    @Test
    public void testItReturnsStorageGuide() throws Exception {
        List<StorageMethod> methods = Arrays.asList(
                new StorageMethod("Refrigerator", "3-4 days"),
                new StorageMethod("Freezer", "10-12 months"));

        List<String> tips = Arrays.asList(
                "Wrap cut melon tightly with plastic wrap or foil, or store in airtight container.",
                "To freeze, place cut up melon in covered airtight containers or heavy-duty freezer bags.",
                "Freezer time shown is for best quality only — foods kept constantly frozen at 0° F will keep safe indefinitely.");

        StorageGuide expected = new StorageGuide("Watermelon — Fresh, Raw, Cut Up", methods, tips);
        assertThat(PARSER.parse(fixture("guide.html"))).isEqualTo(Optional.of(expected));
    }
}