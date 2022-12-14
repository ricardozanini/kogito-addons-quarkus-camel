package org.kie.kogito.addons.quarkus.camel.deployment;

import org.jbpm.ruleflow.core.RuleFlowNodeContainerFactory;
import org.jbpm.ruleflow.core.factory.WorkItemNodeFactory;
import org.kie.kogito.serverless.workflow.parser.ParserContext;
import org.kie.kogito.serverless.workflow.parser.types.WorkItemTypeHandler;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.functions.FunctionDefinition;

import static org.kie.kogito.addons.quarkus.camel.runtime.CamelCustomWorkItemHandler.NAME;
import static org.kie.kogito.addons.quarkus.camel.runtime.CamelCustomWorkItemHandler.OPERATION;
import static org.kie.kogito.serverless.workflow.parser.FunctionTypeHandlerFactory.trimCustomOperation;

/**
 * Implementation for the custom Camel Function Definition
 */
public class CamelTypeHandler extends WorkItemTypeHandler {

    public CamelTypeHandler() {
    }

    @Override
    protected <T extends RuleFlowNodeContainerFactory<T, ?>> WorkItemNodeFactory<T> fillWorkItemHandler(Workflow workflow, ParserContext context, WorkItemNodeFactory<T> node,
            FunctionDefinition functionDef) {
        CamelFunctionStaticValidator.validateFunctionDefinition(workflow, functionDef.getName());
        return node.workName(NAME).metaData(OPERATION, trimCustomOperation(functionDef));
    }

    @Override
    public String type() {
        return "camel";
    }

}
