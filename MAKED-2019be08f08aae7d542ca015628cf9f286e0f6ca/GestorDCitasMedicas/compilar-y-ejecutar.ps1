# Script para compilar y ejecutar la aplicacion JavaFX
Write-Host "=== COMPILANDO Y EJECUTANDO APLICACION JAVAFX ===" -ForegroundColor Cyan

# Verificar que existe JavaFX SDK
if (-not (Test-Path "javafx-sdk-21\lib")) {
    Write-Host "Error: No se encontro JavaFX SDK. Asegurese de que javafx-sdk-21 este en el directorio actual." -ForegroundColor Red
    exit 1
}

# Crear directorio target/classes si no existe
if (-not (Test-Path "target\classes")) {
    New-Item -ItemType Directory -Path "target\classes" -Force | Out-Null
    Write-Host "Directorio target/classes creado." -ForegroundColor Yellow
}

# Compilar el proyecto
Write-Host "Compilando el proyecto..." -ForegroundColor Yellow
javac -cp ".;javafx-sdk-21/lib/*" -d target/classes src/main/java/com/gestorcitasmedicas/*.java src/main/java/com/gestorcitasmedicas/controller/*.java src/main/java/com/gestorcitasmedicas/model/*.java src/main/java/com/gestorcitasmedicas/util/*.java src/main/java/com/gestorcitasmedicas/utils/*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error en la compilacion. Revisa los errores arriba." -ForegroundColor Red
    exit 1
}

Write-Host "Compilacion exitosa!" -ForegroundColor Green

# Copiar recursos FXML
Write-Host "Copiando recursos FXML..." -ForegroundColor Yellow
xcopy /E /I /Y src\main\resources\com\gestorcitasmedicas\*.fxml target\classes\com\gestorcitasmedicas\ > $null

# Copiar imagenes
if (Test-Path "src\main\resources\com\gestorcitasmedicas\img") {
    Write-Host "Copiando imagenes..." -ForegroundColor Yellow
    xcopy /E /I /Y src\main\resources\com\gestorcitasmedicas\img\* target\classes\com\gestorcitasmedicas\img\ > $null
}

# Ejecutar la aplicacion
Write-Host "Ejecutando la aplicacion..." -ForegroundColor Yellow
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;javafx-sdk-21/lib/*" com.gestorcitasmedicas.App

Write-Host "Aplicacion terminada." -ForegroundColor Green
