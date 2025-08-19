package com.gestorcitasmedicas.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private int id;
    private int idPaciente;
    private int idMedico;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado; // programada, completada, cancelada
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public Consulta() {
        this.estado = "programada";
    }

    public Consulta(int idPaciente, int idMedico, LocalDate fecha, LocalTime hora, String motivo) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = "programada";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // Lista estática para almacenar consultas en memoria
    private static List<Consulta> consultas = new ArrayList<>();
    private static int nextId = 1;

    // Método para guardar consulta en memoria
    public boolean guardar() {
        try {
            this.id = nextId++;
            consultas.add(this);
            System.out.println("Consulta guardada en memoria: ID=" + this.id + 
                             ", Paciente=" + this.idPaciente + 
                             ", Médico=" + this.idMedico + 
                             ", Fecha=" + this.fecha);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar consulta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener consultas por paciente
    public static List<Consulta> obtenerPorPaciente(int idPaciente) {
        return consultas.stream()
                .filter(c -> c.getIdPaciente() == idPaciente)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Método para obtener consultas por médico
    public static List<Consulta> obtenerPorMedico(int idMedico) {
        return consultas.stream()
                .filter(c -> c.getIdMedico() == idMedico)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Método para obtener consultas por fecha
    public static List<Consulta> obtenerPorFecha(LocalDate fecha) {
        return consultas.stream()
                .filter(c -> c.getFecha().equals(fecha))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Método para obtener consultas por estado
    public static List<Consulta> obtenerPorEstado(String estado) {
        return consultas.stream()
                .filter(c -> c.getEstado().equals(estado))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Método para obtener todas las consultas
    public static List<Consulta> obtenerTodas() {
        return new ArrayList<>(consultas);
    }

    // Método para actualizar estado de consulta
    public static boolean actualizarEstado(int idConsulta, String nuevoEstado) {
        return consultas.stream()
                .filter(c -> c.getId() == idConsulta)
                .findFirst()
                .map(c -> {
                    c.setEstado(nuevoEstado);
                    return true;
                })
                .orElse(false);
    }

    // Método para agregar diagnóstico y tratamiento
    public static boolean agregarDiagnostico(int idConsulta, String diagnostico, String tratamiento, String observaciones) {
        return consultas.stream()
                .filter(c -> c.getId() == idConsulta)
                .findFirst()
                .map(c -> {
                    c.setDiagnostico(diagnostico);
                    c.setTratamiento(tratamiento);
                    c.setObservaciones(observaciones);
                    c.setEstado("completada");
                    return true;
                })
                .orElse(false);
    }

    // Método para verificar disponibilidad de horario
    public static boolean verificarDisponibilidad(int idMedico, LocalDate fecha, LocalTime hora) {
        return consultas.stream()
                .noneMatch(c -> c.getIdMedico() == idMedico && 
                               c.getFecha().equals(fecha) && 
                               c.getHora().equals(hora) &&
                               !c.getEstado().equals("cancelada"));
    }

    // Método para limpiar la lista (útil para testing)
    public static void limpiarLista() {
        consultas.clear();
        nextId = 1;
    }

    // Método para obtener información completa de la consulta
    public String getInfoCompleta() {
        Paciente paciente = Paciente.obtenerTodos().stream()
                .filter(p -> p.getId() == this.idPaciente)
                .findFirst()
                .orElse(null);
        
        Medico medico = Medico.obtenerTodos().stream()
                .filter(m -> m.getId() == this.idMedico)
                .findFirst()
                .orElse(null);

        return String.format("Consulta #%d\n" +
                           "Paciente: %s\n" +
                           "Médico: %s\n" +
                           "Fecha: %s\n" +
                           "Hora: %s\n" +
                           "Motivo: %s\n" +
                           "Estado: %s",
                           this.id,
                           paciente != null ? paciente.getNombre() : "N/A",
                           medico != null ? medico.getNombre() : "N/A",
                           this.fecha,
                           this.hora,
                           this.motivo,
                           this.estado);
    }
}
