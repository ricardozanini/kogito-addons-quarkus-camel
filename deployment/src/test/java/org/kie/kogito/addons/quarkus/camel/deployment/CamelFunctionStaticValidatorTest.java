package org.kie.kogito.addons.quarkus.camel.deployment;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.serverlessworkflow.api.Workflow;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kie.kogito.addons.quarkus.camel.deployment.CamelFunctionStaticValidator.validateFunctionDefinition;
import static org.kie.kogito.quarkus.serverless.workflow.WorkflowCodeGenUtils.getWorkflows;

public class CamelFunctionStaticValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "camel-soap.sw.json,callSoap",
            "newsletter-subscription.sw.json,subscribeToNewsletter",
            "camel-soap-no-args.sw.json,callSoap",
            "camel-soap-expression-args.sw.json,callSoap"
    })
    public void verifyValidWorkflowsWithCamelFunctions(final String workflowFile, final String functionDefName) throws URISyntaxException {
        final Stream<Workflow> workflows = getWorkflows(Stream.of(
                Paths.get(requireNonNull(CamelFunctionStaticValidatorTest.class.getResource("/" + workflowFile)).toURI())));
        workflows.forEach(w -> validateFunctionDefinition(w, functionDefName));
    }

    @ParameterizedTest
    @CsvSource({ "camel-soap-invalid.sw.json,callSoap" })
    public void verifyInvalidWorkflowsWithCamelFunctions(final String workflowFile, final String functionDefName) throws URISyntaxException {
        final Stream<Workflow> workflows = getWorkflows(Stream.of(
                Paths.get(requireNonNull(CamelFunctionStaticValidatorTest.class.getResource("/" + workflowFile)).toURI())));
        workflows.forEach(w -> assertThrows(IllegalArgumentException.class, () -> validateFunctionDefinition(w, functionDefName)));
    }

}
