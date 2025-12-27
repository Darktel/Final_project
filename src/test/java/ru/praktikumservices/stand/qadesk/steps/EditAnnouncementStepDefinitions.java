package ru.praktikumservices.stand.qadesk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.openqa.selenium.WebDriver;
import ru.praktikumservices.stand.qadesk.DriverFactory;
import ru.praktikumservices.stand.qadesk.client.ClientAnnouncement;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.models.Announcement;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.AnnouncementPage;
import ru.praktikumservices.stand.qadesk.pages.MainPage;
import ru.praktikumservices.stand.qadesk.pages.ProfilePage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EditAnnouncementStepDefinitions {

    private ClientClient clientClient = new ClientClient();
    private ClientAnnouncement clientAnnouncement = new ClientAnnouncement();
    private Announcement announcement;
    private Response response;
    private boolean isAuthenticated = false;
    private final Faker faker = new Faker(); // Используем Faker для генерации тестовых данных
    private String tokenClient;
    private Client client  = new Client(faker.internet().emailAddress(), faker.internet().password());
    private WebDriver driver;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private AnnouncementPage announcementPage;

    @Before
    public void setUp() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        this.mainPage = new MainPage(driver);
        this.profilePage = new ProfilePage(driver); // Инициализация ProfilePage
        this.mainPage = new MainPage(driver);
        this.announcementPage = new AnnouncementPage(driver);
        this.client = new Client(faker.internet().emailAddress(), faker.internet().password());
    }
    @Given("the user is not authenticated")
    public void theUserIsNotAuthenticated() {
        isAuthenticated = false;
    }

    @Given("the user is authenticated by site")
    public void theUserIsAuthenticated() {
        ClientClient clientClient = new ClientClient();
        clientClient.createUserViaApi(client); // Регистрируем пользователя через API перед тестом
        Response response = clientClient.loginUserViaApi(client);
        tokenClient = clientClient.getToken(response);
        isAuthenticated = true;
    }
    @Given("an announcement has been previously created")
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

    @When("the user edits the announcement with:")
    public void theUserEditsTheAnnouncementWith(Map<String, String> updateData) {
        mainPage.open();
        mainPage.clickLoginButton();
        mainPage.LoginClient(client);
        mainPage.isProfileVisible();
        profilePage.open();
        profilePage.clickEditAnnouncementButton();  // Клик по кнопке редактирования
        announcementPage.fillAnnouncementForm(updateData); // Заполнение формы
    }


    @When("the user tries to edit an existing announcement")
    public void theUserTriesToEditAnExistingAnnouncement() {
        // Повтор логики редактирования без авторизации
//        response = Response.builder().setStatusCode(401).build();
    }

    @Then("the announcement should be successfully updated")
    public void theAnnouncementShouldBeSuccessfullyUpdated() {
        profilePage.open();
        profilePage.clickEditAnnouncementButton();
        try {
            Thread.sleep(2000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        assertEquals("Harry Potter and the Deathly Hallows", announcementPage.inputName.getText(), "Name should be updated");
        assertEquals("The final book in the series", announcementPage.inputDescription.getText(), "Description should be updated");
        assertEquals(750, announcementPage.inputPrice.getText(), "Price should be updated");
    }

    @Then("the updated announcement details should be visible on the profile page")
    public void theUpdatedAnnouncementDetailsShouldBeVisibleOnTheProfilePage() {

    }

    @Then("the system should deny access and prompt for login")
    public void theSystemShouldDenyAccessAndPromptForLogin() {

    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

}
