package org.kie.kogito.addons.quarkus.camel.runtime;

import java.util.Iterator;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.serverless.workflow.WorkflowWorkItemHandler;

@ApplicationScoped
public class CamelCustomWorkItemHandler extends WorkflowWorkItemHandler {

    public static final String OPERATION = "operation";
    public static final String NAME = "CamelCustomWorkItemHandler";

    @Inject
    CamelContext context;

    @Override
    protected Object internalExecute(KogitoWorkItem workItem, Map<String, Object> parameters) {
        Iterator<?> iter = parameters.values().iterator();
        Map<String, Object> metadata = workItem.getNodeInstance().getNode().getMetaData();
        String camelRouteURI = (String) metadata.get(OPERATION);
        if (camelRouteURI == null) {
            throw new IllegalArgumentException("Operation (the Camel Route identifier) is a mandatory parameter");
        }
        return context.createProducerTemplate().requestBody(camelRouteURI, iter.next());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
