package com.gestorcitasmedicas.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id;
    private String nombre;
    private String curp;
    private String telefono;
    private String matricula;
    private String correo;
    private String contrasena;
    private String rol;
    private String tipo; // "Estudiante" o "Personal UTEZ"

    public Paciente() {
        this.rol = "paciente";
    }

    public Paciente(String nombre, String curp, String telefono, String matricula, 
                   String correo, String contrasena, String tipo) {
        this.nombre = nombre;
        this.curp = curp;
        this.telefono = telefono;
        this.matricula = matricula;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.rol = "paciente";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // Lista estática para almacenar pacientes en memoria
    private static List<Paciente> pacientes = new ArrayList<>();
    private static int nextId = 1;

    // Método para guardar paciente en memoria
    public boolean guardar() {
        try {
            this.id = nextId++;
            pacientes.add(this);
            System.out.println("Paciente guardado en memoria: " + this.nombre);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar paciente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si el correo ya existe
    public static boolean correoExiste(String correo) {
        return pacientes.stream().anyMatch(p -> p.getCorreo().equals(correo));
    }

    // Método para verificar si el CURP ya existe
    public static boolean curpExiste(String curp) {
        return pacientes.stream().anyMatch(p -> p.getCurp().equals(curp));
    }

    // Método para verificar si la matrícula ya existe
    public static boolean matriculaExiste(String matricula) {
        return pacientes.stream().anyMatch(p -> p.getMatricula().equals(matricula));
    }

    // Método para autenticar paciente
    public static Paciente autenticar(String correo, String contrasena) {
        return pacientes.stream()
                .filter(p -> p.getCorreo().equals(correo) && p.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }

    // Método para obtener todos los pacientes
    public static List<Paciente> obtenerTodos() {
        return new ArrayList<>(pacientes);
    }

    // Método para limpiar la lista (útil para testing)
    public static void limpiarLista() {
        pacientes.clear();
        nextId = 1;
    }
}
