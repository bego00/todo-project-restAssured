package com.qacart.steps;

import com.github.javafaker.Faker;
import com.qacart.apis.UserApi;
import com.qacart.models.Error;
import com.qacart.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "1234546";
        return new User(firstName, lastName, email, password);
    }

    public static User getRegisteredUser() {
        User user = generateUser();
        UserApi.register(user);
        return user;
    }

    public static String generateToken() {
        User user = generateUser();
        Response response = UserApi.register(user);
        return response.path("access_token");
    }

//    public static String getErrorMessageForRegisterUser() {
//        User user = getRegisteredUser();
//        Response response = UserApi.register(user);
//        Error error = response.body().as(Error.class);
//        return error.getMessage();
//    }
}
