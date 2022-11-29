package org.kie.kogito.addons.quarkus.camel.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;

/**
 * Configuration Source Factory for default Camel properties when integrating with Kogito Serverless Workflow.
 * The client application can override these properties.
 */
public final class CamelConfigSourceFactory implements ConfigSourceFactory {

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
        Map<String, String> configuration = new HashMap<>();

        // default Kogito Quarkus Camel Route configuration
        configuration.put("camel.main.routes-reload-enabled", "true");
        configuration.put("camel.main.routes-reload-directory", "src/main/resources/routes");
        configuration.put("camel.main.routes-reload-pattern", "*.xml,*.yaml,*.yml");
        configuration.put("camel.main.routes-include-pattern", "routes/*.xml,routes/*.yaml,routes/*.yml");

        return List.of(new CamelConfigSource(configuration));
    }

    @Override
    public OptionalInt getPriority() {
        return OptionalInt.of(CamelConfigSource.ORDINAL);
    }
}
