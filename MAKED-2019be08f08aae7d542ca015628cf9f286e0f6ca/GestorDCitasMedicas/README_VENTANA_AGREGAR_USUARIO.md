# Ventana de Agregar Usuario - Implementación

## Descripción
Se ha creado una nueva ventana **"Agregar Usuario"** basada en la ventana de registro pero adaptada específicamente para el contexto de gestión de usuarios del sistema. Esta ventana permite a los administradores agregar nuevos usuarios al sistema con diferentes tipos de roles.

## Características Principales

### **🎯 Funcionalidad:**
- **Formulario completo:** Todos los campos necesarios para registrar un usuario
- **Validaciones robustas:** Verificación de formatos y campos obligatorios
- **Tipos de usuario:** Selección entre Paciente, Recepcionista y Médico
- **Navegación integrada:** Menú lateral expandible y botón de regreso
- **Diseño responsive:** Se adapta al tamaño de la ventana

### **📋 Campos del Formulario:**

#### **Información Personal:**
- **Nombre:** Campo obligatorio para el nombre del usuario
- **Apellidos:** Campo obligatorio para los apellidos
- **Matrícula/No. empleado:** Identificador único del usuario
- **CURP:** Clave Única de Registro de Población (formato validado)

#### **Información de Contacto:**
- **Correo:** Email del usuario (formato validado)
- **Teléfono:** Número de contacto (10 dígitos)

#### **Seguridad:**
- **Contraseña:** Mínimo 6 caracteres
- **Confirmar contraseña:** Debe coincidir con la contraseña

#### **Clasificación:**
- **Tipo de Usuario:** ComboBox con opciones:
  - Paciente
  - Recepcionista
  - Médico

## Implementación Técnica

### **Archivos Creados:**

#### **1. Interfaz FXML (`agregarUsuario.fxml`):**
- **Layout:** BorderPane con menú lateral y área central
- **Diseño:** Formulario organizado en filas de 2 columnas
- **ScrollPane:** Para contenido extenso
- **Menú lateral:** Expandible con navegación completa

#### **2. Controlador Java (`AgregarUsuarioController.java`):**
- **Validaciones:** Completas para todos los campos
- **Navegación:** Integrada con el sistema
- **Manejo de errores:** Alertas informativas
- **Menú expandible:** Mismo comportamiento que otras ventanas

### **Estructura del Formulario:**
```xml
<!-- Primera fila: Nombre y Apellidos -->
<HBox spacing="20" alignment="CENTER_LEFT">
   <VBox spacing="5" prefWidth="300">
      <Label text="Nombre:" />
      <TextField fx:id="nombreField" />
   </VBox>
   <VBox spacing="5" prefWidth="300">
      <Label text="Apellidos:" />
      <TextField fx:id="apellidosField" />
   </VBox>
</HBox>

<!-- Segunda fila: Matrícula y CURP -->
<HBox spacing="20" alignment="CENTER_LEFT">
   <VBox spacing="5" prefWidth="300">
      <Label text="Matrícula o No. empleado:" />
      <TextField fx:id="matriculaField" />
   </VBox>
   <VBox spacing="5" prefWidth="300">
      <Label text="CURP:" />
      <TextField fx:id="curpField" />
   </VBox>
</HBox>

<!-- Y así sucesivamente... -->
```

## Validaciones Implementadas

### **✅ Campos Obligatorios:**
- Todos los campos deben estar llenos
- Mensajes específicos para cada campo vacío

### **✅ Formato de Correo:**
```java
String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
if (!Pattern.matches(emailPattern, correoField.getText().trim())) {
    mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
    return false;
}
```

### **✅ Formato de CURP:**
```java
String curpPattern = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$";
if (!Pattern.matches(curpPattern, curpField.getText().trim().toUpperCase())) {
    mostrarAlerta("Error", "El formato del CURP no es válido", Alert.AlertType.ERROR);
    return false;
}
```

### **✅ Formato de Teléfono:**
```java
String telefonoPattern = "^[0-9]{10}$";
if (!Pattern.matches(telefonoPattern, telefonoField.getText().trim().replaceAll("[^0-9]", ""))) {
    mostrarAlerta("Error", "El formato del teléfono no es válido (debe tener 10 dígitos)", Alert.AlertType.ERROR);
    return false;
}
```

