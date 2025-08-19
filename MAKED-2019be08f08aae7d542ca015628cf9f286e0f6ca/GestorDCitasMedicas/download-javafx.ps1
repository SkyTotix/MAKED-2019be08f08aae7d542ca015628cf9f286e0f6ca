# Script simple para descargar JavaFX
Write-Host "Descargando JavaFX SDK..."

# URL de JavaFX
$url = "https://download2.gluonhq.com/openjfx/21/openjfx-21_windows-x64_bin-sdk.zip"
$output = "javafx-sdk.zip"

# Descargar
Invoke-WebRequest -Uri $url -OutFile $output

# Extraer
Write-Host "Extrayendo JavaFX SDK..."
Expand-Archive -Path $output -DestinationPath "." -Force

# Limpiar
Remove-Item $output

Write-Host "JavaFX SDK descargado y extraído correctamente."
Write-Host "Ahora puedes ejecutar la aplicación desde VS Code o usar el script run.ps1"
