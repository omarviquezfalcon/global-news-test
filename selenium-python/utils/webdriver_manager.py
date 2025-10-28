"""
WebDriver Manager for Selenium Tests
Handles browser setup and configuration
"""
import os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.firefox.service import Service as FirefoxService
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions


class DriverManager:
    """Manages WebDriver instances for different browsers"""
    
    @staticmethod
    def get_driver(browser="chrome", headless=None):
        """
        Get WebDriver instance for specified browser
        
        Args:
            browser (str): Browser type ('chrome' or 'firefox')
            headless (bool): Run in headless mode (None = auto-detect CI)
            
        Returns:
            WebDriver: Configured WebDriver instance
        """
        # Auto-detect headless mode in CI environments
        if headless is None:
            headless = os.getenv('CI', 'false').lower() == 'true'
        
        if browser.lower() == "chrome":
            return DriverManager._get_chrome_driver(headless)
        elif browser.lower() == "firefox":
            return DriverManager._get_firefox_driver(headless)
        else:
            raise ValueError(f"Unsupported browser: {browser}")
    
    @staticmethod
    def _get_chrome_driver(headless=True):
        """Configure and return Chrome WebDriver"""
        chrome_options = ChromeOptions()
        
        if headless:
            chrome_options.add_argument("--headless")
        
        # Additional Chrome options for stability
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--window-size=1920,1080")
        chrome_options.add_argument("--disable-web-security")
        chrome_options.add_argument("--allow-running-insecure-content")
        chrome_options.add_argument("--disable-extensions")
        chrome_options.add_argument("--disable-background-timer-throttling")
        chrome_options.add_argument("--disable-backgrounding-occluded-windows")
        chrome_options.add_argument("--disable-renderer-backgrounding")
        
        try:
            # Fix webdriver-manager path issue
            driver_path = ChromeDriverManager().install()
            # If path points to THIRD_PARTY_NOTICES, fix it
            if driver_path.endswith('THIRD_PARTY_NOTICES.chromedriver'):
                driver_dir = os.path.dirname(driver_path)
                actual_driver = os.path.join(driver_dir, 'chromedriver.exe')
                if os.path.exists(actual_driver):
                    driver_path = actual_driver
                    
            service = ChromeService(driver_path)
            return webdriver.Chrome(service=service, options=chrome_options)
        except Exception as e:
            print(f"Error initializing Chrome driver: {e}")
            # Fallback: try without service
            try:
                return webdriver.Chrome(options=chrome_options)
            except Exception as e2:
                print(f"Chrome fallback failed: {e2}")
                raise e2
    
    @staticmethod
    def _get_firefox_driver(headless=True):
        """Configure and return Firefox WebDriver"""
        firefox_options = FirefoxOptions()
        
        if headless:
            firefox_options.add_argument("--headless")
        
        # Additional Firefox options
        firefox_options.add_argument("--width=1920")
        firefox_options.add_argument("--height=1080")
        
        service = FirefoxService(GeckoDriverManager().install())
        return webdriver.Firefox(service=service, options=firefox_options)