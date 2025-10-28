# Script para generar todos los reportes de testing
# Ejecutar este script desde el directorio raíz del proyecto

Write-Host "🚀 Iniciando generación de reportes completos..." -ForegroundColor Cyan

Write-Host "📊 Generando reportes de Python..." -ForegroundColor Blue
Set-Location selenium-python
if (Test-Path "venv") {
    & "venv\Scripts\Activate.ps1"
    python -m pytest tests/ -v --html=reports/selenium_report.html --self-contained-html --tb=short
    python -m pytest tests/ --alluredir=reports/allure-results
    allure generate reports/allure-results --clean -o reports/allure-report
    Write-Host "✅ Reportes de Python generados" -ForegroundColor Green
} else {
    Write-Host "❌ Entorno virtual de Python no encontrado" -ForegroundColor Red
}
Set-Location ..

Write-Host "☕ Generando reportes de Java..." -ForegroundColor Blue
Set-Location selenium-java
mvn test -Dtest=SeleniumJavaTestSuite allure:report
Write-Host "✅ Reportes de Java generados" -ForegroundColor Green
Set-Location ..

Write-Host "🌲 Generando reportes de Cypress..." -ForegroundColor Blue
npx cypress run --env allure=true
allure generate allure-results --clean -o cypress-allure-report
Write-Host "✅ Reportes de Cypress generados" -ForegroundColor Green

Write-Host "📋 Resumen de reportes generados:" -ForegroundColor Yellow
Write-Host "   📊 Python HTML: selenium-python/reports/selenium_report.html"
Write-Host "   📈 Python Allure: selenium-python/reports/allure-report/index.html"
Write-Host "   📊 Java Surefire: selenium-java/target/surefire-reports/index.html"
Write-Host "   📈 Java Allure: selenium-java/target/site/allure-maven-plugin/index.html"
Write-Host "   📈 Cypress Allure: cypress-allure-report/index.html"
Write-Host "   🎥 Cypress Videos: cypress/videos/"
Write-Host "   📋 Dashboard: dashboard.html"

Write-Host "🎉 ¡Todos los reportes han sido generados exitosamente!" -ForegroundColor Green
Write-Host "🌐 Abrir dashboard.html en tu navegador para ver el resumen consolidado" -ForegroundColor Blue