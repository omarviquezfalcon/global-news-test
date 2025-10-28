package com.globalnews.utils;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Base Test Class for Global News Tests
 * Provides common setup and teardown functionality
 */
public class BaseTest implements ITestListener {
    
    protected static final String BASE_URL = "https://global-news-proyect-gmni.vercel.app/";
    
    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void setUp(String browser, String headless) {
        // Default values if parameters not provided
        browser = (browser != null) ? browser : "chrome";
        boolean isHeadless = Boolean.parseBoolean(headless);
        
        System.out.println("🚀 Initializing " + browser + " driver (headless: " + isHeadless + ")");
        DriverManager.initializeDriver(browser, isHeadless);
        
        System.out.println("🌐 Navigating to: " + BASE_URL);
        DriverManager.navigateToUrl(BASE_URL);
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ Test failed: " + result.getName());
            attachScreenshot();
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("✅ Test passed: " + result.getName());
        }
        
        System.out.println("🔄 Closing browser");
        DriverManager.quitDriver();
    }
    
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        try {
            String base64Screenshot = DriverManager.takeScreenshot();
            return java.util.Base64.getDecoder().decode(base64Screenshot);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Wait for a specified amount of time
     */
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get clean URL without trailing slash for comparison
     */
    protected String getCleanUrl(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}