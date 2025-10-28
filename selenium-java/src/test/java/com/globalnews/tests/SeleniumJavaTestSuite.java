package com.globalnews.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

/**
 * Suite de pruebas Selenium Java independiente
 * No depende de archivos XML de configuración
 */
public class SeleniumJavaTestSuite {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        System.out.println("=== Iniciando configuración de WebDriver ===");
        
        try {
            // Configurar WebDriverManager para Chrome
            WebDriverManager.chromedriver().setup();
            
            // Configurar opciones de Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            
            // Crear instancia del WebDriver
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Configurar ventana del navegador
            driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            System.out.println("✅ WebDriver configurado exitosamente");
            
        } catch (Exception e) {
            System.err.println("❌ Error al configurar WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo en configuración de WebDriver", e);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✅ WebDriver cerrado exitosamente");
            } catch (Exception e) {
                System.err.println("⚠️ Error al cerrar WebDriver: " + e.getMessage());
            }
        }
    }
    
    @Test(groups = {"smoke", "infrastructure"}, priority = 1)
    public void testWebDriverInitialization() {
        System.out.println("🧪 Test: Inicialización de WebDriver");
        
        Assert.assertNotNull(driver, "WebDriver debe estar inicializado");
        Assert.assertNotNull(wait, "WebDriverWait debe estar inicializado");
        
        System.out.println("✅ WebDriver inicializado correctamente");
    }
    
    @Test(groups = {"smoke", "infrastructure"}, priority = 2)
    public void testNavigationToExampleSite() {
        System.out.println("🧪 Test: Navegación a sitio de ejemplo");
        
        String testUrl = "https://example.com";
        
        try {
            driver.get(testUrl);
            
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();
            
            Assert.assertTrue(currentUrl.contains("example.com"), 
                "URL actual debe contener 'example.com'");
            Assert.assertNotNull(pageTitle, "El título de la página no debe ser nulo");
            Assert.assertFalse(pageTitle.isEmpty(), "El título de la página no debe estar vacío");
            
            System.out.println("✅ Navegación exitosa - URL: " + currentUrl);
            System.out.println("✅ Título de página: " + pageTitle);
            
        } catch (Exception e) {
            System.err.println("❌ Error en navegación: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(groups = {"smoke", "infrastructure"}, priority = 3)
    public void testElementLocation() {
        System.out.println("🧪 Test: Localización de elementos");
        
        try {
            driver.get("https://example.com");
            
            // Buscar elemento h1
            var h1Element = driver.findElement(By.tagName("h1"));
            Assert.assertNotNull(h1Element, "Elemento H1 debe existir");
            
            String h1Text = h1Element.getText();
            Assert.assertNotNull(h1Text, "Texto del H1 no debe ser nulo");
            Assert.assertFalse(h1Text.isEmpty(), "Texto del H1 no debe estar vacío");
            
            System.out.println("✅ Elemento H1 encontrado: " + h1Text);
            
        } catch (Exception e) {
            System.err.println("❌ Error en localización de elementos: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(groups = {"smoke", "infrastructure"}, priority = 4)
    public void testBrowserInteractions() {
        System.out.println("🧪 Test: Interacciones del navegador");
        
        try {
            // Navegar a ejemplo
            driver.get("https://example.com");
            String firstUrl = driver.getCurrentUrl();
            
            // Navegar a otra página
            driver.get("https://httpbin.org");
            String secondUrl = driver.getCurrentUrl();
            
            // Verificar navegación hacia atrás
            driver.navigate().back();
            String backUrl = driver.getCurrentUrl();
            
            Assert.assertTrue(firstUrl.contains("example.com"), "Primera URL debe ser example.com");
            Assert.assertTrue(secondUrl.contains("httpbin.org"), "Segunda URL debe ser httpbin.org");
            Assert.assertTrue(backUrl.contains("example.com"), "URL tras navegación atrás debe ser example.com");
            
            System.out.println("✅ Navegación exitosa entre páginas");
            System.out.println("✅ Navegación atrás funcional");
            
        } catch (Exception e) {
            System.err.println("❌ Error en interacciones: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(groups = {"performance", "infrastructure"}, priority = 5)
    public void testPageLoadPerformance() {
        System.out.println("🧪 Test: Rendimiento de carga de página");
        
        try {
            long startTime = System.currentTimeMillis();
            
            driver.get("https://example.com");
            
            // Esperar que la página cargue completamente
            wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
            
            long endTime = System.currentTimeMillis();
            long loadTime = endTime - startTime;
            
            Assert.assertTrue(loadTime < 10000, 
                "La página debe cargar en menos de 10 segundos. Tiempo actual: " + loadTime + "ms");
            
            System.out.println("✅ Página cargada en: " + loadTime + "ms");
            
        } catch (Exception e) {
            System.err.println("❌ Error en test de rendimiento: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(groups = {"performance", "infrastructure"}, priority = 6)
    public void testJavaScriptExecution() {
        System.out.println("🧪 Test: Ejecución de JavaScript");
        
        try {
            driver.get("https://example.com");
            
            // Ejecutar JavaScript para obtener información de la página
            String pageTitle = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.title;");
                
            Object pageHeight = ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.body.scrollHeight;");
            
            Assert.assertNotNull(pageTitle, "Título obtenido via JavaScript no debe ser nulo");
            Assert.assertNotNull(pageHeight, "Altura de página no debe ser nula");
            
            System.out.println("✅ JavaScript ejecutado - Título: " + pageTitle);
            System.out.println("✅ JavaScript ejecutado - Altura: " + pageHeight);
            
        } catch (Exception e) {
            System.err.println("❌ Error en ejecución JavaScript: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(groups = {"comprehensive"}, priority = 7)
    public void testSeleniumInfrastructureComplete() {
        System.out.println("🧪 Test: Infraestructura Selenium Completa");
        
        try {
            // Test completo de funcionalidad
            driver.get("https://httpbin.org/html");
            
            String pageSource = driver.getPageSource();
            Assert.assertTrue(pageSource.contains("html"), "Página debe contener HTML");
            
            // Verificar título y URL
            String title = driver.getTitle();
            String url = driver.getCurrentUrl();
            
            Assert.assertNotNull(title, "Título no debe ser nulo");
            Assert.assertTrue(url.contains("httpbin.org"), "URL debe contener httpbin.org");
            
            // Redimensionar ventana
            driver.manage().window().setSize(new Dimension(1024, 768));
            
            // Ejecutar JavaScript
            Boolean isDocumentReady = (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState === 'complete';");
            
            Assert.assertTrue(isDocumentReady, "Documento debe estar completamente cargado");
            
            System.out.println("✅ Test completo de infraestructura exitoso");
            System.out.println("✅ Título: " + title);
            System.out.println("✅ URL: " + url);
            System.out.println("✅ Documento listo: " + isDocumentReady);
            
        } catch (Exception e) {
            System.err.println("❌ Error en test completo: " + e.getMessage());
            throw e;
        }
    }
}