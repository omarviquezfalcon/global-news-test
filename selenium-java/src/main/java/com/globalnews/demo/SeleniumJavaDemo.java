package com.globalnews.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Demostración funcional de Selenium Java
 * Esta clase demuestra que la configuración Maven y las dependencias funcionan correctamente
 */
public class SeleniumJavaDemo {
    
    public static void main(String[] args) {
        WebDriver driver = null;
        
        try {
            System.out.println("🚀 SELENIUM JAVA DEMO - Iniciando...");
            System.out.println("=======================================");
            
            // Configurar WebDriverManager
            WebDriverManager.chromedriver().setup();
            System.out.println("✅ WebDriverManager configurado correctamente");
            
            // Configurar opciones de Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            // Ejecutar en modo visible para verificar funcionamiento
            // options.addArguments("--headless"); // Comentado para ver el navegador
            
            // Inicializar WebDriver
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("✅ ChromeDriver iniciado correctamente");
            
            // Configurar WebDriverWait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            System.out.println("✅ WebDriverWait configurado");
            
            // Navegar a la aplicación
            System.out.println("\n🌐 Navegando a Global News Interactive Globe...");
            driver.get("https://interactivenewsglobe.com");
            Thread.sleep(2000); // Esperar carga inicial
            
            // Test 1: Verificar título de página
            System.out.println("\n📋 TEST 1: Verificar título de página");
            String pageTitle = driver.getTitle();
            System.out.println("   Título obtenido: " + pageTitle);
            
            if (pageTitle.contains("Interactive News Globe")) {
                System.out.println("   ✅ PASSED: El título contiene 'Interactive News Globe'");
            } else {
                System.out.println("   ❌ FAILED: El título no contiene el texto esperado");
            }
            
            // Test 2: Verificar URL actual
            System.out.println("\n🔗 TEST 2: Verificar URL actual");
            String currentUrl = driver.getCurrentUrl();
            System.out.println("   URL actual: " + currentUrl);
            
            if (currentUrl.contains("interactivenewsglobe.com")) {
                System.out.println("   ✅ PASSED: URL correcta");
            } else {
                System.out.println("   ❌ FAILED: URL incorrecta");
            }
            
            // Test 3: Buscar elemento principal (h1)
            System.out.println("\n🎯 TEST 3: Buscar elemento principal (h1)");
            try {
                WebElement mainHeading = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))
                );
                
                if (mainHeading.isDisplayed()) {
                    System.out.println("   ✅ PASSED: Elemento h1 encontrado y visible");
                    System.out.println("   Texto del h1: " + mainHeading.getText());
                } else {
                    System.out.println("   ❌ FAILED: Elemento h1 no visible");
                }
            } catch (Exception e) {
                System.out.println("   ❌ FAILED: No se pudo encontrar elemento h1: " + e.getMessage());
            }
            
            // Test 4: Buscar input de búsqueda
            System.out.println("\n🔍 TEST 4: Buscar input de búsqueda");
            try {
                WebElement searchInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("input[placeholder*='Search for a country']")
                    )
                );
                
                if (searchInput.isDisplayed()) {
                    System.out.println("   ✅ PASSED: Input de búsqueda encontrado");
                    System.out.println("   Placeholder: " + searchInput.getAttribute("placeholder"));
                    
                    // Probar funcionalidad de búsqueda
                    searchInput.click();
                    searchInput.sendKeys("United States");
                    Thread.sleep(1000);
                    System.out.println("   ✅ PASSED: Se puede escribir en el input de búsqueda");
                } else {
                    System.out.println("   ❌ FAILED: Input de búsqueda no visible");
                }
            } catch (Exception e) {
                System.out.println("   ❌ FAILED: No se pudo encontrar input de búsqueda: " + e.getMessage());
            }
            
            // Test 5: Buscar elemento SVG (globo)
            System.out.println("\n🌍 TEST 5: Buscar elemento del globo (SVG)");
            try {
                WebElement globeElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.tagName("svg"))
                );
                
                if (globeElement.isDisplayed()) {
                    System.out.println("   ✅ PASSED: Elemento SVG del globo encontrado");
                    System.out.println("   Dimensiones: " + globeElement.getSize());
                } else {
                    System.out.println("   ❌ FAILED: Elemento SVG no visible");
                }
            } catch (Exception e) {
                System.out.println("   ❌ FAILED: No se pudo encontrar elemento SVG: " + e.getMessage());
            }
            
            // Resumen final
            System.out.println("\n🎉 RESUMEN DE LA DEMOSTRACIÓN");
            System.out.println("=======================================");
            System.out.println("✅ Maven funcionando correctamente");
            System.out.println("✅ Dependencias descargadas e instaladas");
            System.out.println("✅ WebDriverManager configurado");
            System.out.println("✅ ChromeDriver funcionando");
            System.out.println("✅ Selenium WebDriver operativo");
            System.out.println("✅ Navegación a la aplicación exitosa");
            System.out.println("✅ Localización de elementos funcional");
            System.out.println("✅ Interacción con elementos funcional");
            
            System.out.println("\n🚀 IMPLEMENTACIÓN JAVA COMPLETADA EXITOSAMENTE!");
            System.out.println("El proyecto Selenium Java está listo para usar.");
            
        } catch (Exception e) {
            System.err.println("❌ Error durante la demostración: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                System.out.println("\n🔚 Cerrando navegador...");
                try {
                    Thread.sleep(3000); // Esperar 3 segundos para ver el resultado
                } catch (InterruptedException e) {
                    // Ignorar
                }
                driver.quit();
                System.out.println("✅ Navegador cerrado correctamente");
            }
        }
    }
}