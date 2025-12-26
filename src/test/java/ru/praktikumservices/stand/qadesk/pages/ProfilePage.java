package ru.praktikumservices.stand.qadesk.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfilePage {
    private static WebDriver driver;
    private WebDriverWait wait;


    public static final String URL = "https://qa-desk.stand.praktikum-services.ru/profile";


    //Head announcement section
    @FindBy(xpath = "//h1[contains(text(),'Мои объявления')]")
    public WebElement titleSectionAnnouncement;

    //Изображение первого объявления.
    @FindBy(xpath = "//div[@class='card']/img")
    public WebElement firstAnnouncementImage;

    //Наимнование объявления
    @FindBy(xpath = "//div[@class='about']/h2")
    public WebElement firstAnnouncementTitle;

    //Город указанный в объявлении
    @FindBy(xpath = "//div[@class='about']/h3")
    public WebElement firstAnnouncementCity;

    //Цена указанная в объявлении
    @FindBy(xpath = "//div[@class='price']/h2")
    public WebElement firstAnnouncementPrice;

    //кнопка редактирования объявления
    @FindBy(xpath = "//button[@class='editButton']")
    public WebElement editAnnouncementButton;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    public void open() {
        driver.get(URL);
    }


    public void clickEditAnnouncementButton() {
        //промотать до элемента на странице editAnnouncementButton с использованием js executor
        try {
            Thread.sleep(500); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", editAnnouncementButton);
        try {
            Thread.sleep(1000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        //ожидание появления элемента
        wait.until(ExpectedConditions.elementToBeClickable(editAnnouncementButton));
        try {
            Thread.sleep(1000); // 3000 миллисекунд = 3 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
        //клик по кнопке
        editAnnouncementButton.click();

    }
}
