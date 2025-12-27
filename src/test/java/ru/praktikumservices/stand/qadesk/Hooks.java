package ru.praktikumservices.stand.qadesk;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final DriverFactory driverFactory = DriverFactory.getInstance();

    @Before
    public void setUp() {
        driverFactory.initDriver();
    }

    @After
    public void tearDown() {
        driverFactory.quitDriver();
    }
}