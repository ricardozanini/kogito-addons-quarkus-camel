package org.kie.kogito.addons.quarkus.camel.runtime;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class CamelConfigSource implements ConfigSource {

    static final Integer ORDINAL = Integer.MIN_VALUE;

    private final Map<String, String> configuration;

    public CamelConfigSource(Map<String, String> configuration) {
        this.configuration = Collections.unmodifiableMap(configuration);
    }

    @Override
    public Set<String> getPropertyNames() {
        return configuration.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return configuration.get(propertyName);
    }

    @Override
    public String getName() {
        return CamelConfigSource.class.getSimpleName();
    }

    @Override
    public int getOrdinal() {
        return ORDINAL;
    }
}
