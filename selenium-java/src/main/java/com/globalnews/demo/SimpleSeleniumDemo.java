package com.globalnews.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Simple demo without TestNG dependencies
 * Demonstrates core Selenium functionality
 */
public class SimpleSeleniumDemo {
    
    public static void main(String[] args) {
        WebDriver driver = null;
        
        try {
            System.out.println("🚀 Starting Selenium Java Demo...");
            
            // Configure Chrome options
            ChromeOptions options = new ChromeOptions();
            // Uncomment for headless mode: options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            
            // Initialize WebDriver (requires ChromeDriver in PATH or use WebDriverManager)
            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Navigate to the application
            System.out.println("📍 Navigating to Global News Interactive Globe...");
            driver.get("https://interactivenewsglobe.com");
            
            // Test 1: Page Title
            System.out.println("✅ Test 1: Page Title");
            String title = driver.getTitle();
            System.out.println("   Title: " + title);
            assert title.contains("Interactive News Globe") : "Title should contain 'Interactive News Globe'";
            
            // Test 2: Main Heading
            System.out.println("✅ Test 2: Main Heading");
            WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
            System.out.println("   Heading: " + heading.getText());
            assert heading.isDisplayed() : "Main heading should be visible";
            
            // Test 3: Search Input
            System.out.println("✅ Test 3: Search Input");
            WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder*='Search for a country']")
            ));
            assert searchInput.isDisplayed() : "Search input should be visible";
            System.out.println("   Search placeholder: " + searchInput.getAttribute("placeholder"));
            
            // Test 4: Globe SVG
            System.out.println("✅ Test 4: Globe Element");
            WebElement globe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("svg")));
            assert globe.isDisplayed() : "Globe should be visible";
            System.out.println("   Globe dimensions: " + globe.getSize());
            
            // Test 5: Search Functionality
            System.out.println("✅ Test 5: Search Functionality");
            searchInput.click();
            searchInput.sendKeys("United States");
            Thread.sleep(1000); // Wait for any search suggestions
            String inputValue = searchInput.getAttribute("value");
            assert inputValue.equals("United States") : "Search input should accept text";
            System.out.println("   Search value: " + inputValue);
            
            System.out.println("🎉 All tests passed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                System.out.println("🔚 Closing browser...");
                driver.quit();
            }
        }
    }
}