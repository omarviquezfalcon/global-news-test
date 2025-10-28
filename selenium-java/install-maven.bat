@echo off
echo ====================================
echo Maven Installation Script for Windows
echo ====================================
echo.
echo This script will help you install Apache Maven
echo.
echo Please follow these steps:
echo.
echo 1. Download Maven from: https://maven.apache.org/download.cgi
echo    - Download the Binary zip archive (e.g., apache-maven-3.9.6-bin.zip)
echo.
echo 2. Extract to C:\apache-maven-3.9.6
echo.
echo 3. Add to Windows PATH environment variable:
echo    - C:\apache-maven-3.9.6\bin
echo.
echo 4. Set JAVA_HOME environment variable to your Java installation:
echo    - JAVA_HOME=C:\Program Files\Java\jdk-21
echo.
echo 5. Restart Command Prompt and run: mvn --version
echo.
echo ====================================
echo Current Java installation detected:
java -version
echo ====================================
echo.
echo Alternative: Use IntelliJ IDEA or Eclipse with built-in Maven support
echo.
pause