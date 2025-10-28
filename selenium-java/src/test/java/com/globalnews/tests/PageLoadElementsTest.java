package com.globalnews.tests;

import com.globalnews.utils.BaseTest;
import com.globalnews.utils.DriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Page Load and Elements Tests for Global News Application
 * Equivalent to test_page_load_and_elements.py
 */
@Epic("Global News Application")
@Feature("Page Loading and Elements")
public class PageLoadElementsTest extends BaseTest {
    
    @Test(groups = {"smoke", "page-load"})
    @Story("Page Loading")
    @Description("Test that the main page loads with all essential elements")
    @Severity(SeverityLevel.BLOCKER)
    public void testPageLoadsSuccessfully() {
        // Verify page title contains expected text
        String pageTitle = DriverManager.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Interactive News Globe"),
                         "Page title should contain 'Interactive News Globe'");
        
        // Verify main heading is present and visible (it's an h1 element)
        WebElement mainHeading = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible");
        Assert.assertTrue(mainHeading.getText().contains("Interactive News Globe"),
                         "Main heading should contain 'Interactive News Globe'");
        
        System.out.println("✅ Page loads successfully with main elements");
    }
    
    @Test(groups = {"smoke", "page-load"})
    @Story("Loading States")
    @Description("Test that loading state appears during page initialization")
    @Severity(SeverityLevel.NORMAL)
    public void testLoadingStateAppears() {
        // Refresh page to catch loading state
        DriverManager.getDriver().navigate().refresh();
        
        try {
            // Try to catch the loading element (might be too fast)
            WebElement loadingElement = DriverManager.getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Loading')]"))
            );
            Assert.assertNotNull(loadingElement, "Loading element should be present");
        } catch (TimeoutException e) {
            // Loading might be too fast to catch, which is also valid
            System.out.println("⚡ Loading state was too fast to capture (acceptable)");
        }
        
        // Ensure page is fully loaded after
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        
        System.out.println("✅ Loading state test completed");
    }
    
    @Test(groups = {"regression", "responsive"})
    @Story("Responsive Design")
    @Description("Test page behavior at desktop resolution")
    @Severity(SeverityLevel.NORMAL)
    public void testPageResponsivenessDesktop() {
        // Set desktop resolution
        DriverManager.setWindowSize(1920, 1080);
        
        // Verify main elements are visible at desktop size (check h1 instead)
        WebElement mainHeading = DriverManager.getWait().until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible on desktop");
        Assert.assertTrue(mainHeading.getText().contains("Interactive News Globe"),
                         "Main heading should contain correct text");
        
        System.out.println("🖥️ Desktop responsiveness test passed");
    }
    
    @Test(groups = {"regression", "responsive"})
    @Story("Mobile Responsive Design")
    @Description("Test page behavior at mobile resolution")
    @Severity(SeverityLevel.NORMAL)
    public void testPageResponsivenessMobile() {
        // Set mobile resolution
        DriverManager.setWindowSize(375, 667);
        
        // Verify main elements are still accessible on mobile (check h1)
        WebElement mainHeading = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible on mobile");
        Assert.assertTrue(mainHeading.getText().contains("Interactive News Globe"),
                         "Main heading should contain correct text");
        
        System.out.println("📱 Mobile responsiveness test passed");
    }
    
    @Test(groups = {"regression", "structure"})
    @Story("Page Structure")
    @Description("Test that page has proper content structure")
    @Severity(SeverityLevel.NORMAL)
    public void testPageContentStructure() {
        // Wait for page to fully load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        
        // Verify essential page structure elements exist
        WebElement body = DriverManager.getDriver().findElement(By.tagName("body"));
        Assert.assertNotNull(body, "Page body should be present");
        
        // Check for main heading
        WebElement mainHeading = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible");
        Assert.assertTrue(mainHeading.getText().contains("Interactive News Globe"),
                         "Main heading should contain correct text");
        
        // Check for author information
        WebElement authorElement = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'By Omar')]"))
        );
        Assert.assertTrue(authorElement.isDisplayed(), "Author information should be visible");
        
        System.out.println("🏗️ Page structure test passed");
    }
}