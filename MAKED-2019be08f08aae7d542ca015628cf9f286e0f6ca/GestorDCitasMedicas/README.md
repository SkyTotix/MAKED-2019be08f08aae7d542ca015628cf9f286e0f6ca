# Sistema de Gestión de Citas Médicas

## Requisitos del Sistema

### Software Requerido:
- **Java JDK 17** o superior
- **JavaFX SDK 17** o superior
- **Maven** (opcional, para gestión de dependencias)

### Configuración del Entorno:

#### 1. Instalar Java JDK 17+
```bash
# Verificar versión de Java
java -version
```

#### 2. Configurar JavaFX
**Opción A: Usando Maven (Recomendado)**
```xml
<!-- Agregar al pom.xml si no existe -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>17.0.2</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>17.0.2</version>
</dependency>
```

**Opción B: Configuración manual**
1. Descargar JavaFX SDK desde: https://openjfx.io/
2. Extraer en una carpeta (ej: `C:\javafx-sdk-17`)
3. Configurar variables de entorno:
   - `JAVAFX_HOME=C:\javafx-sdk-17`
   - Agregar `%JAVAFX_HOME%\bin` al PATH

#### 3. Configurar el IDE

**IntelliJ IDEA:**
1. File → Project Structure → Project Settings → Project
2. SDK: Seleccionar Java 17
3. File → Project Structure → Libraries
4. Agregar JavaFX SDK como librería

**Eclipse:**
1. Window → Preferences → Java → Installed JREs
2. Agregar Java 17
3. Run Configurations → VM Arguments:
   ```
   --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml
   ```

**NetBeans:**
1. Tools → Options → Java → JavaFX
2. JavaFX SDK: Seleccionar ruta del SDK

## Verificación de Configuración

Antes de compilar, es recomendable verificar que todo esté configurado correctamente:

```powershell
.\verificar-configuracion.ps1
```

Este script verificará:
- Versión de Java (requiere 17+)
- Disponibilidad de Maven
- Estructura del proyecto
- Configuración de JavaFX
- Permisos de PowerShell

## Compilación y Ejecución

### Usando el script PowerShell (Windows):
```powershell
.\compilar-y-ejecutar.ps1
```

### Usando Maven:
```bash
mvn clean compile
mvn javafx:run
```

### Usando Java directamente:
```bash
# Compilar
javac -cp "lib/*" -d target/classes src/main/java/com/gestorcitasmedicas/**/*.java

# Ejecutar
java --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;lib/*" com.gestorcitasmedicas.App
```

## Estructura del Proyecto

```
GestorDCitasMedicas/
├── src/
│   ├── main/
│   │   ├── java/com/gestorcitasmedicas/
│   │   │   ├── controller/     # Controladores FXML
│   │   │   ├── model/          # Modelos de datos
│   │   │   ├── utils/          # Utilidades (SesionManager)
│   │   │   └── App.java        # Punto de entrada
│   │   └── resources/
│   │       └── com/gestorcitasmedicas/
│   │           ├── *.fxml      # Archivos de interfaz
│   │           └── img/        # Imágenes
├── lib/                        # Librerías JavaFX
├── compilar-y-ejecutar.ps1     # Script de compilación
└── README.md                   # Este archivo
```

## Solución de Problemas

### Error: "JavaFX runtime components are missing"
- **Causa:** JavaFX no está configurado correctamente
- **Solución:** Verificar configuración de JavaFX en el IDE

### Error: "Cannot find symbol: javafx"
- **Causa:** JavaFX no está en el classpath
- **Solución:** Agregar JavaFX al classpath del proyecto

### Error: "Unsupported major.minor version"
- **Causa:** Versión de Java incompatible
- **Solución:** Usar Java 17 o superior

## Notas Importantes

- El proyecto usa datos en memoria (no base de datos)
- Las imágenes de perfil se cargan dinámicamente según el género del paciente
- El sistema de sesiones mantiene el estado del usuario logueado
- Los archivos FXML están configurados para ser portables entre sistemas

## Contacto

Para soporte técnico o reportar problemas, contactar al equipo de desarrollo.
