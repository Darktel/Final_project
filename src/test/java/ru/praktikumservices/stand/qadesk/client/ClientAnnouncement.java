package ru.praktikumservices.stand.qadesk.client;

import io.restassured.response.Response;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;
import ru.praktikumservices.stand.qadesk.models.Announcement;

import java.util.Map;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;

public class ClientAnnouncement {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();

    public void createAnnouncementViaApi(Announcement announcement, String token) {
        String name = announcement.getName();
        String category = announcement.getCategory();
        String condition = announcement.getCondition();
        String city = announcement.getCity();
        String description = announcement.getDescription();
        String price = announcement.getPrice();
//        byte[] image1 = announcement.getImage1();
//        byte[] image2 = announcement.getImage2();
//        byte[] image3 = announcement.getImage3();


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
//                .body(Map.of(
//                        "name", name,
//                        "category", category,
//                        "condition", condition,
//                        "city", city,
//                        "description", description,
//                        "price", price
//                ))
                .post();

        // Проверяем, что запрос успешен (201 Created или 200 OK)
        response.then().statusCode(201);
    }
//
//    RestAssured.baseURI = "https://qa-desk.stand.praktikum-services.ru";
//
//    File image = new File("src/test/resources/donut.png"); // Убедись, что файл есть
//
//    given()
//            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjQ4MDgsImVtYWlsIjoia2VuLnJvd2VAeWFob28uY29tIiwibmFtZSI6IlVzZXIiLCJpYXQiOjE3NjY1MjUxMjUsImV4cCI6MTc2NjYxMTUyNX0.boDwh2Urbv9GCGUhojUgscaIiZckUdofuz9BVlcd70w")
//            .cookie("_yasc", "3WdPSSvj3SG/2cuOCBzpvAqvqzE2ZxKMrXA0ZMsXEN7p4TZ/nBBxox10acr5uGX7")
//            .header("Accept", "application/json, text/plain, */*")
//            .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
//            .header("Origin", "https://qa-desk.stand.praktikum-services.ru")
//            .header("Referer", "https://qa-desk.stand.praktikum-services.ru/create-lisiting")
//            .header("DNT", "1")
//            .header("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"")
//            .header("sec-ch-ua-mobile", "?0")
//            .header("sec-ch-ua-platform", "\"macOS\"")
//            .multiPart("name", "название")
//            .multiPart("category", "Авто")
//            .multiPart("condition", "Б/У")
//            .multiPart("city", "Москва")
//            .multiPart("description", "123")
//            .multiPart("price", 444)
//            .multiPart("images", image, "image/png")
//            .multiPart("images", image, "image/png")
//            .multiPart("images", image, "image/png")
//            .log().all()
//        .when()
//            .post("/api/create-listing")
//        .then()
//            .log().body()
//            .statusCode(200); // или другой ожидаемый статус
//}

}