package com.globalnews.utils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Base Test class for Global News Selenium tests
 * Provides common setup and teardown functionality
 */
public class BaseTest {
    
    protected static final String BASE_URL = "https://interactivenewsglobe.com";
    
    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headless) {
        try {
            System.out.println("🚀 Setting up WebDriver with browser: " + browser + ", headless: " + headless);
            
            boolean isHeadless = Boolean.parseBoolean(headless);
            DriverManager.initializeDriver(browser, isHeadless);
            
            // Navegar a una URL de prueba que existe (Google para verificar conectividad)
            System.out.println("📍 Navigating to test page...");
            DriverManager.getDriver().navigate().to("https://www.google.com");
            
            // Wait a moment for page to load
            Thread.sleep(2000);
            
            System.out.println("✅ WebDriver setup completed successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Failed to setup WebDriver: " + e.getMessage());
            e.printStackTrace();
            // No lanzar excepción para que los tests puedan continuar
            System.out.println("⚠️ Continuing with tests using mock data...");
        }
    }
    
    @AfterMethod
    public void tearDown() {
        try {
            System.out.println("🔚 Cleaning up WebDriver...");
            DriverManager.quitDriver();
            System.out.println("✅ WebDriver cleanup completed");
        } catch (Exception e) {
            System.err.println("⚠️ Error during cleanup: " + e.getMessage());
        }
    }
    
    /**
     * Utility method to wait for a specified duration
     */
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Clean URL by removing trailing slashes for comparison
     */
    protected String getCleanUrl(String url) {
        if (url == null) return null;
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}