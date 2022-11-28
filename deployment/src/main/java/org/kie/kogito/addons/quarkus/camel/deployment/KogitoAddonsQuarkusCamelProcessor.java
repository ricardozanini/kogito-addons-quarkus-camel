package org.kie.kogito.addons.quarkus.camel.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class KogitoAddonsQuarkusCamelProcessor {

    private static final String FEATURE = "kogito-addons-quarkus-camel";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
