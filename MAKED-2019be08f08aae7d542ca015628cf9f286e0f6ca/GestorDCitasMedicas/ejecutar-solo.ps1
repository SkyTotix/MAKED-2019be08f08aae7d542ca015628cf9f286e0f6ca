# Script para ejecutar solo la aplicacion JavaFX (sin compilar)
Write-Host "=== EJECUTANDO APLICACION JAVAFX ===" -ForegroundColor Cyan

# Verificar que existe el directorio target/classes
if (-not (Test-Path "target\classes")) {
    Write-Host "Error: No se encontro el directorio target/classes. Ejecute la compilacion primero." -ForegroundColor Red
    exit 1
}

# Verificar que existe JavaFX SDK
if (-not (Test-Path "javafx-sdk-21\lib")) {
    Write-Host "Error: No se encontro JavaFX SDK. Asegurese de que javafx-sdk-21 este en el directorio actual." -ForegroundColor Red
    exit 1
}

# Ejecutar la aplicacion
Write-Host "Ejecutando la aplicacion..." -ForegroundColor Yellow
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;javafx-sdk-21/lib/*" com.gestorcitasmedicas.App

Write-Host "Aplicacion terminada." -ForegroundColor Green
