@echo off
echo ====================================
echo Java Selenium Test Runner (Manual Compilation)
echo ====================================
echo.

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir lib

echo Downloading required JAR files...
echo.
echo Please download the following JAR files to the 'lib' directory:
echo.
echo 1. Selenium Java (4.15.0):
echo    https://selenium-release.storage.googleapis.com/4.15/selenium-java-4.15.0.zip
echo.
echo 2. TestNG (7.8.0):
echo    https://repo1.maven.org/maven2/org/testng/testng/7.8.0/testng-7.8.0.jar
echo.
echo 3. WebDriverManager (5.6.2):
echo    https://repo1.maven.org/maven2/io/github/bonigarcia/webdrivermanager/5.6.2/webdrivermanager-5.6.2.jar
echo.
echo 4. Allure TestNG (2.24.0):
echo    https://repo1.maven.org/maven2/io/qameta/allure/allure-testng/2.24.0/allure-testng-2.24.0.jar
echo.
echo Once downloaded, run: compile-and-run.bat
echo.
pause