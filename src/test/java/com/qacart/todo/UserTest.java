package com.qacart.todo;

import com.qacart.apis.UserApi;
import com.qacart.data.ErrorMessages;
import com.qacart.models.Error;
import com.qacart.models.User;
import com.qacart.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("User Feature")
public class UserTest {
    @Story("Should be able to Register")
    @Test(description = "Should be able to Register")
    public void shouldBeAbleToRegister() {
        User user = UserSteps.generateUser();

        Response response = UserApi.register(user);

        User returnUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Story("Should Not Be Able To Register With The Same Email")
    @Test(description = "Should Not Be Able To Register With The Same Email")
    public void shouldNotBeAbleToRegisterWithTheSameEmail() {
        User user = UserSteps.getRegisteredUser();

        Response response = UserApi.register(user);

        Error error = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(error.getMessage(), equalTo(ErrorMessages.ErrorMessage));
    }

    @Story("should be able to login")
    @Test(description = "should be able to login")
    public void LoginTest() {
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), user.getPassword());
        Response response = UserApi.login(loginData);

        User returnUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Story("should Not be abel to login with Incorrect Password")
    @Test(description = "should Not be abel to login with Incorrect Password")
    public void shouldNotBeLoginWithIncorrectPassword() {
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), "123456355");

        Response response = UserApi.login(loginData);
        Error error = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(error.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));
    }

    @Story("Get All Users")
    @Test(description = "Get All Users")
    public void getAllUsers() {
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .when().get("/api/v1/users")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(200));
    }

}

