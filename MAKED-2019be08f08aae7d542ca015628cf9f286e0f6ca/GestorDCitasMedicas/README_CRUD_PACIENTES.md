# CRUD Completo para Rol de Pacientes

## Mejoras Implementadas

### 1. Funcionalidad de Cancelar Citas

#### Características:
- **Selección de Cita**: El paciente puede seleccionar la cita que desea cancelar desde una tabla con información clara
- **Información Visual**: Se muestra un panel con los detalles de la cita seleccionada (fecha, hora, médico, motivo)
- **Preservación del Historial**: Las citas canceladas se mantienen en el expediente del paciente con el estado "cancelada"
- **Motivo de Cancelación**: Se requiere ingresar un motivo mínimo de 10 caracteres
- **Confirmación**: Proceso de confirmación antes de proceder con la cancelación

#### Archivos Modificados:
- `CancelarCitaController.java`: Lógica mejorada de cancelación
- `CancelarCita.fxml`: Interfaz mejorada con panel de información
- `Consulta.java`: Nuevo método `cancelarCita()` que preserva el historial

### 2. Funcionalidad de Reprogramar Citas

#### Características:
- **Selección de Cita**: El paciente puede seleccionar la cita que desea reprogramar
- **Información Visual**: Panel que muestra los detalles de la cita actual
- **Nueva Fecha y Hora**: Selección de nueva fecha (próximos 30 días) y hora disponible
- **Verificación de Disponibilidad**: Se verifica que el médico esté disponible en la nueva fecha/hora
- **Motivo de Reprogramación**: Se requiere ingresar un motivo mínimo de 10 caracteres
- **Confirmación**: Proceso de confirmación antes de proceder con la reprogramación

#### Archivos Modificados:
- `ReprogramarCitaController.java`: Lógica completa de reprogramación
- `ReprogramarCita.fxml`: Interfaz mejorada con panel de información
- `Consulta.java`: Nuevo método `reprogramarCita()` con verificación de disponibilidad

### 3. Mejoras en la Selección de Citas

#### Características:
- **Filtros Mejorados**: Filtrado por fecha y médico con información clara
- **Información Detallada**: La tabla muestra nombres de médicos y especialidades
- **Panel de Información**: Muestra detalles completos de la cita seleccionada
- **Validaciones**: Solo muestra citas futuras y no canceladas

#### Archivos Modificados:
- `SeleccionarCitaController.java`: Lógica mejorada de selección y filtrado
- `seleccionarCita.fxml`: Interfaz mejorada

### 4. Mejoras en el Modelo de Datos

#### Nuevos Métodos en `Consulta.java`:
- `cancelarCita(int idConsulta, String motivoCancelacion)`: Cancela cita preservando historial
- `reprogramarCita(int idConsulta, LocalDate nuevaFecha, LocalTime nuevaHora, String motivo)`: Reprograma cita con validaciones
- `obtenerHistorialPorPaciente(int idPaciente)`: Obtiene todas las citas del paciente (incluyendo canceladas)
- `obtenerActivasPorPaciente(int idPaciente)`: Obtiene solo citas activas del paciente

## Flujo de Trabajo

### Cancelar Cita:
1. Paciente selecciona "Cancelar Cita" desde el menú
2. Se abre ventana de selección de citas
3. Paciente selecciona la cita a cancelar
4. Se muestra información de la cita seleccionada
5. Paciente ingresa motivo de cancelación
6. Se confirma la cancelación
7. La cita se marca como cancelada pero se mantiene en el historial

### Reprogramar Cita:
1. Paciente selecciona "Reprogramar Cita" desde el menú
2. Se abre ventana de selección de citas
3. Paciente selecciona la cita a reprogramar
4. Se muestra información de la cita actual
5. Paciente selecciona nueva fecha y hora
6. Paciente ingresa motivo de reprogramación
7. Se verifica disponibilidad del médico
8. Se confirma la reprogramación
9. La cita se actualiza con nueva fecha/hora

## Validaciones Implementadas

### Cancelar Cita:
- Cita debe estar seleccionada
- Motivo mínimo 10 caracteres
- Confirmación del usuario

### Reprogramar Cita:
- Cita debe estar seleccionada
- Nueva fecha y hora deben ser válidas
- Verificación de disponibilidad del médico
- Motivo mínimo 10 caracteres
- Confirmación del usuario

## Beneficios de las Mejoras

1. **Experiencia de Usuario Mejorada**: Interfaces más claras y informativas
2. **Integridad de Datos**: Las citas canceladas se preservan en el historial
3. **Validaciones Robustas**: Prevención de errores y datos inconsistentes
4. **Información Clara**: Los usuarios pueden ver exactamente qué cita están modificando
5. **Flujo Intuitivo**: Proceso paso a paso con confirmaciones

## Notas Técnicas

- Todas las operaciones se realizan en memoria (modelo de datos estático)
- Las validaciones incluyen verificación de disponibilidad de médicos
- Los motivos de cancelación y reprogramación se adjuntan al historial de la cita
- Las interfaces son responsivas y mantienen el diseño consistente
