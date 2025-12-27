package ru.praktikumservices.stand.qadesk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import net.datafaker.Faker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.praktikumservices.stand.qadesk.DriverFactory;
import ru.praktikumservices.stand.qadesk.base.BaseHttpClient;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.MainPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private final Faker faker = new Faker();
    Client client = new Client(faker.internet().emailAddress(), faker.internet().password());
    BaseHttpClient baseHttpClient = new BaseHttpClient();


    @Before
    public void setUp() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.mainPage = new MainPage(driver);


    }


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
        DriverFactory.quitDriver();
    }
}