# Funcionalidad de Recuperación de Contraseña

## Descripción
Se ha implementado la funcionalidad de recuperación de contraseña que permite a los usuarios actualizar su contraseña ingresando su CURP.

## Características Implementadas

### ✅ **Validaciones:**
- **CURP:** Validación de formato (18 caracteres alfanuméricos)
- **Contraseña:** Mínimo 6 caracteres
- **Confirmación:** Las contraseñas deben coincidir
- **Base de Datos:** Verificación de que el CURP existe en la BD

### ✅ **Interfaz de Usuario:**
- Diseño dividido: imagen médica a la izquierda, formulario a la derecha
- Campos para CURP, contraseña y confirmación
- Botón de regreso al login
- Enlaces para crear cuenta

### ✅ **Navegación:**
- Enlace desde el login: "¿Has olvidado tu contraseña?"
- Regreso automático al login después de actualizar contraseña

## Cómo Probar la Funcionalidad

### 1. **Acceder a la Recuperación:**
1. Ejecutar la aplicación
2. En la pantalla de login, hacer clic en "¿Has olvidado tu contraseña?"

### 2. **CURPs Válidos para Prueba:**
```
ABCD123456HMCDEF01
EFGH789012HMGHIJ02
IJKL345678HMKLMN03
MNOP901234HMNOPQ04
QRST567890HMQRST05
```

### 3. **Casos de Prueba:**

#### ✅ **Caso Exitoso:**
- CURP: `ABCD123456HMCDEF01`
- Contraseña: `nueva123`
- Confirmar: `nueva123`
- **Resultado:** Contraseña actualizada correctamente

#### ❌ **Casos de Error:**

**CURP Inválido:**
- CURP: `INVALID123456789`
- **Resultado:** "El CURP ingresado no está registrado en la base de datos"

**Formato CURP Incorrecto:**
- CURP: `123456789`
- **Resultado:** "El formato del CURP no es válido"

**Contraseña Corta:**
- Contraseña: `123`
- **Resultado:** "La contraseña debe tener al menos 6 caracteres"

**Contraseñas No Coinciden:**
- Contraseña: `nueva123`
- Confirmar: `diferente456`
- **Resultado:** "Las contraseñas no coinciden"

## Estructura de Archivos

```
src/main/resources/com/gestorcitasmedicas/
├── recuperarContra.fxml          # Interfaz de recuperación
└── img/
    ├── docBack.png               # Imagen de fondo médico
    ├── cross.png                 # Icono de medicina
    └── icon1.png                 # Icono de flecha

src/main/java/com/gestorcitasmedicas/
├── controller/
│   ├── RecuperarContraController.java    # Controlador de recuperación
│   └── LoginController.java              # Controlador de login (actualizado)
└── util/
    └── DatabaseConnection.java           # Clase de conexión BD
```

## Preparación para Base de Datos Real

### **Tabla de Usuarios:**
```sql
CREATE TABLE usuarios (
    id NUMBER PRIMARY KEY,
    curp VARCHAR2(18) UNIQUE NOT NULL,
    contrasena VARCHAR2(255) NOT NULL,
    email VARCHAR2(100),
    nombre VARCHAR2(100),
    fecha_registro DATE DEFAULT SYSDATE
);
```

### **Código de Conexión Real:**
El controlador incluye código comentado para la implementación real con base de datos Oracle.

## Notas Técnicas

- **Validación CURP:** Usa regex para validar formato oficial mexicano
- **Seguridad:** En producción, las contraseñas deben hashearse
- **Conexión BD:** Preparada para Oracle Database
- **Manejo de Errores:** Alertas informativas para el usuario

## Próximos Pasos

1. **Implementar Base de Datos Real**
2. **Agregar Hash de Contraseñas**
3. **Implementar Función de Crear Cuenta**
4. **Agregar Validación de Email**
5. **Implementar Envío de Confirmación por Email**
