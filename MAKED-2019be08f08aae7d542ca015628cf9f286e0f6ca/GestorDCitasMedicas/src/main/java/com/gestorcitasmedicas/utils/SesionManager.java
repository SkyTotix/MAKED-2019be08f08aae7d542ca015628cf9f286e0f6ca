package com.gestorcitasmedicas.utils;

import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.model.Medico;

/**
 * Clase para gestionar la sesión del usuario actual
 */
public class SesionManager {
    private static SesionManager instance;
    private Paciente pacienteActual;
    private Medico medicoActual;
    private String rolActual;
    
    private SesionManager() {
        // Constructor privado para singleton
    }
    
    public static SesionManager getInstance() {
        if (instance == null) {
            instance = new SesionManager();
        }
        return instance;
    }
    
    /**
     * Establece el paciente actual en sesión
     */
    public void setPacienteActual(Paciente paciente) {
        this.pacienteActual = paciente;
        this.medicoActual = null;
        this.rolActual = "paciente";
    }
    
    /**
     * Establece el médico actual en sesión
     */
    public void setMedicoActual(Medico medico) {
        this.medicoActual = medico;
        this.pacienteActual = null;
        this.rolActual = "medico";
    }
    
    /**
     * Obtiene el paciente actual en sesión
     */
    public Paciente getPacienteActual() {
        return pacienteActual;
    }
    
    /**
     * Obtiene el médico actual en sesión
     */
    public Medico getMedicoActual() {
        return medicoActual;
    }
    
    /**
     * Obtiene el rol actual
     */
    public String getRolActual() {
        return rolActual;
    }
    
    /**
     * Verifica si hay un paciente en sesión
     */
    public boolean isPacienteLogueado() {
        return pacienteActual != null && "paciente".equals(rolActual);
    }
    
    /**
     * Verifica si hay un médico en sesión
     */
    public boolean isMedicoLogueado() {
        return medicoActual != null && "medico".equals(rolActual);
    }
    
    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        this.pacienteActual = null;
        this.medicoActual = null;
        this.rolActual = null;
    }
    
    /**
     * Obtiene el ID del usuario actual
     */
    public int getUsuarioId() {
        if (isPacienteLogueado()) {
            return pacienteActual.getId();
        } else if (isMedicoLogueado()) {
            return medicoActual.getId();
        }
        return -1;
    }
    
    /**
     * Obtiene el nombre del usuario actual
     */
    public String getUsuarioNombre() {
        if (isPacienteLogueado()) {
            return pacienteActual.getNombre();
        } else if (isMedicoLogueado()) {
            return medicoActual.getNombre();
        }
        return "";
    }
}
