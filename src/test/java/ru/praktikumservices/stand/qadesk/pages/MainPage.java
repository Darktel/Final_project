package ru.praktikumservices.stand.qadesk.pages;

import io.qameta.allure.Step;
import net.bytebuddy.asm.MemberSubstitution.FieldValue;
import net.datafaker.Faker;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikumservices.stand.qadesk.models.Client;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class MainPage {
    private static WebDriver driver;
    private WebDriverWait wait;




    public static final String URL = "https://qa-desk.stand.praktikum-services.ru/";


     //Кнопка авторизации
    @FindBy(xpath = "//button[contains(text(),'Вход и регистрация')]")
    private WebElement loginButton;

    //Кнопка Разместить объявление
    @FindBy(xpath = "//button[contains(text(),'Разместить объявление')]")
    private WebElement postAdButton;

    //Строка поиска
    @FindBy(xpath = "//input[@placeholder='Я хочу купить...']")
    private WebElement searchInput;

    //Выпадающий список выбора категории
    @FindBy(xpath = "//input[@name='category']")
    private WebElement categoryInput;

    //Выпадающий список выбора города
    @FindBy(xpath = "//input[@name='city']")
    private WebElement cityInput;

    //Поле ввода стоимости
    @FindBy(xpath = "//input[@placeholder='Стоимость']")
    private WebElement priceInput;

    //Кнопка "Применить" фильтр
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement applyFilterButton;

    //Блок объявлений
    @FindBy(xpath = "//div[@class='grid_threeColumns__ldn5D']")
    private WebElement adsBlock;

    //Форма авторизации - ввод email
    @FindBy(xpath = "//input[@placeholder='Введите Email']")
    private WebElement emailInput;

    //Форма авторизации - ввод password
    @FindBy(xpath = "//input[@placeholder='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@placeholder='Повторите пароль']")
    private WebElement confirmPasswordInput;

    //Кнопка войти
    @FindBy(xpath = "//button[contains(text(),'Войти')]")
    private WebElement signInButton;

    //Кнопка закрытия окна авторизации
    @FindBy(xpath = "//*[name()='path' and contains(@d,'M10 10L30 ')]")
    private WebElement closeAuthModalButton;

    //h3[@class='profileText name']
    @FindBy(xpath = "//h3[@class='profileText name']")
    private WebElement profileName;

    @FindBy(xpath = "//button[contains(text(),'Создать аккаунт')]")
    private WebElement signUpButton;

    @FindBy(xpath = "//button[contains(text(),'Нет аккаунта')]")
    private WebElement noHaveAccountButton;

    @FindBy(xpath = "//h1[@class='h1']")
    private WebElement mainHeaderRegisterWindow;

    //локатор при ошибке регистрации дубля пользователя
    @FindBy(xpath = "//span[contains(text(),'Ошибка')]")
    private WebElement errorRegistrationMassege;

    public MainPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    
//    @Step("Переходим к профилю клиента")
//    public void clickProfileButton(){
//        wait.until(ExpectedConditions.elementToBeClickable(buttonProfileLink));
//        buttonProfileLink.click();
//    }

public void open() {
        driver.get(URL);
    }

    public void clickLoginButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", loginButton); //клик по кнопке Войти через JS
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
    }

    public void clickSignInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    public String isProfileVisible() {
      wait.until(ExpectedConditions.visibilityOf(profileName));
        String actualName = profileName.getText();
        return actualName;
    }

    // @Step("Нажимаем кнопку 'Вход и регистрация'")
    // public void clickLoginButton(){
    //     wait.until(ExpectedConditions.visibilityOf(loginButton));
    //     loginButton.click();
    // }

    public void LoginClient(Client client) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(client.getEmail());
        passwordInput.sendKeys(client.getPassword());
        signInButton.click();

    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput));
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickCreateAccountButton(){
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton));
        signUpButton.click();
    }

    public void clickNoAccountButton(){
        wait.until(ExpectedConditions.elementToBeClickable(noHaveAccountButton));
        noHaveAccountButton.click();
    }
    

    public boolean checkErrorMassageIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(mainHeaderRegisterWindow));
            wait.until(ExpectedConditions.visibilityOf(errorRegistrationMassege));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}