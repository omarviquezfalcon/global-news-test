# Pruebas Automatizadas de Cypress para Global News Project

Este proyecto contiene 3 pruebas automatizadas en Cypress para la aplicación Global News disponible en: https://global-news-proyect-gmni.vercel.app/

## 📋 Descripción de las Pruebas

### 1. **Prueba de Carga de Página** (`01-page-load.cy.js`)
- ✅ Verifica que la página principal carga correctamente
- ✅ Confirma la presencia de elementos principales (título, subtítulo, autor)
- ✅ Valida la carga del globo interactivo
- ✅ Prueba la responsividad en diferentes tamaños de pantalla

### 2. **Prueba de Navegación del Globo** (`02-globe-navigation.cy.js`)
- ✅ Verifica la carga y visualización del globo interactivo
- ✅ Prueba las interacciones con el globo (clics, navegación)
- ✅ Valida la funcionalidad de búsqueda (si está disponible)
- ✅ Testa la navegación por teclado
- ✅ Verifica que el estado de la página se mantiene durante las interacciones

### 3. **Prueba de Sección de Noticias** (`03-news-section.cy.js`)
- ✅ Verifica la visualización de la sección de noticias
- ✅ Valida el manejo correcto del estado "sin noticias disponibles"
- ✅ Confirma la información de actualización diaria
- ✅ Prueba la persistencia de la sección durante interacciones
- ✅ Valida el comportamiento responsivo de la sección

## 🚀 Instalación y Configuración

### Prerrequisitos
- Node.js (versión 14 o superior)
- npm o yarn

### Pasos de instalación

1. **Instalar dependencias:**
   ```powershell
   npm install
   ```

2. **Instalar Cypress:**
   ```powershell
   npm install cypress --save-dev
   ```

## 🧪 Ejecutar las Pruebas

### Modo Interactivo (recomendado para desarrollo)
```powershell
npm run cypress:open
```
Este comando abre la interfaz gráfica de Cypress donde puedes seleccionar y ejecutar las pruebas individualmente.

### Modo Headless (para CI/CD)
```powershell
npm run cypress:run
```
Este comando ejecuta todas las pruebas en modo headless y genera reportes.

### Ejecutar una prueba específica
```powershell
npx cypress run --spec "cypress/e2e/01-page-load.cy.js"
```

## 📁 Estructura del Proyecto

```
global test/
├── cypress/
│   ├── e2e/
│   │   ├── 01-page-load.cy.js      # Pruebas de carga de página
│   │   ├── 02-globe-navigation.cy.js # Pruebas de navegación del globo
│   │   └── 03-news-section.cy.js    # Pruebas de sección de noticias
│   └── support/
│       ├── commands.js              # Comandos personalizados
│       └── e2e.js                   # Configuración de soporte
├── cypress.config.js                # Configuración principal de Cypress
├── package.json                     # Dependencias y scripts
└── README.md                        # Este archivo
```

## ⚙️ Configuración

### Configuración de Cypress (`cypress.config.js`)
- **Base URL:** https://global-news-proyect-gmni.vercel.app
- **Viewport:** 1280x720 (desktop)
- **Timeouts:** 10 segundos por defecto
- **Videos y screenshots:** Habilitados para debugging

### Comandos Personalizados (`commands.js`)
- `waitForPageLoad()`: Espera a que la página cargue completamente
- `waitForGlobeLoad()`: Espera a que el globo interactivo se cargue
- `waitForNetworkIdle()`: Espera a que las peticiones de red se estabilicen

## 🎯 Casos de Uso Cubiertos

1. **Verificación de Carga:**
   - Carga exitosa de la aplicación
   - Presencia de elementos UI principales
   - Comportamiento responsivo

2. **Interactividad:**
   - Funcionalidad del globo interactivo
   - Navegación y búsqueda
   - Manejo de eventos de usuario

3. **Contenido Dinámico:**
   - Estado de "sin noticias"
   - Mensajes informativos
   - Persistencia de datos

## 🐛 Troubleshooting

### Problemas Comunes:

1. **Timeout en la carga del globo:**
   - Las pruebas incluyen timeouts extendidos para elementos que cargan dinámicamente
   - Si persiste, verificar la conexión a internet

2. **Elementos no encontrados:**
   - Las pruebas usan selectores flexibles que se adaptan a la estructura de la página
   - Los selectores incluyen múltiples alternativas

3. **Errores de viewport:**
   - Las pruebas incluyen validación en múltiples tamaños de pantalla
   - Configuración automática de viewport

## 📊 Reportes

Cypress genera automáticamente:
- Videos de las ejecuciones de pruebas
- Screenshots en caso de fallos
- Reportes de console en formato JSON

Los archivos se guardan en:
- `cypress/videos/`
- `cypress/screenshots/`

## 🔄 Mantenimiento

Para mantener las pruebas actualizadas:

1. **Revisar selectores:** Si la aplicación cambia, actualizar los selectores en las pruebas
2. **Actualizar timeouts:** Ajustar según el rendimiento de la aplicación
3. **Agregar nuevas pruebas:** Para nuevas funcionalidades de la aplicación

## 📝 Notas Adicionales

- Las pruebas están diseñadas para ser robustas y manejar estados dinámicos
- Se incluyen múltiples assertions por prueba para mayor cobertura
- Los comandos personalizados facilitan el mantenimiento y reutilización
- Las pruebas son independientes y pueden ejecutarse en cualquier orden