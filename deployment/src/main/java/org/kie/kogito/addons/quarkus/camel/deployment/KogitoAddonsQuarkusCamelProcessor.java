package org.kie.kogito.addons.quarkus.camel.deployment;

import org.kie.kogito.quarkus.addons.common.deployment.KogitoCapability;
import org.kie.kogito.quarkus.addons.common.deployment.RequireCapabilityKogitoAddOnProcessor;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class KogitoAddonsQuarkusCamelProcessor extends RequireCapabilityKogitoAddOnProcessor {

    private static final String FEATURE = "kogito-addons-quarkus-camel";

    public KogitoAddonsQuarkusCamelProcessor() {
        super(KogitoCapability.SERVERLESS_WORKFLOW);
    }

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

}
