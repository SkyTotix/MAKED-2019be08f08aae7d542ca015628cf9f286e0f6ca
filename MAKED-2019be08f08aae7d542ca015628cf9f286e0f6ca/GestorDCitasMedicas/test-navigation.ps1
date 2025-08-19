# Script para probar la navegación de los botones cancelar
Write-Host "=== PRUEBA DE NAVEGACIÓN - BOTONES CANCELAR ===" -ForegroundColor Cyan

# Verificar que existe JavaFX SDK
if (-not (Test-Path "javafx-sdk-21\lib")) {
    Write-Host "Error: No se encontro JavaFX SDK. Asegurese de que javafx-sdk-21 este en el directorio actual." -ForegroundColor Red
    exit 1
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

Write-Host "=== INSTRUCCIONES DE PRUEBA ===" -ForegroundColor Green
Write-Host "1. La aplicacion se abrira automaticamente" -ForegroundColor White
Write-Host "2. Inicia sesion como paciente (usuario: paciente, password: 123)" -ForegroundColor White
Write-Host "3. Haz clic en 'Cancelar Cita' y luego en el boton 'Cancelar'" -ForegroundColor White
Write-Host "4. Verifica que regresa al menu principal sin cerrar la app" -ForegroundColor White
Write-Host "5. Haz clic en 'Reprogramar Cita' y luego en el boton 'Cancelar'" -ForegroundColor White
Write-Host "6. Verifica que regresa al menu principal sin cerrar la app" -ForegroundColor White
Write-Host "7. Cierra la aplicacion cuando termines las pruebas" -ForegroundColor White

Write-Host "=== EJECUTANDO APLICACION ===" -ForegroundColor Yellow
# Ejecutar la aplicacion
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;javafx-sdk-21/lib/*" com.gestorcitasmedicas.App

Write-Host "=== PRUEBA TERMINADA ===" -ForegroundColor Green
