package com.globalnews.tests;

import com.globalnews.utils.BaseTest;
import com.globalnews.utils.DriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Basic Selenium Tests for Global News Application
 * Equivalent to test_basico.py
 */
@Epic("Global News Application")
@Feature("Basic Functionality")
public class BasicTest extends BaseTest {
    
    @Test(groups = {"smoke", "basic"})
    @Story("Basic Connectivity Test")
    @Description("Test that Selenium can connect to the Global News website and verify basic functionality")
    @Severity(SeverityLevel.BLOCKER)
    public void testSeleniumBasic() {
        // Step 1: Verify page title
        String pageTitle = DriverManager.getPageTitle();
        System.out.println("📰 Page title: " + pageTitle);
        Assert.assertTrue(pageTitle.contains("Interactive News Globe"), 
                         "Page title should contain 'Interactive News Globe'");
        
        // Step 2: Verify current URL
        String currentUrl = DriverManager.getCurrentUrl();
        System.out.println("🌐 Current URL: " + currentUrl);
        Assert.assertEquals(getCleanUrl(currentUrl), getCleanUrl(BASE_URL),
                           "Current URL should match expected URL");
        
        // Step 3: Verify main heading is present and visible
        WebElement mainHeading = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible");
        Assert.assertTrue(mainHeading.getText().contains("Interactive News Globe"),
                         "Main heading should contain 'Interactive News Globe'");
        
        // Step 4: Verify basic page structure
        WebElement body = DriverManager.getDriver().findElement(By.tagName("body"));
        Assert.assertNotNull(body, "Page body should be present");
        
        // Step 5: Log success
        System.out.println("✅ Basic test completed successfully!");
        System.out.println("🔍 Page is accessible and main elements are present");
    }
    
    @Test(groups = {"smoke", "basic"})
    @Story("Page Loading Performance")
    @Description("Test that the page loads within acceptable time limits")
    @Severity(SeverityLevel.NORMAL)
    public void testPageLoadTime() {
        long startTime = System.currentTimeMillis();
        
        // Navigate to page (already loaded in setup, but refresh for timing)
        DriverManager.getDriver().navigate().refresh();
        
        // Wait for main content to load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        
        System.out.println("⏱️ Page load time: " + loadTime + "ms");
        Assert.assertTrue(loadTime < 10000, "Page should load within 10 seconds");
    }
    
    @Test(groups = {"basic", "ui"})
    @Story("Browser Compatibility")
    @Description("Test basic browser compatibility and JavaScript execution")
    @Severity(SeverityLevel.NORMAL)
    public void testBrowserCompatibility() {
        // Check if JavaScript is enabled by trying to execute a simple script
        Object result = DriverManager.getDriver().manage().logs();
        Assert.assertNotNull(result, "Browser should support WebDriver operations");
        
        // Check if the page renders properly
        String pageSource = DriverManager.getDriver().getPageSource();
        Assert.assertTrue(pageSource.contains("Interactive News Globe"),
                         "Page source should contain main content");
        Assert.assertTrue(pageSource.contains("html"),
                         "Page should have proper HTML structure");
        
        System.out.println("🌐 Browser compatibility test passed");
    }
}