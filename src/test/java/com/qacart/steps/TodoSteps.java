package com.qacart.steps;

import com.github.javafaker.Faker;
import com.qacart.apis.TodoApi;
import com.qacart.models.Todo;
import com.qacart.models.User;
import io.restassured.response.Response;

public class TodoSteps {
    public static Todo generateTodos() {
        Faker faker = new Faker();
        String item = faker.book().title();
        boolean isCompleted = false;
        return new Todo(isCompleted, item);
    }

    public static String getIDForTodo(Todo todo, String token) {
        Response response = TodoApi.addTodo(todo, token);
        return response.path("_id");
    }


}
