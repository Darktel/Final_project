package ru.praktikumservices.stand.qadesk.client;

import io.restassured.response.Response;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;
import ru.praktikumservices.stand.qadesk.models.Announcement;


import static io.restassured.RestAssured.given;

public class ClientAnnouncement {

    public void createAnnouncementViaApi(Announcement announcement, String token) {
        String name = announcement.getName();
        String category = announcement.getCategory();
        String condition = announcement.getCondition();
        String city = announcement.getCity();
        String description = announcement.getDescription();
        String price = announcement.getPrice();

        Response response = given()
                .header("Accept", "application/json, text/plain, */*")
                .log().all()
                .baseUri("https://qa-desk.stand.praktikum-services.ru/api/")
                .basePath("create-listing")
                .header("Authorization", "Bearer " + token)
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .multiPart("name", name)
                .multiPart("category", category)
                .multiPart("condition", condition)
                .multiPart("city", city)
                .multiPart("description", description)
                .multiPart("price", price)
                .post();
        // Проверяем, что запрос успешен (201 Created или 200 OK)
        response.then().statusCode(201);
    }
}