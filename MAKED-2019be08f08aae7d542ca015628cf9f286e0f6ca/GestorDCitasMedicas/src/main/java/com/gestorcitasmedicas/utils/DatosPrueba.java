package com.gestorcitasmedicas.utils;

import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.model.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

public class DatosPrueba {
    
    public static void inicializarDatosPrueba() {
        System.out.println("Inicializando datos de prueba...");
        
        // Limpiar datos existentes
        Medico.limpiarLista();
        Paciente.limpiarLista();
        Consulta.limpiarLista();
        
        // Crear médicos de prueba
        Medico medico1 = new Medico(
            "Dr. Juan Pérez",
            "Cardiología",
            "12345678",
            "doctor@test.com",
            "5512345678",
            "Lunes a Viernes 8:00-16:00",
            "Consultorio 101",
            "doctor123"
        );
        medico1.guardar();
        
        Medico medico2 = new Medico(
            "Dra. María González",
            "Dermatología",
            "87654321",
            "dermatologa@test.com",
            "5598765432",
            "Lunes a Viernes 9:00-17:00",
            "Consultorio 102",
            "dermatologa123"
        );
        medico2.guardar();
        
        Medico medico3 = new Medico(
            "Dr. Carlos Rodríguez",
            "Pediatría",
            "11223344",
            "pediatra@test.com",
            "5544332211",
            "Lunes a Viernes 7:00-15:00",
            "Consultorio 103",
            "pediatra123"
        );
        medico3.guardar();
        
        // Crear pacientes de prueba
        Paciente paciente1 = new Paciente(
            "María González",
            "GONM800101MDFXXX01",
            "5598765432",
            "A123456",
            "paciente@test.com",
            "paciente123",
            "Estudiante"
        );
        paciente1.guardar();
        
        Paciente paciente2 = new Paciente(
            "Juan López",
            "LOPJ850515HDFXXX02",
            "5511223344",
            "B789012",
            "juan.lopez@test.com",
            "juan123",
            "Personal UTEZ"
        );
        paciente2.guardar();
        
        Paciente paciente3 = new Paciente(
            "Ana Martínez",
            "MARA900320MDFXXX03",
            "5566778899",
            "C345678",
            "ana.martinez@test.com",
            "ana123",
            "Estudiante"
        );
        paciente3.guardar();
        
        // Crear consultas de prueba vinculadas a los usuarios
        crearConsultasPrueba();
        
        System.out.println("Datos de prueba inicializados correctamente.");
        System.out.println("Médicos creados: " + Medico.obtenerTodos().size());
        System.out.println("Pacientes creados: " + Paciente.obtenerTodos().size());
        System.out.println("Consultas creadas: " + Consulta.obtenerTodas().size());
    }
    
    private static void crearConsultasPrueba() {
        // Obtener IDs de médicos y pacientes
        Medico medico1 = Medico.obtenerTodos().get(0); // Dr. Juan Pérez
        Medico medico2 = Medico.obtenerTodos().get(1); // Dra. María González
        Medico medico3 = Medico.obtenerTodos().get(2); // Dr. Carlos Rodríguez
        
        Paciente paciente1 = Paciente.obtenerTodos().get(0); // María González
        Paciente paciente2 = Paciente.obtenerTodos().get(1); // Juan López
        Paciente paciente3 = Paciente.obtenerTodos().get(2); // Ana Martínez
        
        // Consultas para María González (paciente1)
        Consulta consulta1 = new Consulta(
            paciente1.getId(),
            medico1.getId(),
            LocalDate.now().plusDays(1),
            LocalTime.of(9, 0),
            "Revisión cardiológica de rutina"
        );
        consulta1.guardar();
        
        Consulta consulta2 = new Consulta(
            paciente1.getId(),
            medico2.getId(),
            LocalDate.now().plusDays(3),
            LocalTime.of(14, 30),
            "Consulta por problemas de piel"
        );
        consulta2.guardar();
        
        // Consultas para Juan López (paciente2)
        Consulta consulta3 = new Consulta(
            paciente2.getId(),
            medico1.getId(),
            LocalDate.now().plusDays(2),
            LocalTime.of(11, 0),
            "Evaluación cardíaca por dolor en el pecho"
        );
        consulta3.guardar();
        
        Consulta consulta4 = new Consulta(
            paciente2.getId(),
            medico3.getId(),
            LocalDate.now().plusDays(5),
            LocalTime.of(10, 0),
            "Consulta pediátrica para su hijo"
        );
        consulta4.guardar();
        
        // Consultas para Ana Martínez (paciente3)
        Consulta consulta5 = new Consulta(
            paciente3.getId(),
            medico2.getId(),
            LocalDate.now().plusDays(1),
            LocalTime.of(16, 0),
            "Revisión dermatológica anual"
        );
        consulta5.guardar();
        
        // Consulta completada con diagnóstico
        Consulta consultaCompletada = new Consulta(
            paciente3.getId(),
            medico1.getId(),
            LocalDate.now().minusDays(2),
            LocalTime.of(8, 30),
            "Consulta por arritmia"
        );
        consultaCompletada.guardar();
        
        // Agregar diagnóstico a la consulta completada
        Consulta.agregarDiagnostico(
            consultaCompletada.getId(),
            "Arritmia sinusal benigna",
            "Monitoreo cardíaco por 24 horas y evitar cafeína",
            "Paciente presenta arritmia sinusal que no requiere tratamiento inmediato"
        );
        
        // Consulta cancelada
        Consulta consultaCancelada = new Consulta(
            paciente1.getId(),
            medico3.getId(),
            LocalDate.now().minusDays(1),
            LocalTime.of(13, 0),
            "Consulta pediátrica"
        );
        consultaCancelada.guardar();
        Consulta.actualizarEstado(consultaCancelada.getId(), "cancelada");
    }
    
    // Método para obtener información de los datos de prueba
    public static String obtenerInfoDatosPrueba() {
        StringBuilder info = new StringBuilder();
        info.append("=== DATOS DE PRUEBA ===\n\n");
        
        info.append("MÉDICOS:\n");
        for (Medico medico : Medico.obtenerTodos()) {
            info.append(String.format("- %s (%s) - %s\n", 
                medico.getNombre(), medico.getEspecialidad(), medico.getCorreo()));
        }
        
        info.append("\nPACIENTES:\n");
        for (Paciente paciente : Paciente.obtenerTodos()) {
            info.append(String.format("- %s - %s\n", 
                paciente.getNombre(), paciente.getCorreo()));
        }
        
        info.append("\nCONSULTAS:\n");
        for (Consulta consulta : Consulta.obtenerTodas()) {
            info.append(String.format("- Consulta #%d: %s - %s\n", 
                consulta.getId(), consulta.getFecha(), consulta.getEstado()));
        }
        
        return info.toString();
    }
    
    // Método para verificar si los datos de prueba están cargados
    public static boolean datosCargados() {
        return !Medico.obtenerTodos().isEmpty() && 
               !Paciente.obtenerTodos().isEmpty() && 
               !Consulta.obtenerTodas().isEmpty();
    }
}
