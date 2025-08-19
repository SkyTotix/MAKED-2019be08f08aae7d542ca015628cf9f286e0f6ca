# Botones de Regreso - Recuperación de Contraseña

## Descripción
Se han implementado **dos botones de regreso** en la ventana de recuperación de contraseña para facilitar la navegación al inicio de sesión.

## Botones Implementados

### 1. **Botón Superior (Esquina Superior Izquierda)**
- **Ubicación:** Parte superior izquierda de la ventana
- **Texto:** "← Regresar"
- **Estilo:** Azul (#3B6F89) con texto blanco
- **Función:** Regresa al inicio de sesión

### 2. **Botón Inferior (Parte Inferior)**
- **Ubicación:** Parte inferior de la ventana, debajo de los enlaces
- **Texto:** "Volver al Inicio de Sesión"
- **Estilo:** Gris (#6C757D) con texto blanco
- **Función:** Regresa al inicio de sesión

## Funcionalidad

### **Navegación:**
- Ambos botones ejecutan el método `regresarALogin()`
- Cierran la ventana actual de recuperación de contraseña
- Abren la ventana de inicio de sesión (`login.fxml`)
- Mantienen el tamaño y posición de la ventana

### **Código del Método:**
```java
@FXML
private void regresarALogin() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
        Parent loginRoot = loader.load();
        
        Scene nuevaEscena = new Scene(loginRoot, 759, 422);
        Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
        currentStage.setScene(nuevaEscena);
        currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
        currentStage.centerOnScreen();
        
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo regresar al login", Alert.AlertType.ERROR);
    }
}
```

## Estructura en FXML

### **Botón Superior:**
```xml
<HBox alignment="CENTER_LEFT" spacing="10">
   <Button fx:id="btnRegresar" text="← Regresar" 
           style="-fx-background-color: #3B6F89; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-padding: 8 15;" 
           onAction="#regresarALogin" />
</HBox>
```

### **Botón Inferior:**
```xml
<Button fx:id="btnRegresarInferior" text="Volver al Inicio de Sesión" 
        style="-fx-background-color: #6C757D; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-padding: 10 20;" 
        onAction="#regresarALogin" />
```

## Ventajas de la Implementación

### ✅ **Doble Acceso:**
- **Botón superior:** Acceso rápido para usuarios que quieren regresar inmediatamente
- **Botón inferior:** Acceso más visible después de completar el formulario

### ✅ **Diseño Intuitivo:**
- **Flecha (←):** Indica claramente la acción de regresar
- **Texto descriptivo:** "Volver al Inicio de Sesión" es claro y específico

### ✅ **Consistencia Visual:**
- **Colores:** Azul para el botón principal, gris para el secundario
- **Estilos:** Bordes redondeados y padding consistente
- **Tipografía:** Fuente en negrita para mejor visibilidad

## Casos de Uso

### **Usuario que se arrepiente:**
1. Abre la ventana de recuperación
2. Ve el botón "← Regresar" en la esquina superior
3. Hace clic y regresa al login

### **Usuario que completa el proceso:**
1. Llena el formulario de recuperación
2. Ve el botón "Volver al Inicio de Sesión" al final
3. Hace clic y regresa al login

### **Usuario que quiere crear cuenta:**
1. Ve el enlace "¡Crea uno ahora!" en la parte inferior
2. Hace clic para acceder a la función de registro (en desarrollo)

## Pruebas Recomendadas

1. **Probar ambos botones:** Verificar que ambos llevan al login
2. **Verificar navegación:** Confirmar que la ventana se cierra correctamente
3. **Probar desde diferentes estados:** Con formulario vacío, parcialmente lleno, y completo
4. **Verificar manejo de errores:** Probar con archivos FXML corruptos

## Notas Técnicas

- **Eventos FXML:** Los botones usan `onAction="#regresarALogin"` en lugar de configuración programática
- **Manejo de errores:** Incluye try-catch para manejar errores de carga
- **Dimensiones:** Mantiene las dimensiones originales del login (759x422)
- **Título:** Restaura el título original de la ventana de login
