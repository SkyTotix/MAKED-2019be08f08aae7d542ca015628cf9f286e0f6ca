# Sistema de Persistencia de Datos - Gestor de Citas Médicas

## 📁 Descripción General

El proyecto ahora incluye un **sistema de persistencia de datos completamente portable** que permite que los datos se mantengan entre sesiones y se puedan compartir entre diferentes computadoras.

## 🔧 Características Principales

### ✅ **Portabilidad Completa**
- **Sin configuración de base de datos** requerida
- **Funciona en cualquier computadora** con Java 17+
- **Archivos JSON** fáciles de leer y compartir
- **Compatible con GitHub** (archivos de datos excluidos del repositorio)

### ✅ **Persistencia Automática**
- **Guardado automático** al cerrar la aplicación
- **Carga automática** al iniciar la aplicación
- **Datos de prueba** incluidos para desarrollo
- **Sin pérdida de datos** entre sesiones

### ✅ **Seguridad y Privacidad**
- **Archivos de datos excluidos** del repositorio Git
- **Datos personales protegidos** de subida accidental
- **Control total** sobre qué datos compartir

## 📂 Estructura de Archivos

```
GestorDCitasMedicas/
├── data/                    # 📁 Directorio de datos (creado automáticamente)
│   ├── pacientes.json      # 👥 Información de pacientes
│   ├── medicos.json        # 👨‍⚕️ Información de médicos
│   └── consultas.json      # 📅 Información de citas médicas
├── lib/                    # 📚 Librerías (incluye Gson para JSON)
├── src/                    # 💻 Código fuente
└── .gitignore             # 🚫 Excluye archivos de datos del repositorio
```

## 🔄 Funcionamiento del Sistema

### **Primera Ejecución:**
1. La aplicación inicia sin archivos de datos
2. Se cargan automáticamente los **datos de prueba**
3. Se crea el directorio `data/`
4. Se guardan los datos de prueba en archivos JSON
5. Los datos quedan disponibles para futuras sesiones

### **Ejecuciones Posteriores:**
1. La aplicación busca archivos JSON en `data/`
2. Si encuentra archivos, **carga los datos guardados**
3. Si no encuentra archivos, usa datos de prueba
4. Al cerrar, **guarda automáticamente** cualquier cambio

### **Modificaciones de Datos:**
1. Cualquier cambio (nuevos usuarios, citas, etc.) se mantiene en memoria
2. Al cerrar la aplicación, **todos los cambios se guardan automáticamente**
3. En la próxima ejecución, los cambios estarán disponibles

## 🚀 Cómo Usar la Portabilidad

### **Para Compartir el Proyecto:**
1. **Subir a GitHub:** Solo el código (los archivos `data/` están excluidos)
2. **Otros desarrolladores:** Clonan el repositorio
3. **Primera ejecución:** Se crean automáticamente los datos de prueba
4. **Desarrollo:** Cada desarrollador tiene sus propios datos

### **Para Compartir Datos Específicos:**
1. **Copiar archivos:** Los archivos JSON de `data/` se pueden copiar manualmente
2. **Pegar en otra computadora:** En el directorio `data/` del proyecto
3. **Ejecutar aplicación:** Los datos se cargarán automáticamente

### **Para Backup de Datos:**
1. **Hacer copia:** Del directorio `data/` completo
2. **Guardar en lugar seguro:** USB, nube, etc.
3. **Restaurar:** Copiar de vuelta al directorio del proyecto

## 📋 Archivos JSON Generados

### **pacientes.json:**
```json
[
  {
    "id": 1,
    "nombre": "María González",
    "curp": "GONM800101MDFXXX01",
    "telefono": "5598765432",
    "matricula": "A123456",
    "correo": "paciente@test.com",
    "contrasena": "paciente123",
    "rol": "paciente",
    "tipo": "Estudiante"
  }
]
```

### **medicos.json:**
```json
[
  {
    "id": 1,
    "nombre": "Dr. Juan Pérez",
    "especialidad": "Cardiología",
    "cedula": "12345678",
    "correo": "doctor@test.com",
    "telefono": "5512345678",
    "horario": "Lunes a Viernes 8:00-16:00",
    "consultorio": "Consultorio 101",
    "contrasena": "doctor123",
    "rol": "medico"
  }
]
```

### **consultas.json:**
```json
[
  {
    "id": 1,
    "idPaciente": 1,
    "idMedico": 1,
    "fecha": "2025-08-20",
    "hora": "09:00:00",
    "motivo": "Revisión cardiológica de rutina",
    "estado": "programada",
    "diagnostico": null,
    "tratamiento": null,
    "observaciones": null
  }
]
```

## 🔧 Configuración Técnica

### **Dependencias Agregadas:**
- **Gson 2.10.1:** Para serialización/deserialización JSON
- **Adaptadores personalizados:** Para `LocalDate` y `LocalTime`

### **Clases Principales:**
- **`DataPersistence.java`:** Manejo de archivos JSON
- **`LocalDateAdapter.java`:** Serialización de fechas
- **`LocalTimeAdapter.java`:** Serialización de horas
- **`AutoSaveManager.java`:** Guardado automático
- **`DatosPrueba.java`:** Actualizado para usar persistencia

### **Archivos Modificados:**
- **`pom.xml`:** Agregada dependencia Gson
- **`compilar-y-ejecutar.ps1`:** Incluye librería Gson en classpath
- **`.gitignore`:** Excluye archivos de datos
- **`README.md`:** Documentación actualizada

## 🎯 Ventajas del Sistema

### **Para Desarrolladores:**
- ✅ **Sin configuración de BD** requerida
- ✅ **Desarrollo rápido** y simple
- ✅ **Datos de prueba** incluidos automáticamente
- ✅ **Portabilidad completa** entre equipos

### **Para Usuarios Finales:**
- ✅ **Datos persistentes** entre sesiones
- ✅ **Sin pérdida de información**
- ✅ **Fácil backup** de datos
- ✅ **Compartir datos** entre computadoras

### **Para el Proyecto:**
- ✅ **GitHub seguro** (sin datos personales)
- ✅ **Colaboración fácil** entre desarrolladores
- ✅ **Deployment simple** sin dependencias externas
- ✅ **Escalabilidad** para futuras mejoras

## 🔮 Próximos Pasos

### **Mejoras Futuras:**
1. **Encriptación:** Proteger datos sensibles
2. **Compresión:** Reducir tamaño de archivos
3. **Validación:** Verificar integridad de datos
4. **Sincronización:** Sincronizar entre dispositivos
5. **Base de datos:** Migración opcional a BD real

### **Mantenimiento:**
- Los archivos JSON se mantienen automáticamente
- No requiere intervención manual
- Compatible con futuras versiones del proyecto

---

**¡El sistema de persistencia está completamente funcional y listo para uso en producción!** 🎉
