package com.globalnews.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WebDriver Manager for Selenium Java Tests
 * Handles browser setup and configuration
 */
public class DriverManager {
    
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    
    // Configuration
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(5);
    
    /**
     * Get WebDriver instance for current thread
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    /**
     * Get WebDriverWait instance for current thread
     */
    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }
    
    /**
     * Initialize WebDriver with specified browser
     * @param browser Browser type (chrome, firefox)
     * @param headless Run in headless mode
     */
    public static void initializeDriver(String browser, boolean headless) {
        WebDriver driver;
        
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = createChromeDriver(headless);
                break;
            case "firefox":
                driver = createFirefoxDriver(headless);
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        // Configure timeouts
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        
        // Maximize window (unless headless)
        if (!headless) {
            driver.manage().window().maximize();
        } else {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        }
        
        // Set up ThreadLocal instances
        driverThreadLocal.set(driver);
        waitThreadLocal.set(new WebDriverWait(driver, DEFAULT_TIMEOUT));
    }
    
    /**
     * Create Chrome WebDriver with options
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Standard Chrome options for testing
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        
        // Performance optimizations
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver with options
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Firefox preferences
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.navigator.permission.disabled", true);
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Quit WebDriver and clean up ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            waitThreadLocal.remove();
        }
    }
    
    /**
     * Navigate to URL
     */
    public static void navigateToUrl(String url) {
        getDriver().get(url);
    }
    
    /**
     * Get current URL
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
    
    /**
     * Get page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }
    
    /**
     * Take screenshot (returns base64 string)
     */
    public static String takeScreenshot() {
        return ((org.openqa.selenium.TakesScreenshot) getDriver())
                .getScreenshotAs(org.openqa.selenium.OutputType.BASE64);
    }
    
    /**
     * Set window size
     */
    public static void setWindowSize(int width, int height) {
        getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
    }
}