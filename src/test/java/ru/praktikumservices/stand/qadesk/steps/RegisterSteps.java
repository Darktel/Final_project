package ru.praktikumservices.stand.qadesk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

public class RegisterSteps {

    private WebDriver driver;
    private MainPage mainPage;
    private final Faker faker = new Faker();
    Client client = new Client(faker.internet().emailAddress(), faker.internet().password());
    BaseHttpClient baseHttpClient = new BaseHttpClient();

    public RegisterSteps() {
    }



//    @Before
//    public void setUp() {
        // Создаём драйвер для браузера Chrome
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
//        this.driver = new ChromeDriver(options);
//        this.mainPage = new MainPage(driver);
//    }


    // @Step("Проверка что на основной странице появились данные пользователя")
    // @Then("the user is successfully logged in and sees their profile")
    // public void theUserIsSuccessfullyLoggedInAndSeesTheirProfile() {
    //     assertEquals(mainPage.isProfileVisible(), "User.", "User profile is not visible after login");
    // }

        

}