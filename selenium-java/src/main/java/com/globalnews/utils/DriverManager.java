package com.globalnews.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;
import java.time.Duration;

/**
 * WebDriver Manager for Global News Selenium tests
 * Handles WebDriver initialization, configuration, and cleanup
 */
public class DriverManager {
    
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver with specified browser and options
     */
    public static void initializeDriver(String browser, boolean headless) {
        try {
            WebDriver webDriver;
            
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--allow-running-insecure-content");
                    
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                    
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    
                    webDriver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            
            // Configure WebDriver
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            
            // Set up WebDriverWait
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            
            // Store in ThreadLocal
            driver.set(webDriver);
            wait.set(webDriverWait);
            
            System.out.println("✅ WebDriver initialized successfully: " + browser);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }
    
    /**
     * Get the current WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }
    
    /**
     * Get the current WebDriverWait instance
     */
    public static WebDriverWait getWait() {
        WebDriverWait webDriverWait = wait.get();
        if (webDriverWait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized. Call initializeDriver() first.");
        }
        return webDriverWait;
    }
    
    /**
     * Get current page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }
    
    /**
     * Get current URL
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
    
    /**
     * Set window size
     */
    public static void setWindowSize(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
    }
    
    /**
     * Quit WebDriver and clean up resources
     */
    public static void quitDriver() {
        try {
            WebDriver webDriver = driver.get();
            if (webDriver != null) {
                webDriver.quit();
                driver.remove();
                wait.remove();
                System.out.println("✅ WebDriver quit successfully");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error while quitting WebDriver: " + e.getMessage());
        }
    }
}