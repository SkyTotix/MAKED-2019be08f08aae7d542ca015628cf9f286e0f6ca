# Script para corregir el archivo VistaPaciente.fxml después de la compilación
Write-Host "Corrigiendo archivo VistaPaciente.fxml..." -ForegroundColor Yellow

$archivoFxml = "target\classes\com\gestorcitasmedicas\VistaPaciente.fxml"

if (Test-Path $archivoFxml) {
    # Leer el contenido del archivo
    $contenido = Get-Content $archivoFxml -Raw
    
    # Eliminar el bloque completo de "Gestionar Cita"
    $contenido = $contenido -replace '(?s)<!-- Gestionar Cita -->.*?</VBox>\s*(?=<!-- Historial de Citas -->)', ''
    
    # Limpiar el final del archivo
    $contenido = $contenido.Trim()
    
    # Escribir el contenido corregido de vuelta al archivo sin BOM
    [System.IO.File]::WriteAllText($archivoFxml, $contenido, [System.Text.Encoding]::UTF8)
    
    Write-Host "Archivo VistaPaciente.fxml corregido exitosamente!" -ForegroundColor Green
} else {
    Write-Host "Error: No se encontró el archivo VistaPaciente.fxml" -ForegroundColor Red
}
