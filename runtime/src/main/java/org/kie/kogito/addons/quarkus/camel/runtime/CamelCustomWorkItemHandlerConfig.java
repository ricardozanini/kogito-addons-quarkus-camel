package org.kie.kogito.addons.quarkus.camel.runtime;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kie.kogito.process.impl.CachedWorkItemHandlerConfig;

@ApplicationScoped
public class CamelCustomWorkItemHandlerConfig extends CachedWorkItemHandlerConfig {

    @Inject
    CamelCustomWorkItemHandler handler;

    @PostConstruct
    void init() {
        register(handler.getName(), handler);
    }

}
