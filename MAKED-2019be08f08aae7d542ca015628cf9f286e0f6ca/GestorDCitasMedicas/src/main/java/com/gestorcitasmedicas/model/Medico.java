package com.gestorcitasmedicas.model;

import java.util.ArrayList;
import java.util.List;

public class Medico {
    private int id;
    private String nombre;
    private String especialidad;
    private String cedula;
    private String correo;
    private String telefono;
    private String horario;
    private String consultorio;
    private String contrasena;
    private String rol;

    public Medico() {
        this.rol = "medico";
    }

    public Medico(String nombre, String especialidad, String cedula, String correo, 
                  String telefono, String horario, String consultorio, String contrasena) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.horario = horario;
        this.consultorio = consultorio;
        this.contrasena = contrasena;
        this.rol = "medico";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Lista estática para almacenar médicos en memoria
    private static List<Medico> medicos = new ArrayList<>();
    private static int nextId = 1;

    // Método para guardar médico en memoria
    public boolean guardar() {
        try {
            this.id = nextId++;
            medicos.add(this);
            System.out.println("Médico guardado en memoria: " + this.nombre);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar médico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si el correo ya existe
    public static boolean correoExiste(String correo) {
        return medicos.stream().anyMatch(m -> m.getCorreo().equals(correo));
    }

    // Método para verificar si la cédula ya existe
    public static boolean cedulaExiste(String cedula) {
        return medicos.stream().anyMatch(m -> m.getCedula().equals(cedula));
    }

    // Método para autenticar médico
    public static Medico autenticar(String correo, String contrasena) {
        return medicos.stream()
                .filter(m -> m.getCorreo().equals(correo) && m.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }

    // Método para obtener todos los médicos
    public static List<Medico> obtenerTodos() {
        return new ArrayList<>(medicos);
    }

    // Método para limpiar la lista (útil para testing)
    public static void limpiarLista() {
        medicos.clear();
        nextId = 1;
    }
}
