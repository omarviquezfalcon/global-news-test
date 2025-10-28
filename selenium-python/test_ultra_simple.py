# -*- coding: utf-8 -*-

import osimport os

import sysimport sys

sys.path.insert(0, os.path.abspath("."))sys.path.insert(0, os.path.abspath("."))



from selenium import webdriverfrom selenium import webdriver

from selenium.webdriver.chrome.service import Service as ChromeServicefrom selenium.webdriver.chrome.service import Service as ChromeService

from selenium.webdriver.chrome.options import Options as ChromeOptionsfrom selenium.webdriver.chrome.options import Options as ChromeOptions

from webdriver_manager.chrome import ChromeDriverManagerfrom webdriver_manager.chrome import ChromeDriverManager



def test_ultra_simple():def test_ultra_simple():

    try:    try:

        print("Configurando Chrome options...")        print("Configurando Chrome options...")

        chrome_options = ChromeOptions()        chrome_options = ChromeOptions()

        chrome_options.add_argument("--headless")        chrome_options.add_argument("--headless")

        chrome_options.add_argument("--no-sandbox")        chrome_options.add_argument("--no-sandbox")

        chrome_options.add_argument("--disable-dev-shm-usage")        chrome_options.add_argument("--disable-dev-shm-usage")

                

        print("Obteniendo ChromeDriver...")        print("Obteniendo ChromeDriver...")

        driver_path = ChromeDriverManager().install()        driver_path = ChromeDriverManager().install()

        print(f"Driver path original: {driver_path}")        print(f"Driver path original: {driver_path}")

                

        # Corregir path si es necesario        # Corregir path si es necesario

        if driver_path.endswith("THIRD_PARTY_NOTICES.chromedriver"):        if driver_path.endswith("THIRD_PARTY_NOTICES.chromedriver"):

            driver_dir = os.path.dirname(driver_path)            driver_dir = os.path.dirname(driver_path)

            actual_driver = os.path.join(driver_dir, "chromedriver.exe")            actual_driver = os.path.join(driver_dir, "chromedriver.exe")

            if os.path.exists(actual_driver):            if os.path.exists(actual_driver):

                driver_path = actual_driver                driver_path = actual_driver

                print(f"Driver path corregido: {driver_path}")                print(f"Driver path corregido: {driver_path}")

                

        print("Iniciando Chrome...")        print("Iniciando Chrome...")

        service = ChromeService(driver_path)        service = ChromeService(driver_path)

        driver = webdriver.Chrome(service=service, options=chrome_options)        driver = webdriver.Chrome(service=service, options=chrome_options)

                

        print("Navegando a Google...")        print("Navegando a Google...")

        driver.get("https://www.google.com")        driver.get("https://www.google.com")

                

        print(f"Titulo: {driver.title}")        print(f"T�tulo: {driver.title}")

        assert "Google" in driver.title        assert "Google" in driver.title

                

        print("Cerrando driver...")        print("Cerrando driver...")

        driver.quit()        driver.quit()

                

        print("TEST ULTRA SIMPLE EXITOSO!")        print("�TEST ULTRA SIMPLE EXITOSO!")

        return True        return True

                

    except Exception as e:    except Exception as e:

        print(f"Error en test ultra simple: {e}")        print(f"Error en test ultra simple: {e}")

        import traceback        return False

        traceback.print_exc()

        return Falseif __name__ == "__main__":

    test_ultra_simple()

if __name__ == "__main__":
    test_ultra_simple()