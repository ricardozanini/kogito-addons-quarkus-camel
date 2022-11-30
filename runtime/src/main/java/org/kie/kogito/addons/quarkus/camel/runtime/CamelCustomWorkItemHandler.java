package org.kie.kogito.addons.quarkus.camel.runtime;

import java.util.Iterator;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.serverless.workflow.WorkflowWorkItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CamelCustomWorkItemHandler extends WorkflowWorkItemHandler {

    public static final String OPERATION = "operation";
    public static final String NAME = "CamelCustomWorkItemHandler";

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelCustomWorkItemHandler.class);

    @Inject
    CamelContext context;

    @Override
    protected Object internalExecute(KogitoWorkItem workItem, Map<String, Object> parameters) {
        final Iterator<?> iter = parameters.values().iterator();
        final Map<String, Object> metadata = workItem.getNodeInstance().getNode().getMetaData();
        final String camelEndpoint = (String) metadata.get(OPERATION);

        checkEndpointExists(camelEndpoint);

        if (iter.hasNext()) {
            final Object args = iter.next();
            LOGGER.debug("Invoking Camel Endpoint '{}' with arguments '{}'", camelEndpoint, args);
            return context.createProducerTemplate().requestBody(camelEndpoint, iter.next());
        } else {
            LOGGER.debug("Invoking Camel Endpoint '{}' with no arguments", camelEndpoint);
            return context.createProducerTemplate().requestBody(camelEndpoint);
        }
    }

    private void checkEndpointExists(final String endpoint) {
        if (endpoint == null) {
            throw new IllegalArgumentException("Operation (the Camel Endpoint Identifier) is a mandatory parameter");
        }
        if (context.hasEndpoint(endpoint) == null) {
            throw new IllegalArgumentException("Endpoint '" + endpoint + "' doesn't exist. Make sure that the Camel Route is within the project's context.");
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
