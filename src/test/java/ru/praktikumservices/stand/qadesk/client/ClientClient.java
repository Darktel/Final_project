package ru.praktikumservices.stand.qadesk.client;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;

import static io.restassured.RestAssured.given;

import java.util.Map;
import ru.praktikumservices.stand.qadesk.models.Client;


public class ClientClient {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();

        public void createUserViaApi(Client client) {
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
    }

//    @Step("Создание пользователя")
//    public Response createClient(Client clientData) {
//        return given()
//                .spec(baseHttpClient.requestSpecification)
//                .body(clientData)
//                .when()
//                .post("auth/register");
//    }
//
//    @Step("Получение access токена пользователя")
//    public String getTokenClient(Client clientData) {
//        return given()
//                .spec(baseHttpClient.requestSpecification)
//                .body(clientData)
//                .when()
//                .post("auth/login")
//                .then()
//                .extract()
//                .jsonPath()
//                .getString("accessToken");
//    }
//
//    @Step("Авторизация пользователя")
//    public Response loginClient(Client clientData) {
//        return given()
//                .spec(baseHttpClient.requestSpecification)
//                .body(clientData)
//                .when()
//                .post("auth/login");
//    }
//
//    @Step("Удаление пользователя")
//    public Response deleteClient(String accessToken ) {
//        if (accessToken != null) {
//            return given()
//                    .spec(baseHttpClient.requestSpecification)
//                    .header("Authorization", accessToken)
//                    .when()
//                    .delete("auth/user");
//        }
//        return null;
//    }
//
//    @Step("Изменение данных пользователя")
//    public Response changeDataClient(String tokenClient, Client client) {
//        return given()
//                .spec(baseHttpClient.requestSpecification)
//                .header("Authorization", tokenClient)
//                .body(client)
//                .when()
//                .patch("auth/user");
//    }
//
//    @Step("Изменение данных пользователя без авторизации")
//    public Response changeDataClientWithoutLogin(Client client) {
//        return given()
//                .spec(baseHttpClient.requestSpecification)
//                .body(client)
//                .when()
//                .patch("auth/user");
//    }
}

