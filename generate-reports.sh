#!/bin/bash

# Script para generar todos los reportes de testing
# Ejecutar este script desde el directorio raíz del proyecto

echo "🚀 Iniciando generación de reportes completos..."

# Colores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}📊 Generando reportes de Python...${NC}"
cd selenium-python
if [ -d "venv" ]; then
    source venv/bin/activate
    python -m pytest tests/ -v --html=reports/selenium_report.html --self-contained-html --tb=short
    python -m pytest tests/ --alluredir=reports/allure-results
    allure generate reports/allure-results --clean -o reports/allure-report
    echo -e "${GREEN}✅ Reportes de Python generados${NC}"
else
    echo -e "${RED}❌ Entorno virtual de Python no encontrado${NC}"
fi
cd ..

echo -e "${BLUE}☕ Generando reportes de Java...${NC}"
cd selenium-java
mvn test -Dtest=SeleniumJavaTestSuite allure:report
echo -e "${GREEN}✅ Reportes de Java generados${NC}"
cd ..

echo -e "${BLUE}🌲 Generando reportes de Cypress...${NC}"
npx cypress run --env allure=true
allure generate allure-results --clean -o cypress-allure-report
echo -e "${GREEN}✅ Reportes de Cypress generados${NC}"

echo -e "${YELLOW}📋 Resumen de reportes generados:${NC}"
echo -e "   📊 Python HTML: selenium-python/reports/selenium_report.html"
echo -e "   📈 Python Allure: selenium-python/reports/allure-report/index.html"
echo -e "   📊 Java Surefire: selenium-java/target/surefire-reports/index.html"
echo -e "   📈 Java Allure: selenium-java/target/site/allure-maven-plugin/index.html"
echo -e "   📈 Cypress Allure: cypress-allure-report/index.html"
echo -e "   🎥 Cypress Videos: cypress/videos/"
echo -e "   📋 Dashboard: dashboard.html"

echo -e "${GREEN}🎉 ¡Todos los reportes han sido generados exitosamente!${NC}"
echo -e "${BLUE}🌐 Abrir dashboard.html en tu navegador para ver el resumen consolidado${NC}"