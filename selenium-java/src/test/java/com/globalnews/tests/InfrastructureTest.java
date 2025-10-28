package com.globalnews.tests;

import com.globalnews.utils.BaseTest;
import com.globalnews.utils.DriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Basic Infrastructure Tests for Selenium Java
 * Pruebas básicas para verificar que la infraestructura Java funciona
 */
@Epic("Selenium Java Infrastructure")
@Feature("Basic Infrastructure Tests")
public class InfrastructureTest extends BaseTest {
    
    @Test(groups = {"smoke", "infrastructure"})
    @Story("WebDriver Initialization")
    @Description("Test that WebDriver initializes correctly")
    @Severity(SeverityLevel.BLOCKER)
    public void testWebDriverInitialization() {
        try {
            // Verificar que el driver está inicializado
            Assert.assertNotNull(DriverManager.getDriver(), "WebDriver should be initialized");
            
            // Verificar que podemos obtener el título de la página
            String pageTitle = DriverManager.getPageTitle();
            Assert.assertNotNull(pageTitle, "Page title should not be null");
            System.out.println("✅ Page title: " + pageTitle);
            
            // Verificar que podemos obtener la URL actual
            String currentUrl = DriverManager.getCurrentUrl();
            Assert.assertNotNull(currentUrl, "Current URL should not be null");
            System.out.println("✅ Current URL: " + currentUrl);
            
            System.out.println("🎉 WebDriver initialization test passed!");
            
        } catch (Exception e) {
            System.err.println("❌ WebDriver initialization test failed: " + e.getMessage());
            Assert.fail("WebDriver initialization failed: " + e.getMessage());
        }
    }
    
    @Test(groups = {"smoke", "infrastructure"})
    @Story("Page Navigation")
    @Description("Test that page navigation works correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void testPageNavigation() {
        try {
            // Verificar que podemos navegar a diferentes páginas
            DriverManager.getDriver().navigate().to("https://www.example.com");
            Thread.sleep(1000);
            
            String currentUrl = DriverManager.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("example.com"), 
                             "Should navigate to example.com");
            
            System.out.println("✅ Navigation test passed: " + currentUrl);
            
        } catch (Exception e) {
            System.err.println("❌ Navigation test failed: " + e.getMessage());
            Assert.fail("Navigation test failed: " + e.getMessage());
        }
    }
    
    @Test(groups = {"infrastructure", "elements"})
    @Story("Element Location")
    @Description("Test that element location works correctly")
    @Severity(SeverityLevel.NORMAL)
    public void testElementLocation() {
        try {
            // Navegar a example.com que tiene elementos conocidos
            DriverManager.getDriver().navigate().to("https://www.example.com");
            Thread.sleep(2000);
            
            // Buscar un elemento básico
            WebElement heading = DriverManager.getDriver().findElement(By.tagName("h1"));
            Assert.assertNotNull(heading, "Should find h1 element");
            Assert.assertTrue(heading.isDisplayed(), "h1 element should be displayed");
            
            String headingText = heading.getText();
            System.out.println("✅ Found heading: " + headingText);
            
        } catch (Exception e) {
            System.err.println("❌ Element location test failed: " + e.getMessage());
            Assert.fail("Element location test failed: " + e.getMessage());
        }
    }
    
    @Test(groups = {"infrastructure", "interaction"})
    @Story("Browser Interaction")
    @Description("Test that browser interaction capabilities work")
    @Severity(SeverityLevel.NORMAL)
    public void testBrowserInteraction() {
        try {
            // Verificar que podemos cambiar el tamaño de la ventana
            DriverManager.setWindowSize(1024, 768);
            Thread.sleep(500);
            
            // Verificar que podemos navegar hacia atrás y adelante
            DriverManager.getDriver().navigate().to("https://www.example.com");
            Thread.sleep(1000);
            
            DriverManager.getDriver().navigate().to("https://httpbin.org/html");
            Thread.sleep(1000);
            
            DriverManager.getDriver().navigate().back();
            Thread.sleep(1000);
            
            String currentUrl = DriverManager.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("example.com"), 
                             "Should navigate back to example.com");
            
            System.out.println("✅ Browser interaction test passed");
            
        } catch (Exception e) {
            System.err.println("❌ Browser interaction test failed: " + e.getMessage());
            Assert.fail("Browser interaction test failed: " + e.getMessage());
        }
    }
    
    @Test(groups = {"infrastructure", "performance"})
    @Story("Performance Measurement")
    @Description("Test that performance measurement works")
    @Severity(SeverityLevel.MINOR)
    public void testPerformanceMeasurement() {
        try {
            long startTime = System.currentTimeMillis();
            
            // Navegar a una página y medir el tiempo
            DriverManager.getDriver().navigate().to("https://httpbin.org/delay/1");
            Thread.sleep(1000);
            
            long endTime = System.currentTimeMillis();
            long loadTime = endTime - startTime;
            
            System.out.println("✅ Page load time: " + loadTime + "ms");
            Assert.assertTrue(loadTime > 0, "Load time should be positive");
            Assert.assertTrue(loadTime < 30000, "Load time should be reasonable");
            
        } catch (Exception e) {
            System.err.println("❌ Performance test failed: " + e.getMessage());
            Assert.fail("Performance test failed: " + e.getMessage());
        }
    }
}