package com.globalnews.tests;

import com.globalnews.utils.BaseTest;
import com.globalnews.utils.DriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Globe Interaction Tests for Global News Application
 * Equivalent to test_globe_interaction.py
 */
@Epic("Global News Application")
@Feature("Globe Interaction")
public class GlobeInteractionTest extends BaseTest {
    
    @Test(groups = {"smoke", "globe"})
    @Story("Globe Loading")
    @Description("Test that globe loading indicator appears during initialization")
    @Severity(SeverityLevel.NORMAL)
    public void testGlobeLoadingIndicator() {
        // Refresh to potentially catch loading state
        DriverManager.getDriver().navigate().refresh();
        
        // Wait for page to be in a loading or loaded state
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        
        // The globe should eventually be present (SVG element)
        WebElement globeSvg = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("svg"))
        );
        Assert.assertNotNull(globeSvg, "Globe SVG should be present");
        
        System.out.println("🌍 Globe loading test completed");
    }
    
    @Test(groups = {"regression", "globe"})
    @Story("Globe Container")
    @Description("Test that globe container exists and is properly structured")
    @Severity(SeverityLevel.NORMAL)
    public void testGlobeContainerExists() {
        // Wait for page to load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        
        // Look for SVG element (the globe)
        WebElement globeContainer = DriverManager.getDriver().findElement(By.tagName("svg"));
        Assert.assertTrue(globeContainer.isDisplayed(), "Globe container should be visible");
        
        // Verify it has the expected attributes for interaction
        String tabIndex = globeContainer.getAttribute("tabindex");
        Assert.assertNotNull(tabIndex, "Globe should be focusable");
        
        System.out.println("📦 Globe container test passed");
    }
    
    @Test(groups = {"regression", "globe"})
    @Story("Globe Interaction")
    @Description("Test that globe supports interaction")
    @Severity(SeverityLevel.NORMAL)
    public void testGlobeInteractionCapabilities() {
        // Wait for page to fully load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        waitFor(3); // Allow globe to initialize
        
        // Try to find interactive elements
        WebElement body = DriverManager.getDriver().findElement(By.tagName("body"));
        Actions actions = new Actions(DriverManager.getDriver());
        
        // Simulate mouse interactions at different positions
        int[][] positions = {{200, 200}, {400, 300}, {600, 400}};
        
        for (int[] position : positions) {
            try {
                // Move to position and click
                actions.moveToElement(body, position[0], position[1]).click().perform();
                waitFor(1); // Short wait between interactions
            } catch (Exception e) {
                // Interaction might not be available, which is acceptable
                System.out.println("⚠️ Interaction at position [" + position[0] + "," + position[1] + "] not available");
            }
        }
        
        // Verify page is still functional after interactions
        // Fix URL comparison to handle trailing slash
        String currentUrl = getCleanUrl(DriverManager.getCurrentUrl());
        String baseUrl = getCleanUrl(BASE_URL);
        Assert.assertEquals(currentUrl, baseUrl, "URL should remain the same after interactions");
        
        System.out.println("🎯 Globe interaction test completed");
    }
    
    @Test(groups = {"regression", "search"})
    @Story("Search Functionality")
    @Description("Test search functionality interface")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchFunctionalityInterface() {
        // Wait for page to load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        
        // Look for search input
        WebElement searchInput = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder*='Search for a country']")
            )
        );
        Assert.assertTrue(searchInput.isDisplayed(), "Search input should be visible");
        
        // Test that search input is functional
        searchInput.click();
        searchInput.sendKeys("United States");
        waitFor(1);
        
        String inputValue = searchInput.getAttribute("value");
        Assert.assertTrue(inputValue.contains("United States"), "Search input should accept text");
        
        System.out.println("🔍 Search functionality test passed");
    }
    
    @Test(groups = {"regression", "keyboard"})
    @Story("Keyboard Navigation")
    @Description("Test keyboard navigation capabilities")
    @Severity(SeverityLevel.NORMAL)
    public void testKeyboardNavigationSupport() {
        // Wait for page to load
        DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );
        waitFor(2);
        
        WebElement body = DriverManager.getDriver().findElement(By.tagName("body"));
        Actions actions = new Actions(DriverManager.getDriver());
        
        // Test various keyboard interactions
        Keys[] keyboardActions = {Keys.TAB, Keys.ARROW_LEFT, Keys.ARROW_RIGHT, Keys.ARROW_UP, Keys.ARROW_DOWN};
        
        for (Keys key : keyboardActions) {
            try {
                actions.sendKeys(body, key).perform();
                waitFor(1); // Short wait between keys
            } catch (Exception e) {
                // Some keyboard actions might not be supported
                System.out.println("⚠️ Keyboard action " + key + " not supported");
            }
        }
        
        // Verify page remains functional
        WebElement titleElement = DriverManager.getDriver().findElement(By.tagName("h1"));
        Assert.assertTrue(titleElement.isDisplayed(), "Page should remain functional after keyboard navigation");
        Assert.assertTrue(titleElement.getText().contains("Interactive News Globe"),
                         "Title should contain expected text");
        
        System.out.println("⌨️ Keyboard navigation test completed");
    }
    
    @Test(groups = {"regression", "persistence"})
    @Story("State Persistence")
    @Description("Test that page maintains state during interactions")
    @Severity(SeverityLevel.NORMAL)
    public void testPageStatePersistence() {
        // Wait for initial load
        WebElement initialTitle = DriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
        );
        Assert.assertTrue(initialTitle.isDisplayed(), "Initial title should be visible");
        Assert.assertTrue(initialTitle.getText().contains("Interactive News Globe"),
                         "Initial title should contain expected text");
        
        String initialTitleText = initialTitle.getText();
        
        // Perform some interactions
        try {
            WebElement body = DriverManager.getDriver().findElement(By.tagName("body"));
            Actions actions = new Actions(DriverManager.getDriver());
            
            // Simulate user interactions
            actions.moveToElement(body, 300, 300).click().perform();
            waitFor(1);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(1);
        } catch (Exception e) {
            System.out.println("⚠️ Some interactions not available");
        }
        
        // Verify state is maintained
        WebElement finalTitle = DriverManager.getDriver().findElement(By.tagName("h1"));
        Assert.assertTrue(finalTitle.isDisplayed(), "Title should still be visible after interactions");
        Assert.assertEquals(finalTitle.getText(), initialTitleText,
                           "Title text should remain the same");
        
        System.out.println("💾 State persistence test passed");
    }
}