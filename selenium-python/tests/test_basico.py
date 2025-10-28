"""
Test Básico para verificar que Selenium funciona
"""
import pytest
import time
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.chrome.options import Options as ChromeOptions
from utils.webdriver_manager import DriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class TestBasico:
    """Test básico para verificar configuración"""
    
    def setup_method(self):
        """Setup que usa el driver manager corregido"""
        chrome_options = ChromeOptions()
        chrome_options.add_argument("--headless")
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--window-size=1920,1080")

        # Usar DriverManager que tiene el chromedriver corregido
        self.driver = DriverManager.get_driver(browser="chrome", headless=True)
        self.wait = WebDriverWait(self.driver, 10)
        
    def teardown_method(self):
        """Cleanup"""
        if self.driver:
            self.driver.quit()
    
    def test_selenium_basico(self):
        """Test básico de conectividad"""
        # Ir a Google primero para verificar conectividad
        self.driver.get("https://www.google.com")
        assert "Google" in self.driver.title
        
        # Ahora probar la aplicación
        self.driver.get("https://global-news-proyect-gmni.vercel.app")
        
        # Verificar que la página carga
        self.wait.until(lambda driver: driver.title != "")
        
        # Verificar que contiene texto esperado
        body_text = self.driver.find_element(By.TAG_NAME, "body").text
        assert "Interactive News Globe" in body_text or "News" in body_text
        
        print(f"Título de la página: {self.driver.title}")
        print(f"URL actual: {self.driver.current_url}")
        print("Test básico completado exitosamente!")