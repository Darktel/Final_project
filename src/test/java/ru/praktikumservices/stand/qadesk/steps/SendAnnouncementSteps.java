package ru.praktikumservices.stand.qadesk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikumservices.stand.qadesk.DriverFactory;
import ru.praktikumservices.stand.qadesk.client.ClientClient;
import ru.praktikumservices.stand.qadesk.models.Client;
import ru.praktikumservices.stand.qadesk.pages.AnnouncementPage;
import ru.praktikumservices.stand.qadesk.pages.MainPage;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import org.openqa.selenium.JavascriptExecutor;
import ru.praktikumservices.stand.qadesk.pages.ProfilePage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendAnnouncementSteps {

    private WebDriver driver;
    private AnnouncementPage announcementPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private WebDriverWait wait;
//    private final DriverFactory driverFactory = new DriverFactory();
    private final Faker faker = new Faker();
    private Client client;
    private boolean isAuthenticated = false;
    public String tokenClient; // Переменная для хранения токена клиента
    private String announcementId; // Переменная для хранения ID объявления


    @Before
    public void setUp() {
//        driverFactory.initDriver();
//        this.driver = driverFactory.driver;
        WebDriver driver = DriverFactory.getInstance().getDriver();
        this.mainPage = new MainPage(driver);
        this.profilePage = new ProfilePage(driver); // Инициализация ProfilePage
        this.mainPage = new MainPage(driver);
        this.announcementPage = new AnnouncementPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.client = new Client(faker.internet().emailAddress(), faker.internet().password());
    }

    @Given("the user is authenticated")
    public void the_user_is_authenticated() {
        ClientClient clientClient = new ClientClient();
        Response response = clientClient.createUserViaApi(client); // Регистрируем пользователя через API перед тестом
        tokenClient = clientClient.getToken(response);
        driver.get(mainPage.URL);
        mainPage.clickLoginButton();
        mainPage.LoginClient(client);
        mainPage.isProfileVisible();
        isAuthenticated = true;
    }

    @Given("the user is on the announcement creation page")
    public void the_user_is_on_the_announcement_creation_page() {
        driver.get(announcementPage.URL);
        announcementPage.isPageLoaded();
    }

    @When("the user fills in the announcement form:")
    public void the_user_fills_in_the_announcement_form(DataTable dataTable) {
       JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, String> data = dataTable.asMap();
        announcementPage.inputName.sendKeys(data.get("title"));
        announcementPage.selectCategoryButton.click();
        announcementPage.buttonCategoryBooks.click();
        if ("New".equals(data.get("condition"))) {
            announcementPage.selectNew.click();
        } else {
            announcementPage.selectUsed.click();
        }
        announcementPage.selectCityButton.click();
        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Казань')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);//        announcementPage.cityKazan.click();
        announcementPage.inputDescription.sendKeys(data.get("description"));
        announcementPage.inputPrice.sendKeys(data.get("price"));
    }

    @And("the user uploads photo {int}")
    public void the_user_uploads_photo(Integer photoNumber) {
        String absolutePath;
        switch (photoNumber) {
            case 1:
                absolutePath = Paths.get("src/test/resources/test-data/book1.jpg").toAbsolutePath().toString();
                uploadFile(announcementPage.UploadPhoto1, absolutePath);
                try {
                    Thread.sleep(3000); // 3000 миллисекунд = 3 секунды
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Sleep interrupted", e);
                }
                break;
            case 2:
                absolutePath = Paths.get("src/test/resources/test-data/book.jpg").toAbsolutePath().toString();
                uploadFile(announcementPage.UploadPhoto2, absolutePath);
                try {
                    Thread.sleep(3000); // 3000 миллисекунд = 3 секунды
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Sleep interrupted", e);
                }
                break;
            case 3:
                absolutePath = Paths.get("src/test/resources/test-data/book3.jpg").toAbsolutePath().toString();
                uploadFile(announcementPage.UploadPhoto3, absolutePath);
                try {
                    Thread.sleep(3000); // 3000 миллисекунд = 3 секунды
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Sleep interrupted", e);
                }
                break;
        }
    }

    private void uploadFile(org.openqa.selenium.WebElement uploadElement, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File found: " + filePath);
            uploadElement.sendKeys(filePath);
        } else {
            throw new AssertionError("File not found: " + filePath);
        }
    }

    @And("the user clicks the \"Publish\" button")
    public void the_user_clicks_the_publish_button() {
        wait.until(ExpectedConditions.elementToBeClickable(announcementPage.buttonPublish)); // Дождаться, пока кнопка станет кликабельной
        // Клик по кнопке "Publish" на странице создания объявления
        announcementPage.buttonPublish.click();
    }

    @Then("the announcement is successfully published and visible on profile page")
    public void the_announcement_is_successfully_published_and_visible_on_profile_page() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        wait.until(ExpectedConditions.visibilityOf(announcementPage.profileButton));
        announcementPage.profileButton.click();
        assertTrue(driver.getCurrentUrl().contains("/profile")); // Проверка, что пользователь перешел на страницу профиля
        //Прокручиваем страницу вниз до элемента profilePage.titleSectionAnnouncement
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", profilePage.titleSectionAnnouncement); // Прокрутка к элементу titleSectionAnnouncement
        wait.until(ExpectedConditions.visibilityOf(profilePage.titleSectionAnnouncement));
        assertTrue(profilePage.titleSectionAnnouncement.isDisplayed()); // Проверка отображения заголовка объявления на странице профиля
        wait.until(ExpectedConditions.visibilityOf(profilePage.firstAnnouncementTitle)); // Ожидание появления текста в элементе
        assertEquals("\"The Lord of the Rings\"", profilePage.firstAnnouncementTitle.getText()); // Сравнение текста объявления с ожидаемым значением
        assertEquals("Казань", profilePage.firstAnnouncementCity.getText()); // Сравнение даты объявления с ожидаемым значением
        assertEquals("500 ₽", profilePage.firstAnnouncementPrice.getText()); // Сравнение времени объявления с ожидаемым значением
    }


        @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

}
