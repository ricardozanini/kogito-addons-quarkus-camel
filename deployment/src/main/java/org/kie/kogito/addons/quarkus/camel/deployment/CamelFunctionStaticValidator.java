package org.kie.kogito.addons.quarkus.camel.deployment;

import java.util.Objects;

import org.kie.kogito.addons.quarkus.camel.runtime.CamelFunctionArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.actions.Action;
import io.serverlessworkflow.api.functions.FunctionRef;
import io.serverlessworkflow.api.interfaces.State;
import io.serverlessworkflow.api.states.CallbackState;
import io.serverlessworkflow.api.states.ForEachState;
import io.serverlessworkflow.api.states.OperationState;

/**
 * Static validation for Workflow DSL in build time.
 */
public final class CamelFunctionStaticValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelFunctionStaticValidator.class);

    private CamelFunctionStaticValidator() {
    }

    /**
     * Validation for the Function Definition within the given Workflow
     *
     * @param workflow        the workflow reference
     * @param functionDefName the name of the function definition
     * @throws IllegalArgumentException if the referenced function has more than one argument in the call
     */
    public static void validateFunctionDefinition(final Workflow workflow, final String functionDefName) {
        for (State state : workflow.getStates()) {
            switch (state.getType()) {
                case OPERATION:
                    ((OperationState) state).getActions().forEach(action -> validateAction(action, functionDefName));
                    break;
                case FOREACH:
                    ((ForEachState) state).getActions().forEach(action -> validateAction(action, functionDefName));
                    break;
                case CALLBACK:
                    validateAction(((CallbackState) state).getAction(), functionDefName);
                    break;
            }
        }
    }

    /**
     * Validation for the Function Reference
     *
     * @param functionRef the given Function Reference
     * @throws IllegalArgumentException if the there's more than one argument in the function call
     */
    public static void validateFunctionRef(final FunctionRef functionRef) {
        final JsonNode jsonNode = functionRef.getArguments();
        if (jsonNode == null) {
            return;
        }
        if (jsonNode.size() > 2) {
            throw new IllegalArgumentException("Camel functions only support 'body', 'header', or no arguments. Please review the arguments: \n" + jsonNode.asText());
        }
        final JsonNode headers = jsonNode.get(CamelFunctionArgs.HEADERS);
        if (headers != null && (headers.isArray() || !headers.isObject())) {
            throw new IllegalArgumentException("Camel functions headers arguments must be either an object or array. Please review the arguments: \n" + headers.asText());
        }
        if (jsonNode.get(CamelFunctionArgs.BODY) == null) {
            LOGGER.warn("No body arguments found in the function reference '{}'. The first parameter will be used as the Camel message body. Please use 'body: { }'.", functionRef.getRefName());
        }
    }

    private static void validateAction(final Action action, final String functionDefName) {
        if (action != null &&
                action.getFunctionRef() != null &&
                Objects.equals(action.getFunctionRef().getRefName(), functionDefName)) {
            validateFunctionRef(action.getFunctionRef());
        }
    }
}
