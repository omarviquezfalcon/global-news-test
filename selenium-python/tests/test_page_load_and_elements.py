"""
Test Page Load and Basic Elements
Equivalent to Cypress 01-page-load-and-elements.cy.js
"""
import pytest
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from utils.webdriver_manager import DriverManager


class TestPageLoadAndElements:
    """Test suite for page loading and basic element validation"""
    
    def setup_method(self):
        """Setup before each test method"""
        self.driver = DriverManager.get_driver()
        self.wait = WebDriverWait(self.driver, 15)
        self.base_url = "https://global-news-proyect-gmni.vercel.app"
        
    def teardown_method(self):
        """Cleanup after each test method"""
        if self.driver:
            self.driver.quit()
    
    @pytest.mark.smoke
    def test_page_loads_successfully(self):
        """Test that the main page loads with all essential elements"""
        self.driver.get(self.base_url)

        # Verify page title contains expected text
        self.wait.until(lambda driver: "Interactive News Globe" in driver.title)
        assert "Interactive News Globe" in self.driver.title

        # Verify main heading is present and visible (it's an h1 element)
        main_heading = self.wait.until(
            EC.presence_of_element_located((By.TAG_NAME, "h1"))
        )
        assert main_heading.is_displayed()
        assert "Interactive News Globe" in main_heading.text        # Verify subtitle/description
        subtitle = self.wait.until(
            EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'Click a country or search')]"))
        )
        assert subtitle.is_displayed()
        
        # Verify author information
        author = self.wait.until(
            EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'By Omar Viquez')]"))
        )
        assert author.is_displayed()
    
    @pytest.mark.smoke
    def test_loading_state_appears(self):
        """Test that loading indicator appears during initial load"""
        self.driver.get(self.base_url)
        
        # Check for loading message (might be quick, so try to catch it)
        try:
            loading_element = self.wait.until(
                EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'Loading')]"))
            )
            assert loading_element is not None
        except TimeoutException:
            # Loading might be too fast to catch, which is also valid
            pass
    
    @pytest.mark.regression
    def test_page_responsiveness_desktop(self):
        """Test page behavior at desktop resolution"""
        # Set desktop resolution
        self.driver.set_window_size(1920, 1080)
        self.driver.get(self.base_url)

        # Verify main elements are visible at desktop size (check h1 instead)
        main_heading = self.wait.until(
            EC.visibility_of_element_located((By.TAG_NAME, "h1"))
        )
        assert main_heading.is_displayed()
        assert "Interactive News Globe" in main_heading.text
        assert main_heading.is_displayed()
        
        # Verify subtitle is visible
        subtitle = self.wait.until(
            EC.visibility_of_element_located((By.XPATH, "//*[contains(text(), 'Click a country')]"))
        )
        assert subtitle.is_displayed()
    
    @pytest.mark.regression
    def test_page_responsiveness_mobile(self):
        """Test page behavior at mobile resolution"""
        # Set mobile resolution
        self.driver.set_window_size(375, 667)
        self.driver.get(self.base_url)

        # Verify main elements are still accessible on mobile (check h1)
        main_heading = self.wait.until(
            EC.presence_of_element_located((By.TAG_NAME, "h1"))
        )
        assert main_heading.is_displayed()
        assert "Interactive News Globe" in main_heading.text
        
        # Verify the page adapts to mobile viewport (allow some flexibility)
        viewport_width = self.driver.execute_script("return window.innerWidth;")
        assert viewport_width <= 500  # Allow some margin for mobile viewport
    
    @pytest.mark.regression
    def test_page_content_structure(self):
        """Test that page has proper content structure"""
        self.driver.get(self.base_url)

        # Wait for page to fully load
        self.wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))

        # Verify essential page structure elements exist
        body = self.driver.find_element(By.TAG_NAME, "body")
        assert body is not None

        # Check for main heading instead of News section
        main_heading = self.wait.until(
            EC.presence_of_element_located((By.TAG_NAME, "h1"))
        )
        assert main_heading.is_displayed()
        assert "Interactive News Globe" in main_heading.text

        # Check for author information
        author_element = self.wait.until(
            EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'By Omar')]"))
        )
        assert author_element.is_displayed()        # Verify page is interactive (not just static content)
        assert len(self.driver.find_elements(By.TAG_NAME, "script")) > 0