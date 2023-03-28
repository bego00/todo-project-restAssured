package com.qacart.apis;

import com.qacart.base.Specs;
import com.qacart.data.Route;
import com.qacart.models.Todo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response addTodo(Todo todo, String token) {
        return given()
                .spec(Specs.resSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post(Route.TODOS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response getTodoById(String token, String ID) {
        return given()
                .spec(Specs.resSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS_ROUTE + "/" + ID)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response getAllTodo(String token) {
        return given()
                .spec(Specs.resSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodoById(String token, String ID) {
        return given()
                .spec(Specs.resSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOS_ROUTE + "/" + ID)
                .then()
                .log().all()
                .extract().response();
    }
}
