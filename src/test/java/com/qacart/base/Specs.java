package com.qacart.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static String getEnv() {
        String env = System.getProperty("env", "PRODUCTION");
        String baseURL;
        switch (env) {
            case "PRODUCTION":
                baseURL = "https://qacart-todo.herokuapp.com";
                break;
            case "LOCAL":
                baseURL = "http://localhost:8080";
                break;
            default:
                throw new RuntimeException("Unknown environment");

        }
        return baseURL;
    }

    public static RequestSpecification resSpec() {

        return given()
                .baseUri(getEnv())
                .contentType(ContentType.JSON)
                .log().all();
    }
}
