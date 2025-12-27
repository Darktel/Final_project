package ru.praktikumservices.stand.qadesk.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Map;


public class AnnouncementPage {
    private static WebDriver driver;
    private WebDriverWait wait;



    public static final String URL = "https://qa-desk.stand.praktikum-services.ru/create-lisiting";


     //Head page
    @FindBy(xpath = "//h1[@class='hi createListing_title__IFtFs']")
    public WebElement titlePage;

    //Кнопка загрузки 1-го фото
    @FindBy(xpath = "//input[@name='img1']/parent::button")
    public WebElement buttonUploadPhoto1;

    @FindBy(xpath = "//input[@name='img1']")
    public WebElement UploadPhoto1;

    //Кнопка загрузки 2-го фото
    @FindBy(xpath = "//input[@name='img2']/parent::button")
    public WebElement buttonUploadPhoto2;

    @FindBy(xpath = "//input[@name='img2']")
    public WebElement UploadPhoto2;

    //Кнопка загрузки 3-го фото
    @FindBy(xpath = "//input[@name='img3']/parent::button")
    public WebElement buttonUploadPhoto3;

    @FindBy(xpath = "//input[@name='img3']")
    public WebElement UploadPhoto3;

    //Название объявления
    @FindBy(xpath = "//input[@placeholder='Название']")
    public WebElement inputName;

    //Выбор категории
    @FindBy(xpath = "//div[@class='createListing_inputRow__fmwXw']//div[@class='dropDownMenu_input__itKtw']//button[@type='button']")
    public WebElement selectCategoryButton;

    //Переключатель Состояние товара: Новый
    @FindBy(xpath = "//div[@class='radioUnput_inputActive__eC-HY']")
    public WebElement selectNew;

    //Переключатель Состояние товара: Б/У
    @FindBy(xpath = "//div[@class='radioUnput_inputRegular__FbVbr']")
    public WebElement selectUsed;

    //Выбор категории - Книги
    @FindBy(xpath = "//div[@class='createListing_inputRow__fmwXw']//button[2]")
    public WebElement buttonCategoryBooks;

    //Выбор города
    @FindBy(xpath = "//body/div[@id='root']/div[@class='App_app__GuJBs']/div[@class='createListingPage_createListingPageStyle__U-MJJ']/div[@class='createListing_shell__A5EA7']/form[@class='createListing_shell__A5EA7']/div[@class='dropDownMenu_dropMenu__sBxhz']/div[1]")
    public WebElement selectCityButton;

    //Выбранный город Москва
    //span[contains(text(),'Москва')]
    @FindBy(xpath = "//span[contains(text(),'Москва')]")
    public WebElement cityMoscow;

    //Выбранный город Санкт-Петербург
    @FindBy(xpath = "//span[contains(text(),'Санкт-Петербург')]")
    public WebElement citySPB;

    //Выбранный город Новосибирск
    @FindBy(xpath = "//span[contains(text(),'Новосибирск')]")
    public WebElement cityNovosibirsk;

    //Выбранный город Екатеринбург
    @FindBy(xpath = "//span[contains(text(),'Екатеринбург')]")
    public WebElement cityEkaterinburg;

    //Выбранный город Нижный Новгород
    @FindBy(xpath = "//span[contains(text(),'Нижний Новгород')]")
    public WebElement cityNizhniyNovgorod;

    //Выбранный город Казань
    @FindBy(xpath = "//span[contains(text(),'Казань')]")
    public WebElement cityKazan;

    //Описание объявления
    @FindBy(xpath = "//textarea[@placeholder='Описание товара']")
    public WebElement inputDescription;

    //Цена
    @FindBy(xpath = "//input[@placeholder='Стоимость']")
    public WebElement inputPrice;

    //Кнопка "Опубликовать" дл объявления
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonPublish;

    //Кнопка профиля
    @FindBy(xpath = "//button[@class='circleSmall']//*[name()='svg']")
    public WebElement profileButton;

    public AnnouncementPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void isPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(titlePage));
        assertTrue(titlePage.getText().contains("Новое объявление"));

    }

    public void fillAnnouncementForm(Map<String, String> updateData) {
        inputName.clear();
        inputName.sendKeys(updateData.get("name"));
        selectCityButton.click();
        //Клик по элементу cityNovosibirsk используя js executor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", cityNovosibirsk);
        inputDescription.clear();
        inputDescription.sendKeys(updateData.get("description"));
        inputPrice.clear();
        inputPrice.sendKeys(updateData.get("price"));
        buttonPublish.click();
    }
}