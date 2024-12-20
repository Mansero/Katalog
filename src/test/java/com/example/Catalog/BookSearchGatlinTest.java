package com.example.Catalog;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.*;

public class BookSearchGatlinTest extends Simulation {

    //Add Basic
    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("application/json")
                    .contentTypeHeader("application/json");

    //Add Scenario
    ScenarioBuilder searchBookScenario = scenario("Search Book Scenario")
            .exec(http("Search Books Scenario")
                    .get("/api/books/search")
                    .queryParam("searchTerm", "Java")
                    .check(status().is(200))
                    .check(jsonPath("$[0].title").exists()));

    //Add Testconfiguration
    {
        setUp(
                searchBookScenario.injectOpen(
                        atOnceUsers(100) // 100 Benutzer (gleichzeitig gesendete Anfragen)
                )
        ).protocols(httpProtocol);
    }
}

