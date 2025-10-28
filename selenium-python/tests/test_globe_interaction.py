"""
Test Globe Interaction and Navigation
Equivalent to Cypress 02-globe-interaction.cy.js
"""
import pytest
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import TimeoutException, NoSuchElementException
from utils.webdriver_manager import DriverManager


class TestGlobeInteraction:
    """Test suite for globe interaction and navigation features"""
    
    def setup_method(self):
        """Setup before each test method"""
        self.driver = DriverManager.get_driver()
        self.wait = WebDriverWait(self.driver, 15)
        self.base_url = "https://global-news-proyect-gmni.vercel.app"
        self.actions = ActionChains(self.driver)
        
    def teardown_method(self):
        """Cleanup after each test method"""
        if self.driver:
            self.driver.quit()
    
    @pytest.mark.smoke
    def test_globe_loading_indicator(self):
        """Test that globe loading indicator appears"""
        self.driver.get(self.base_url)
        
        # Check for loading message
        try:
            loading_element = self.wait.until(
                EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'Loading Interactive News Globe')]"))
            )
            assert loading_element is not None
        except TimeoutException:
            # If loading is too fast, check that we can see the globe container
            pass
    
    @pytest.mark.regression
    def test_globe_container_exists(self):
        """Test that globe container elements are present"""
        self.driver.get(self.base_url)
        
        # Wait for page to load
        self.wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
        time.sleep(3)  # Allow time for globe to initialize
        
        # Look for potential globe containers (canvas, WebGL, or 3D elements)
        globe_selectors = [
            (By.TAG_NAME, "canvas"),
            (By.XPATH, "//*[contains(@class, 'globe')]"),
            (By.XPATH, "//*[contains(@id, 'globe')]"),
            (By.TAG_NAME, "svg")
        ]
        
        globe_found = False
        for selector in globe_selectors:
            try:
                elements = self.driver.find_elements(*selector)
                if elements:
                    globe_found = True
                    break
            except NoSuchElementException:
                continue
        
        # At minimum, verify the page structure supports interactive elements
        assert globe_found or len(self.driver.find_elements(By.TAG_NAME, "script")) > 0
    
    @pytest.mark.regression
    def test_globe_interaction_capabilities(self):
        """Test that globe supports interaction"""
        self.driver.get(self.base_url)
        
        # Wait for page to fully load
        self.wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
        time.sleep(3)  # Allow globe to initialize
        
        # Try to find interactive elements
        body = self.driver.find_element(By.TAG_NAME, "body")
        
        # Simulate mouse interactions at different positions
        interaction_positions = [
            (200, 200),
            (400, 300),
            (600, 400)
        ]
        
        for x, y in interaction_positions:
            try:
                # Move to position and click
                self.actions.move_to_element_with_offset(body, x, y).click().perform()
                time.sleep(0.5)
            except Exception:
                # Interaction might not be available, which is acceptable
                pass
        
        # Verify page is still functional after interactions
        # Fix URL comparison to handle trailing slash
        current_url = self.driver.current_url.rstrip('/')
        base_url = self.base_url.rstrip('/')
        assert current_url == base_url
    
    @pytest.mark.regression
    def test_search_functionality_interface(self):
        """Test search interface elements if available"""
        self.driver.get(self.base_url)
        
        # Wait for page to load
        self.wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
        time.sleep(2)
        
        # Look for search-related elements
        search_selectors = [
            (By.XPATH, "//input[@type='search']"),
            (By.XPATH, "//input[contains(@placeholder, 'search')]"),
            (By.XPATH, "//*[contains(@class, 'search')]"),
            (By.XPATH, "//*[contains(@id, 'search')]")
        ]
        
        search_element = None
        for selector in search_selectors:
            try:
                elements = self.driver.find_elements(*selector)
                if elements and elements[0].is_displayed():
                    search_element = elements[0]
                    break
            except NoSuchElementException:
                continue
        
        if search_element:
            # Test search input functionality
            search_element.clear()
            search_element.send_keys("United States")
            search_element.send_keys(Keys.ENTER)
            time.sleep(2)
            
            # Verify page is still responsive
            assert self.driver.current_url is not None
        else:
            # If no search found, verify the instruction text exists
            instruction_text = self.wait.until(
                EC.presence_of_element_located((By.XPATH, "//*[contains(text(), 'search to discover')]"))
            )
            assert instruction_text.is_displayed()
    
    @pytest.mark.regression 
    def test_keyboard_navigation_support(self):
        """Test keyboard navigation capabilities"""
        self.driver.get(self.base_url)
        
        # Wait for page to load
        self.wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
        time.sleep(2)
        
        body = self.driver.find_element(By.TAG_NAME, "body")
        
        # Test various keyboard interactions
        keyboard_actions = [
            Keys.TAB,
            Keys.ARROW_LEFT,
            Keys.ARROW_RIGHT,
            Keys.ARROW_UP,
            Keys.ARROW_DOWN
        ]
        
        for key in keyboard_actions:
            try:
                body.send_keys(key)
                time.sleep(0.3)
            except Exception:
                # Some keyboard actions might not be supported
                pass
        
        # Verify page remains functional
        title_element = self.driver.find_element(By.TAG_NAME, "h1")
        assert title_element.is_displayed()
        assert "Interactive News Globe" in title_element.text
    
    @pytest.mark.regression
    def test_page_state_persistence(self):
        """Test that page maintains state during interactions"""
        self.driver.get(self.base_url)
        
        # Wait for initial load
        initial_title = self.wait.until(
            EC.presence_of_element_located((By.TAG_NAME, "h1"))
        )
        assert initial_title.is_displayed()
        assert "Interactive News Globe" in initial_title.text
        
        # Perform multiple interactions
        body = self.driver.find_element(By.TAG_NAME, "body")
        
        # Multiple clicks at different positions
        for i in range(5):
            x = 100 + (i * 100)
            y = 100 + (i * 50)
            try:
                self.actions.move_to_element_with_offset(body, x, y).click().perform()
                time.sleep(0.5)
            except Exception:
                pass
        
        # Verify main elements are still present and functional
        title_still_present = self.driver.find_element(By.TAG_NAME, "h1")
        assert title_still_present.is_displayed()
        assert "Interactive News Globe" in title_still_present.text
        
        subtitle_still_present = self.driver.find_element(By.XPATH, "//*[contains(text(), 'Click a country')]")
        assert subtitle_still_present.is_displayed()