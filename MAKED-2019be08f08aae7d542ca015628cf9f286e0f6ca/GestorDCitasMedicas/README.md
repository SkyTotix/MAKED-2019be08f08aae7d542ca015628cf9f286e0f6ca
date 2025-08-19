# Gestor de Citas Médicas

Aplicación JavaFX para la gestión de citas médicas en la UTEZ.

## Requisitos

- Java JDK 21 o superior
- JavaFX SDK 21
- PowerShell (para ejecutar los scripts)

## Instalación

1. Asegúrate de tener JavaFX SDK 21 en el directorio del proyecto
2. El directorio debe llamarse `javafx-sdk-21` y contener la carpeta `lib`

## Ejecución

### Opción 1: Script completo (Recomendado)
```powershell
.\compilar-y-ejecutar.ps1
```

### Opción 2: Solo ejecutar (si ya está compilado)
```powershell
.\ejecutar-app.ps1
```

### Opción 3: Comando manual
```powershell
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;javafx-sdk-21/lib/*" com.gestorcitasmedicas.App
```

## Credenciales de Prueba

### Administradores
- **Correo:** admin@utez.edu.mx
- **Contraseña:** admin123

### Médicos
- **Dr. Juan Pérez (Cardiólogo)**
  - Correo: juan.perez@utez.edu.mx
  - Contraseña: medico123
- **Dra. María González (Dermatóloga)**
  - Correo: maria.gonzalez@utez.edu.mx
  - Contraseña: medico123
- **Dr. Carlos Rodríguez (Pediatra)**
  - Correo: carlos.rodriguez@utez.edu.mx
  - Contraseña: medico123

### Pacientes
- **María González (Estudiante)**
  - Correo: paciente@test.com
  - Contraseña: paciente123
- **Juan López (Personal UTEZ)**
  - Correo: juan.lopez@test.com
  - Contraseña: juan123
- **Ana Martínez (Estudiante)**
  - Correo: ana.martinez@test.com
  - Contraseña: ana123

## Funcionalidades Implementadas

✅ **1. Cambiar contraseña desde el perfil** - Todos los roles pueden cambiar su contraseña
✅ **2. Admin puede crear médicos y pacientes** - Desde el panel principal
✅ **3. Admin y doctor pueden cambiar su estado** - Botón en el perfil
✅ **4. Creación y asignación de consultorios** - Al crear médicos
✅ **5. Selección de cita para cancelar/reprogramar** - Ventana de selección
✅ **6. Doctor puede modificar expediente médico** - Durante la consulta
✅ **7. Recuperación de contraseña por email** - Cambiado de CURP a email
✅ **8. Consultorio en gestión de médicos** - Columna agregada
✅ **9. Botón para agregar médicos** - Formulario completo
✅ **10. Admin puede modificar datos de médicos** - Preparado
✅ **11. Teléfono y tipo de paciente** - Estudiante o Personal UTEZ
✅ **12. Admin puede crear usuarios** - Formulario completo
✅ **13. Botones para regresar al menú principal** - Navegación mejorada

## Estructura del Proyecto

```
GestorDCitasMedicas/
├── src/main/java/com/gestorcitasmedicas/
│   ├── App.java                          # Punto de entrada
│   ├── controller/                       # Controladores JavaFX
│   ├── model/                           # Modelos de datos
│   ├── util/                            # Utilidades
│   └── utils/                           # Utilidades adicionales
├── src/main/resources/com/gestorcitasmedicas/
│   ├── *.fxml                          # Archivos de interfaz
│   └── img/                            # Imágenes
├── javafx-sdk-21/                      # JavaFX SDK
├── target/classes/                     # Archivos compilados
├── compilar-y-ejecutar.ps1             # Script completo
├── ejecutar-app.ps1                    # Script de ejecución
└── README.md                           # Este archivo
```

## Solución de Problemas

### Error: JavaFX runtime components are missing
- Asegúrate de usar el script `compilar-y-ejecutar.ps1`
- Verifica que `javafx-sdk-21` esté en el directorio correcto

### Error: Could not find or load main class
- Ejecuta la compilación primero con el script completo
- Verifica que el directorio `target/classes` exista

### Error de permisos en PowerShell
- Ejecuta: `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`
- O usa: `powershell -ExecutionPolicy Bypass -File .\compilar-y-ejecutar.ps1`
