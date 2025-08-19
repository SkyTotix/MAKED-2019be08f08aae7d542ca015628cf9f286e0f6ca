# Botón de Regreso - Ventana de Agendar Cita

## Descripción
Se ha agregado un **botón de regreso al menú principal** en la ventana de agendar cita (`agendarCitaAdm.fxml`) para facilitar la navegación y mantener la consistencia con las otras ventanas del sistema.

## Ubicación y Diseño

### **Ubicación:**
- **Posición:** Parte inferior izquierda de la ventana
- **Ubicación específica:** Debajo de los botones de acción principales
- **Alineación:** `CENTER_LEFT` para mantener consistencia con otras ventanas

### **Diseño Visual:**
- **Texto:** "← Volver al Menú Principal"
- **Color de fondo:** Azul (#3B6F89) - consistente con el tema
- **Color de texto:** Blanco
- **Fuente:** Negrita, tamaño 14px
- **Bordes:** Redondeados (5px)
- **Padding:** 10px vertical, 20px horizontal

## Implementación Técnica

### **FXML (Interfaz):**
```xml
<!-- Botón de regreso al menú principal -->
<HBox alignment="CENTER_LEFT" spacing="10">
   <Button fx:id="btnVolverMenu" text="← Volver al Menú Principal" 
           style="-fx-background-color: #3B6F89; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-padding: 10 20;" 
           onAction="#volverAlMenuPrincipal" />
</HBox>
```

### **Java (Controlador):**
```java
@FXML
private void volverAlMenuPrincipal() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
        Parent mainRoot = loader.load();
        
        Scene nuevaEscena = new Scene(mainRoot, 1080, 720);
        Stage currentStage = (Stage) btnAgendar.getScene().getWindow();
        currentStage.setScene(nuevaEscena);
        currentStage.setTitle("Panel Principal - Recepcionista");
        currentStage.centerOnScreen();
        
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo regresar al menú principal", Alert.AlertType.ERROR);
    }
}
```

## Funcionalidad

### **Navegación:**
- **Destino:** Panel Principal del Recepcionista (`mainRecepcionista.fxml`)
- **Método:** Reemplaza la escena actual con la nueva ventana
- **Dimensiones:** Mantiene las dimensiones estándar (1080x720)
- **Título:** Restaura el título original del panel principal

### **Manejo de Errores:**
- **Try-catch:** Captura errores de carga del FXML
- **Alerta de error:** Muestra mensaje si no se puede regresar
- **Logging:** Imprime el stack trace para debugging

## Ventajas de la Implementación

### ✅ **Consistencia de Navegación:**
- **Patrón uniforme:** Mismo comportamiento que otras ventanas
- **Ubicación estándar:** Posición consistente en todas las ventanas
- **Diseño coherente:** Mismo estilo visual que otros botones de regreso

### ✅ **Mejor Experiencia de Usuario:**
- **Navegación intuitiva:** Los usuarios saben dónde encontrar el botón
- **Acceso rápido:** Regreso directo al menú principal sin usar el menú lateral
- **Flexibilidad:** Opción adicional de navegación

### ✅ **Funcionalidad Completa:**
- **Manejo de errores:** Robustez ante fallos de carga
- **Feedback visual:** Mensajes claros en caso de error
- **Preservación de estado:** No afecta el estado de otras ventanas

## Casos de Uso

### **Usuario que termina de agendar:**
1. Completa el formulario de agendar cita
2. Hace clic en "Agendar Cita"
3. Ve el botón "← Volver al Menú Principal"
4. Hace clic para regresar al panel principal

### **Usuario que se arrepiente:**
1. Abre la ventana de agendar cita
2. Decide no agendar en ese momento
3. Hace clic en "← Volver al Menú Principal"
4. Regresa al panel principal sin cambios

### **Usuario que quiere navegar:**
1. Está en la ventana de agendar cita
2. Quiere ir a otra sección del sistema
3. Usa el botón de regreso para volver al menú principal
4. Desde ahí navega a la sección deseada

## Comparación con Otras Ventanas

### **Gestión de Usuarios:**
- ✅ Mismo diseño y ubicación
- ✅ Misma funcionalidad de navegación
- ✅ Mismo manejo de errores

### **Gestión de Médicos:**
- ✅ Mismo diseño y ubicación
- ✅ Misma funcionalidad de navegación
- ✅ Mismo manejo de errores

### **Consistencia General:**
- ✅ Todas las ventanas del recepcionista tienen el botón
- ✅ Mismo estilo visual en todas
- ✅ Mismo comportamiento de navegación

## Pruebas Recomendadas

### **1. Pruebas de Navegación:**
- Hacer clic en el botón desde diferentes estados del formulario
- Verificar que regrese correctamente al panel principal
- Comprobar que el título de la ventana se actualice

### **2. Pruebas de Estado:**
- Probar con formulario vacío
- Probar con formulario parcialmente lleno
- Probar con formulario completo
- Verificar que no se pierda información en otras ventanas

### **3. Pruebas de Error:**
- Simular error de carga del FXML
- Verificar que se muestre el mensaje de error
- Comprobar que la aplicación no se cierre

### **4. Pruebas de Integración:**
- Navegar desde agendar cita → menú principal → otras ventanas
- Verificar que la navegación sea fluida
- Comprobar que no haya conflictos entre ventanas

## Notas Técnicas

### **Dependencias:**
- **FXMLLoader:** Para cargar la nueva ventana
- **Scene:** Para crear la nueva escena
- **Stage:** Para actualizar la ventana actual
- **IOException:** Para manejo de errores

### **Rendimiento:**
- **Carga eficiente:** Solo carga cuando se hace clic
- **Memoria:** Libera recursos de la ventana anterior
- **Velocidad:** Navegación rápida entre ventanas

### **Mantenibilidad:**
- **Código limpio:** Método bien estructurado
- **Reutilizable:** Patrón que se puede aplicar a otras ventanas
- **Documentado:** Comentarios claros en el código

## Próximas Mejoras

### **Animación de Transición:**
- Efecto de fade entre ventanas
- Transición suave al cambiar de escena

### **Confirmación de Salida:**
- Preguntar si guardar cambios pendientes
- Prevenir pérdida de datos no guardados

### **Historial de Navegación:**
- Botón "Atrás" para regresar a la ventana anterior
- Navegación más flexible entre ventanas
