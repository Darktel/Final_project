package ru.praktikumservices.stand.qadesk.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikumservices.stand.qadesk.DriverFactory;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.client.ClientAnnouncement;
import ru.praktikumservices.stand.qadesk.models.Announcement;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.AnnouncementPage;
import ru.praktikumservices.stand.qadesk.pages.MainPage;
import ru.praktikumservices.stand.qadesk.pages.ProfilePage;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteAnnouncementSteps {

    private WebDriver driver;
    private AnnouncementPage announcementPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private WebDriverWait wait;
    private final Faker faker = new Faker();
    private Client client;
    private boolean isAuthenticated = false;
    public String tokenClient; // Переменная для хранения токена клиента
    private String announcementId; // Переменная для хранения ID объявления
    private final ClientClient clientClient = new ClientClient();
    private final ClientAnnouncement announcementClient = new ClientAnnouncement();
    private Announcement announcement = new Announcement();
    private ClientAnnouncement clientAnnouncement = new ClientAnnouncement(); // Инициализация объекта ClientAnnouncement


    @Before
    public void setUp() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        this.driver = driver;
        this.mainPage = new MainPage(driver);
        this.profilePage = new ProfilePage(driver);
        this.announcementPage = new AnnouncementPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.client = new Client(faker.internet().emailAddress(), faker.internet().password());
    }

    @And("the user clicks the delete button for the announcement")
    public void theUserDeleteTheAnnouncement() {
        profilePage.clickDeleteAnnouncementButton();  // Клик по кнопке Удаления объявления
    }

    @Given("User creates a new account with valid credentials")
    public void userCreatesANewAccountWithValidCredentials() {
        ClientClient clientClient = new ClientClient();
        clientClient.createUserViaApi(client); // Регистрируем пользователя через API перед тестом
        Response response = clientClient.loginUserViaApi(client);
        tokenClient = clientClient.getToken(response);
    }

    @And("is authorized user navigates to the profile page")
    public void the_user_navigates_to_the_profile_page() {
        mainPage.open();
        mainPage.clickLoginButton();
        mainPage.LoginClient(client);
        try {
            Thread.sleep(2000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        profilePage.open();
    }

    @Given("an announcement has been created")
    public void anAnnouncementHasBeenPreviouslyCreated() {
        announcement = new Announcement();
        announcement.setName("Harry Potter and the Philosopher's Stone");
        announcement.setCategory("Books");
        announcement.setCondition("new");
        announcement.setCity("Kazan");
        announcement.setDescription("Best book из всей серии");
        announcement.setPrice("699");
        clientAnnouncement.createAnnouncementViaApi(announcement, tokenClient);
    }


    @And("the user scrolls down to the announcement")
    public void the_user_scrolls_down_to_the_announcement() {

        wait.until(ExpectedConditions.visibilityOf(profilePage.firstAnnouncementTitle));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", profilePage.firstAnnouncementTitle);
        try {
            Thread.sleep(2000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
    }


    // Step: Not visible on profile
    @And("the announcement should no longer be visible on the profile page")
    public void the_announcement_should_no_longer_be_visible_on_the_profile_page() {
        driver.navigate().refresh();
        profilePage.open();
        //Промотать вниз страницы
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try {
            Thread.sleep(2000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        // Проверка, что объявление больше не отображается
        assertFalse(profilePage.isAnnouncementVisible(), "Announcement should not be visible after deletion"); // Проверяет, что объявление не видно на странице профиля
    }
}