### **✅ Contraseña:**
- Mínimo 6 caracteres
- Debe coincidir con la confirmación

### **✅ Tipo de Usuario:**
- Debe seleccionar una opción del ComboBox

## Navegación Integrada

### **Menú Lateral:**
- **Gestión Usuarios:** Resaltado (ya estamos en esa sección)
- **Gestión Médicos:** Navega a `gestMedicos.fxml`
- **Gestión Citas:** Navega a `agendarCitaAdm.fxml`
- **Salir:** Regresa al login

### **Botones de Acción:**
- **Agregar Usuario:** Valida y guarda el usuario
- **Cancelar:** Limpia el formulario
- **← Volver al Menú Principal:** Regresa al panel principal

## Diferencias con la Ventana de Registro

### **🎨 Diseño:**
- **Header específico:** "Agregar Nuevo Usuario"
- **Menú lateral:** Integrado para navegación del sistema
- **Botón de regreso:** Al menú principal
- **Tipo de usuario:** Campo adicional para clasificación

### **🔧 Funcionalidad:**
- **Contexto administrativo:** Para uso de recepcionistas/administradores
- **Validaciones más estrictas:** CURP y teléfono con formatos específicos
- **Navegación completa:** Acceso a todas las secciones del sistema
- **Integración:** Con el sistema de gestión de usuarios

### **📱 Responsive:**
- **ScrollPane:** Para contenido extenso
- **Dimensiones:** 900x600 (más grande que el registro)
- **Adaptable:** Se ajusta al contenido

## Flujo de Uso

### **1. Acceso:**
- Desde "Gestión de Usuarios" → Botón "Agregar Usuario"
- Se abre la ventana `agregarUsuario.fxml`

### **2. Llenado del Formulario:**
- Usuario llena todos los campos requeridos
- Sistema valida formatos en tiempo real

### **3. Validación:**
- Al hacer clic en "Agregar Usuario"
- Sistema verifica todos los campos
- Muestra errores específicos si hay problemas

### **4. Guardado:**
- Si todo está correcto, guarda el usuario
- Muestra mensaje de éxito
- Limpia el formulario para nuevo registro

### **5. Navegación:**
- Puede regresar al menú principal
- Puede navegar a otras secciones
- Puede agregar más usuarios

## Ventajas de la Implementación

### ✅ **Consistencia:**
- Mismo diseño que otras ventanas del sistema
- Mismos patrones de navegación
- Mismos estilos visuales

### ✅ **Funcionalidad Completa:**
- Validaciones robustas
- Manejo de errores
- Navegación fluida

### ✅ **Experiencia de Usuario:**
- Interfaz intuitiva
- Feedback claro
- Navegación fácil

### ✅ **Mantenibilidad:**
- Código bien estructurado
- Validaciones reutilizables
- Fácil de extender

## Pruebas Recomendadas

### **1. Validaciones:**
- Probar todos los campos vacíos
- Probar formatos incorrectos
- Probar contraseñas que no coinciden

### **2. Navegación:**
- Probar menú lateral
- Probar botón de regreso
- Probar navegación entre ventanas

### **3. Funcionalidad:**
- Probar agregar usuario exitoso
- Probar cancelar operación
- Probar limpiar formulario

### **4. Responsive:**
- Cambiar tamaño de ventana
- Probar con diferentes resoluciones
- Verificar scroll cuando sea necesario

## Próximas Mejoras

### **🔮 Funcionalidades Futuras:**
- **Conexión a BD:** Integrar con base de datos real
- **Validación CURP:** Verificar contra base de datos
- **Carga de imagen:** Foto del usuario
- **Historial:** Ver usuarios agregados recientemente

### **🎨 Mejoras de UI:**
- **Autocompletado:** Para campos comunes
- **Guardado automático:** Borrador temporal
- **Vista previa:** Del usuario antes de guardar

### **⚡ Optimizaciones:**
- **Validación en tiempo real:** Mientras el usuario escribe
- **Guardado rápido:** Con atajos de teclado
- **Plantillas:** Para tipos de usuario comunes
