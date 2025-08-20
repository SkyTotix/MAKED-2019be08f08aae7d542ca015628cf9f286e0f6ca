# Script para verificar la configuracion del proyecto
Write-Host "=== Verificacion de Configuracion del Proyecto ===" -ForegroundColor Green
Write-Host ""

# Verificar Java
Write-Host "1. Verificando Java..." -ForegroundColor Yellow
$javaVersion = java -version 2>&1 | Select-String "version"
if ($javaVersion -match '"(\d+)\.') {
    $majorVersion = [int]$matches[1]
    if ($majorVersion -ge 17) {
        Write-Host "   OK Java $majorVersion encontrado" -ForegroundColor Green
    } else {
        Write-Host "   ERROR Java $majorVersion encontrado. Se requiere Java 17 o superior" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "   ERROR No se pudo determinar la version de Java" -ForegroundColor Red
    Write-Host "   Instale Java JDK 17 o superior desde: https://adoptium.net/" -ForegroundColor Yellow
    exit 1
}

# Verificar Maven
Write-Host ""
Write-Host "2. Verificando Maven..." -ForegroundColor Yellow
$mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
if ($mavenVersion) {
    Write-Host "   OK Maven encontrado" -ForegroundColor Green
    $mavenAvailable = $true
} else {
    Write-Host "   ADVERTENCIA Maven no encontrado (opcional)" -ForegroundColor Yellow
    $mavenAvailable = $false
}

# Verificar estructura del proyecto
Write-Host ""
Write-Host "3. Verificando estructura del proyecto..." -ForegroundColor Yellow

$requiredFiles = @(
    "src/main/java/com/gestorcitasmedicas/App.java",
    "src/main/resources/com/gestorcitasmedicas/Login.fxml",
    "pom.xml",
    "compilar-y-ejecutar.ps1"
)

$missingFiles = @()
foreach ($file in $requiredFiles) {
    if (Test-Path $file) {
        Write-Host "   OK $file" -ForegroundColor Green
    } else {
        Write-Host "   ERROR $file" -ForegroundColor Red
        $missingFiles += $file
    }
}

if ($missingFiles.Count -gt 0) {
    Write-Host ""
    Write-Host "   ERROR Faltan archivos requeridos:" -ForegroundColor Red
    foreach ($file in $missingFiles) {
        Write-Host "     - $file" -ForegroundColor Red
    }
    exit 1
}

# Verificar JavaFX
Write-Host ""
Write-Host "4. Verificando JavaFX..." -ForegroundColor Yellow

if (Test-Path "javafx-sdk-21") {
    Write-Host "   OK JavaFX SDK encontrado en el proyecto" -ForegroundColor Green
    $javafxLocal = $true
} elseif (Test-Path "javafx-sdk-17") {
    Write-Host "   OK JavaFX SDK encontrado en el proyecto" -ForegroundColor Green
    $javafxLocal = $true
} else {
    Write-Host "   ADVERTENCIA JavaFX SDK no encontrado localmente" -ForegroundColor Yellow
    $javafxLocal = $false
}

if ($mavenAvailable) {
    Write-Host "   OK Maven puede descargar JavaFX automaticamente" -ForegroundColor Green
    $javafxMaven = $true
} else {
    Write-Host "   ADVERTENCIA Maven no disponible para descargar JavaFX" -ForegroundColor Yellow
    $javafxMaven = $false
}

if (-not $javafxLocal -and -not $javafxMaven) {
    Write-Host ""
    Write-Host "   ERROR JavaFX no esta disponible" -ForegroundColor Red
    Write-Host "   Soluciones:" -ForegroundColor Yellow
    Write-Host "   1. Descargar JavaFX SDK desde: https://openjfx.io/" -ForegroundColor Yellow
    Write-Host "   2. Instalar Maven para descarga automatica" -ForegroundColor Yellow
    exit 1
}

# Resumen
Write-Host ""
Write-Host "=== Resumen de la Verificacion ===" -ForegroundColor Green
Write-Host "OK Java: Configurado correctamente" -ForegroundColor Green
Write-Host "OK Estructura del proyecto: Completa" -ForegroundColor Green

if ($mavenAvailable) {
    Write-Host "OK Maven: Disponible" -ForegroundColor Green
} else {
    Write-Host "ADVERTENCIA Maven: No disponible (opcional)" -ForegroundColor Yellow
}

if ($javafxLocal -or $javafxMaven) {
    Write-Host "OK JavaFX: Disponible" -ForegroundColor Green
} else {
    Write-Host "ERROR JavaFX: No disponible" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Opciones de Ejecucion ===" -ForegroundColor Green

if ($mavenAvailable) {
    Write-Host "1. Usando Maven (Recomendado):" -ForegroundColor Cyan
    Write-Host "   mvn clean compile" -ForegroundColor White
    Write-Host "   mvn javafx:run" -ForegroundColor White
    Write-Host ""
}

Write-Host "2. Usando script PowerShell:" -ForegroundColor Cyan
Write-Host "   .\compilar-y-ejecutar.ps1" -ForegroundColor White
Write-Host ""

Write-Host "3. Crear JAR ejecutable (con Maven):" -ForegroundColor Cyan
Write-Host "   mvn clean package" -ForegroundColor White
Write-Host "   java -jar target/gestor-citas-medicas-1.0.0.jar" -ForegroundColor White

Write-Host ""
Write-Host "Configuracion verificada exitosamente!" -ForegroundColor Green
