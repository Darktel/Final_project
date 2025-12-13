package ru.praktikumservices.stand.qadesk;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private WebDriver driver;


    public void initDriver() {
        if ("chrome".equals(System.getProperty("browser"))) {
            setupChrome();
        }
        else {
            setupFirefox();
        }
    }

    public void setupFirefox() {
        driver = new org.openqa.selenium.firefox.FirefoxDriver();
        driver.manage().window().maximize();
    }

    public void setupChrome() {
        driver = new org.openqa.selenium.chrome.ChromeDriver();
        driver.manage().window().maximize();
    }
    public WebDriver getDriver() {
        return driver;
    }

}