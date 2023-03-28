package com.qacart.todo;

import com.qacart.apis.TodoApi;
import com.qacart.data.ErrorMessages;
import com.qacart.models.Error;
import com.qacart.models.Todo;
import com.qacart.steps.TodoSteps;
import com.qacart.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.qacart.steps.TodoSteps.generateTodos;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Todo Feature")
public class TodoTest {
    @Story("should be abel to Add Todo")
    @Test(description = "should be abel to Add Todo")
    public void AddTodo() {

        Todo todo = generateTodos();
        String token = UserSteps.generateToken();
        Response response = TodoApi.addTodo(todo, token);
        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));

    }

    @Story("should Not be able to AddTodo if Is_Completed missing ")
    @Test(description = "should Not be able to AddTodo if Is_Completed missing ")
    public void shouldNotAddTodoIfIsCompletedMissing() {

        Todo todo1 = new Todo("book");

        String token = UserSteps.generateToken();
        Response response = TodoApi.addTodo(todo1, token);
        Error error = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(error.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }

    @Story("Should get Todo By Id")
    @Test(description = "Should get Todo By Id")
    public void getTodoByID() {
        String token = UserSteps.generateToken();
        Todo todo = generateTodos();
        String ID = TodoSteps.getIDForTodo(todo, token);

        Response response = TodoApi.getTodoById(token, ID);

        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }

    @Story("Should get All Todos")
    @Test(description = "Should get All Todos")
    public void getAllTodos() {
        String token = UserSteps.generateToken();
        Response response = TodoApi.getAllTodo(token);
        assertThat(response.getStatusCode(), equalTo(200));

    }

    @Story("Should delete a Todo By ID")
    @Test(description = "Should delete a Todo By ID")
    public void deleteTodoByID() {
        String token = UserSteps.generateToken();
        Todo todo = generateTodos();
        String ID = TodoSteps.getIDForTodo(todo, token);

        Response response = TodoApi.deleteTodoById(token, ID);

        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }
}
