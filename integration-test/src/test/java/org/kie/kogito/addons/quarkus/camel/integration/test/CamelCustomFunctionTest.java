package org.kie.kogito.addons.quarkus.camel.integration.test;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.emptyOrNullString;

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
                .body("workflowdata.id", notNullValue(String.class))
                .body("workflowdata.numbers", equalTo(2));
    }

    @Test
    void verifyBody() {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("{ \"numbers\": 2 }")
                .post("/send_body")
                .then()
                .statusCode(201)
                .assertThat()
                .body("workflowdata.id", emptyOrNullString())
                .body("workflowdata.numbers", equalTo(2));
    }

    @Test
    void verifyArbitraryBody() {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("{ \"numbers\": 2 }")
                .post("/send_arbitrary_body")
                .then()
                .statusCode(201)
                .assertThat()
                .body("workflowdata.id", emptyOrNullString())
                .body("workflowdata.numbers", equalTo(2));
    }

    @Test
    void verifyNoArgs() {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("{ \"numbers\": 2 }")
                .post("/send_nothing")
                .then()
                .statusCode(201)
                .assertThat()
                .body("workflowdata.id", emptyOrNullString())
                .body("workflowdata.numbers", equalTo(2));
    }
}
