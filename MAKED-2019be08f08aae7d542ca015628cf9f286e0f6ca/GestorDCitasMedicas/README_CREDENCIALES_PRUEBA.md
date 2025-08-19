# Credenciales de Prueba - Gestor de Citas Médicas

## Usuarios de Prueba Disponibles

### Recepcionista
- **Correo**: `recepcionista@test.com`
- **Contraseña**: `recepcionista123`
- **Rol**: Recepcionista
- **Funcionalidades**: Gestión de usuarios, médicos, citas

### Médicos

#### Dr. Juan Pérez
- **Correo**: `doctor@test.com`
- **Contraseña**: `doctor123`
- **Especialidad**: Cardiología
- **Cédula**: 12345678
- **Consultorio**: 101
- **Horario**: Lunes a Viernes 8:00-16:00

#### Dra. María González
- **Correo**: `dermatologa@test.com`
- **Contraseña**: `dermatologa123`
- **Especialidad**: Dermatología
- **Cédula**: 87654321
- **Consultorio**: 102
- **Horario**: Lunes a Viernes 9:00-17:00

#### Dr. Carlos Rodríguez
- **Correo**: `pediatra@test.com`
- **Contraseña**: `pediatra123`
- **Especialidad**: Pediatría
- **Cédula**: 11223344
- **Consultorio**: 103
- **Horario**: Lunes a Viernes 7:00-15:00

### Pacientes

#### María González
- **Correo**: `paciente@test.com`
- **Contraseña**: `paciente123`
- **CURP**: GONM800101MDFXXX01
- **Matrícula**: A123456
- **Teléfono**: 5598765432

#### Juan López
- **Correo**: `juan.lopez@test.com`
- **Contraseña**: `juan123`
- **CURP**: LOPJ850515HDFXXX02
- **Matrícula**: B789012
- **Teléfono**: 5511223344

#### Ana Martínez
- **Correo**: `ana.martinez@test.com`
- **Contraseña**: `ana123`
- **CURP**: MARA900320MDFXXX03
- **Matrícula**: C345678
- **Teléfono**: 5566778899

## Consultas de Prueba Creadas

### Consultas Programadas
1. **María González** - Dr. Juan Pérez (Cardiología)
   - Fecha: Mañana
   - Hora: 9:00 AM
   - Motivo: Revisión cardiológica de rutina

2. **María González** - Dra. María González (Dermatología)
   - Fecha: En 3 días
   - Hora: 2:30 PM
   - Motivo: Consulta por problemas de piel

3. **Juan López** - Dr. Juan Pérez (Cardiología)
   - Fecha: En 2 días
   - Hora: 11:00 AM
   - Motivo: Evaluación cardíaca por dolor en el pecho

4. **Juan López** - Dr. Carlos Rodríguez (Pediatría)
   - Fecha: En 5 días
   - Hora: 10:00 AM
   - Motivo: Consulta pediátrica para su hijo

5. **Ana Martínez** - Dra. María González (Dermatología)
   - Fecha: Mañana
   - Hora: 4:00 PM
   - Motivo: Revisión dermatológica anual

### Consulta Completada
- **Ana Martínez** - Dr. Juan Pérez (Cardiología)
  - Fecha: Hace 2 días
  - Diagnóstico: Arritmia sinusal benigna
  - Tratamiento: Monitoreo cardíaco por 24 horas y evitar cafeína
  - Estado: Completada

### Consulta Cancelada
- **María González** - Dr. Carlos Rodríguez (Pediatría)
  - Fecha: Ayer
  - Estado: Cancelada

## Funcionalidades por Rol

### Recepcionista
- Gestión de pacientes
- Gestión de médicos
- Agendar citas
- Ver historial de citas
- Cancelar/reprogramar citas

### Médico
- Ver citas programadas
- Ver expedientes de pacientes
- Agregar diagnósticos y tratamientos
- Completar consultas
- Ver historial de consultas

### Paciente
- Ver citas programadas
- Ver historial de consultas
- Ver diagnósticos y tratamientos
- Solicitar nuevas citas

## Notas Importantes

- **Datos en Memoria**: Todos los datos se almacenan en memoria durante la ejecución de la aplicación
- **Persistencia**: Los datos se pierden al cerrar la aplicación
- **Inicialización Automática**: Los datos de prueba se cargan automáticamente al iniciar la aplicación
- **Validaciones**: Se mantienen todas las validaciones de formato y duplicados
- **Autenticación**: Funciona con las credenciales reales de los usuarios de prueba

## Cómo Usar

1. **Iniciar la aplicación**
2. **Usar cualquiera de las credenciales de prueba** para iniciar sesión
3. **Explorar las funcionalidades** según el rol del usuario
4. **Crear nuevos registros** que se agregarán a los datos en memoria
5. **Ver las consultas vinculadas** a cada usuario

## Datos de Prueba Incluidos

- **3 Médicos** con diferentes especialidades
- **3 Pacientes** con datos completos
- **8 Consultas** en diferentes estados (programadas, completadas, canceladas)
- **Diagnósticos y tratamientos** de ejemplo
- **Horarios y consultorios** asignados
