package ru.praktikumservices.stand.qadesk.client;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;

import static io.restassured.RestAssured.given;

import java.util.Map;
import ru.praktikumservices.stand.qadesk.models.Client;


public class ClientClient {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();

    public Response createUserViaApi(Client client) {
        String email = client.getEmail();
        String password = client.getPassword();

        Response response = given()
                .spec(baseHttpClient.requestSpecification)
                .log().all()
                .basePath("signup")
                .body(Map.of(
                        "email", email,
                        "password", password,
                        "submitPassword", password
                ))
                .post();

        // Проверяем, что запрос успешен (201 Created или 200 OK)
        response.then().statusCode(201);
        return response;
    }

    public Response loginUserViaApi(Client client) {
        String email = client.getEmail();
        String password = client.getPassword();
        return given().spec(baseHttpClient.requestSpecification)
                .basePath("signin")
                .body(Map.of("email", email,"password", password))
                .post();
    }

    public String getToken(Response response) {
        return response
                .path("token.access_token");
    }

    @Step("Удаление пользователя через API")
    public void deleteUserViaApi(String accessToken) {
        given()
                .spec(baseHttpClient.requestSpecification)
                .log().all()
                .basePath("user")
                .header("Authorization", accessToken)
                .delete()
                .then()
                .statusCode(200); // Ожидаем успешное удаление
    }
}
