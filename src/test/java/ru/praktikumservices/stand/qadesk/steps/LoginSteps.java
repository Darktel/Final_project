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


import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.dockerjava.transport.DockerHttpClient.Request;

//import ru.praktikumservices.stand.qadesk.DriverExtension;
import ru.praktikumservices.stand.qadesk.DriverFactory;
//import ru.praktikumservices.stand.qadesk.TestContext;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Map;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private final Faker faker = new Faker();
    Client client = new Client(faker.internet().emailAddress(), faker.internet().password());
    BaseHttpClient baseHttpClient = new BaseHttpClient();


    public LoginSteps() {
    }


    @Before
    public void setUp() {
//        DriverFactory driverFactory = new DriverFactory();
//        driverFactory.initDriver();
//        this.driver = driverFactory.driver;
        WebDriver driver = DriverFactory.getInstance().getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.mainPage = new MainPage(driver);


    }
//
//    @Before
//    public void setup() {
//    }


    @Step("Подготовка данных теста - Регистрация юзера через API")
    @And("the user is registered via API")
    public void CreateUserByApi(){
        ClientClient clientClient = new ClientClient();
        clientClient.createUserViaApi(client); // Регистрируем пользователя через API перед тестом
    }

    @Step("Открытие главной страницы")
    @Given("the user is on the main page")
    public void theUserIsOnTheMainPage() {
        mainPage.open();
    }

    @Step("Клик по кнопке \"Вход и регистрация\" на главной страницы")
    @When("the user clicks the \"Вход и регистрация\" button")
    public void theUserClicksTheSignInAndRegisterButton() {
        mainPage.clickLoginButton();
    }
    @Step("Ввод логина и пароля пользоателя в поля ввода логина и пароля соответстенно")
    @And("the user enters email and password")
    public void the_user_enters_email_and_password() {
    // Генерируем реальные данные через DataFaker
    mainPage.enterEmail(client.getEmail());
    mainPage.enterPassword(client.getPassword());
}
    @Step("Нажатие кнопки Войти")
    @And("the user clicks the \"Войти\" button")
    public void theUserClicksTheВойтиButton() {
        mainPage.clickSignInButton();
    }

    @Step("Проверка что на основной странице появились данные пользователя")
    @Then("the user is successfully logged in and sees their profile")
    public void theUserIsSuccessfullyLoggedInAndSeesTheirProfile() {
        assertEquals(mainPage.isProfileVisible(), "User.", "User profile is not visible after login");
    }







    // Шаги на странице регистрации. 


    @Step("Переход к окну регистрации")
    @And("the user clicks the \"Нет аккаунта\" button")
    public void theUserClicksTheSignUpButton() {
        mainPage.clickNoAccountButton();
    }


    @Step("Подтвердить создание аккаунта")
    @And("the user clicks the \"Создать аккаунт\" button")
    public void theUserClicksTheCreateAccountButton() {
        mainPage.clickCreateAccountButton();
    }

    @Step("Проверка наличия ошибки, при регистрации уже занятого email")
    @Then("the registration should fail with error message \"Ошибка\"")
    public void theUserTriesToRegisterWithAnExistingEmail() {
        assertTrue(mainPage.checkErrorMassageIsDisplayed());
    }
    

    @Step("Вводим данные для регистрации пользователя")
    @And("the user enters email and password and confirm password")
    public void theUserEntersValidCredentials() {
        mainPage.enterEmail(client.getEmail());
        mainPage.enterPassword(client.getPassword());
        mainPage.enterConfirmPassword(client.getPassword()); // Используем тот же пароль для подтверждения
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}