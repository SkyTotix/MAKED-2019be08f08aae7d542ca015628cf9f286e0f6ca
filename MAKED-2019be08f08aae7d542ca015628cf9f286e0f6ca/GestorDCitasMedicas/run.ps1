# Script para ejecutar la aplicación JavaFX
# Descargar JavaFX si no está disponible

$javafxVersion = "21"
$javafxUrl = "https://download2.gluonhq.com/openjfx/$javafxVersion/openjfx-$javafxVersion_windows-x64_bin-sdk.zip"
$javafxZip = "javafx-sdk.zip"
$javafxDir = "javafx-sdk-21"

# Verificar si JavaFX ya está descargado
if (-not (Test-Path $javafxDir)) {
    Write-Host "Descargando JavaFX SDK..."
    
    # Descargar JavaFX
    Invoke-WebRequest -Uri $javafxUrl -OutFile $javafxZip
    
    # Extraer JavaFX
    Write-Host "Extrayendo JavaFX SDK..."
    Expand-Archive -Path $javafxZip -DestinationPath "." -Force
    
    # Limpiar archivo ZIP
    Remove-Item $javafxZip
}

# Compilar el proyecto
Write-Host "Compilando el proyecto..."
javac -cp "target/classes" -d target/classes src/main/java/com/gestorcitasmedicas/App.java

# Ejecutar la aplicación
Write-Host "Ejecutando la aplicación..."
java --module-path "$javafxDir/lib" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED -cp "target/classes" com.gestorcitasmedicas.App
