package me.jcomo.foodie.cli;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import me.jcomo.foodie.FoodieConfiguration;
import me.jcomo.stilltasty.client.StillTastyClient;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintStream;

public class UpdateFixturesCommand extends ConfiguredCommand<FoodieConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFixturesCommand.class);

    public UpdateFixturesCommand() {
        super("update-fixtures", "Update the test fixtures from stilltasty.com");
    }

    @Override
    protected void run(Bootstrap<FoodieConfiguration> bootstrap,
                       Namespace namespace,
                       FoodieConfiguration config) throws Exception {

        final StillTastyClient client = new StillTastyClient(HttpClients.createDefault());

        updateFixture("src/test/resources/guide.html", client.fetchGuide(18665));
        updateFixture("src/test/resources/search.html", client.fetchSearchResults("watermelon"));
    }

    private void updateFixture(String fixturePath, String newFixture) throws Exception {
        File fixtureFile = new File(fixturePath);
        try (PrintStream out = new PrintStream(fixtureFile)) {
            out.print(newFixture);
            LOGGER.info("updated " + fixtureFile.getAbsolutePath());
        }
    }
}
