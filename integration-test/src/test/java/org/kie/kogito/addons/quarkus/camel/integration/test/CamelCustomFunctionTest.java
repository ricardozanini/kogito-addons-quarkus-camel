package org.kie.kogito.addons.quarkus.camel.integration.test;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CamelCustomFunctionTest {

    @Test
    void verifyBodyAndHeaders() {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("{ \"numbers\": 2 }")
                .post("/send_body_headers")
                .then()
                .statusCode(201)
                .assertThat()
                .body("workflowdata.id", CoreMatchers.notNullValue(String.class));
    }
}
