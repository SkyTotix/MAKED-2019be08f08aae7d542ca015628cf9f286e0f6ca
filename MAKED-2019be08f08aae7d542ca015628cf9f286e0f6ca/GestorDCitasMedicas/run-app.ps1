# Script para ejecutar la aplicación JavaFX desde cualquier ubicación
param(
    [string]$ProjectPath = "."
)

# Obtener la ruta absoluta del proyecto
$ProjectPath = Resolve-Path $ProjectPath
Write-Host "Ejecutando aplicación desde: $ProjectPath"

# Verificar que JavaFX esté disponible
$JavaFXPath = Join-Path $ProjectPath "javafx-sdk-21"
if (-not (Test-Path $JavaFXPath)) {
    Write-Host "JavaFX no encontrado en: $JavaFXPath"
    Write-Host "Ejecutando script de descarga..."
    & (Join-Path $ProjectPath "download-javafx.ps1")
}

# Verificar que las clases estén compiladas
$ClassesPath = Join-Path $ProjectPath "target/classes"
if (-not (Test-Path $ClassesPath)) {
    Write-Host "Clases no encontradas. Compilando..."
    javac -d $ClassesPath (Join-Path $ProjectPath "src/main/java/com/gestorcitasmedicas/App.java")
}

# Ejecutar la aplicación
Write-Host "Ejecutando la aplicación..."
$JavaFXLibPath = Join-Path $JavaFXPath "lib"
$ClassesPath = Join-Path $ProjectPath "target/classes"

java --module-path "$JavaFXLibPath" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED -cp "$ClassesPath" com.gestorcitasmedicas.App
