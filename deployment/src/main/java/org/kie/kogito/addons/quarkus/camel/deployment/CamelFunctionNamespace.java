package org.kie.kogito.addons.quarkus.camel.deployment;

import org.jbpm.ruleflow.core.RuleFlowNodeContainerFactory;
import org.jbpm.ruleflow.core.factory.WorkItemNodeFactory;
import org.kie.kogito.serverless.workflow.functions.WorkItemFunctionNamespace;
import org.kie.kogito.serverless.workflow.parser.ParserContext;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.functions.FunctionRef;

import static org.kie.kogito.addons.quarkus.camel.runtime.CamelCustomWorkItemHandler.NAME;
import static org.kie.kogito.addons.quarkus.camel.runtime.CamelCustomWorkItemHandler.OPERATION;
import static org.kie.kogito.serverless.workflow.parser.FunctionNamespaceFactory.getFunctionName;

/**
 * Implementation of the custom Camel Namespace Function Reference
 */
public class CamelFunctionNamespace extends WorkItemFunctionNamespace {

    @Override
    protected <T extends RuleFlowNodeContainerFactory<T, ?>> WorkItemNodeFactory<T> fillWorkItemHandler(Workflow workflow, ParserContext parserContext, WorkItemNodeFactory<T> workItemNodeFactory,
            FunctionRef functionRef) {
        CamelFunctionStaticValidator.validateFunctionRef(functionRef);
        return workItemNodeFactory.workName(NAME).metaData(OPERATION, getFunctionName(functionRef));
    }

    @Override
    public String namespace() {
        return "camel";
    }
}
