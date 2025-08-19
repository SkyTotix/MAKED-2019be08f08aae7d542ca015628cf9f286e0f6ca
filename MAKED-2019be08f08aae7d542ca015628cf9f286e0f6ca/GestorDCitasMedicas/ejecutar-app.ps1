# Script para ejecutar la aplicación JavaFX
Write-Host "Ejecutando la aplicación JavaFX..." -ForegroundColor Green

# Verificar que existe el directorio target/classes
if (-not (Test-Path "target\classes")) {
    Write-Host "Error: No se encontró el directorio target/classes. Ejecute la compilación primero." -ForegroundColor Red
    exit 1
}

# Verificar que existe JavaFX SDK
if (-not (Test-Path "javafx-sdk-21\lib")) {
    Write-Host "Error: No se encontró JavaFX SDK. Asegúrese de que javafx-sdk-21 esté en el directorio actual." -ForegroundColor Red
    exit 1
}

# Ejecutar la aplicación con los parámetros correctos de JavaFX
Write-Host "Iniciando aplicación..." -ForegroundColor Yellow
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;javafx-sdk-21/lib/*" com.gestorcitasmedicas.App

Write-Host "Aplicación terminada." -ForegroundColor Green
