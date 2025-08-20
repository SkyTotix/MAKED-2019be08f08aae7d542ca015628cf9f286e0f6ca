package com.gestorcitasmedicas.utils;

import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Consulta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Clase para manejar la persistencia de datos en archivos JSON
 * Permite que los datos sean portables entre diferentes computadoras
 */
public class DataPersistence {
    
    private static final String DATA_DIR = "data";
    private static final String PACIENTES_FILE = "pacientes.json";
    private static final String MEDICOS_FILE = "medicos.json";
    private static final String CONSULTAS_FILE = "consultas.json";
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();
    
    /**
     * Inicializa el directorio de datos si no existe
     */
    private static void initializeDataDirectory() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                System.out.println("Directorio de datos creado: " + dataPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorio de datos: " + e.getMessage());
        }
    }
    
    /**
     * Guarda la lista de pacientes en archivo JSON
     */
    public static boolean savePacientes(List<Paciente> pacientes) {
        try {
            initializeDataDirectory();
            String json = gson.toJson(pacientes);
            Path filePath = Paths.get(DATA_DIR, PACIENTES_FILE);
            Files.write(filePath, json.getBytes());
            System.out.println("Pacientes guardados en: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carga la lista de pacientes desde archivo JSON
     */
    public static List<Paciente> loadPacientes() {
        try {
            Path filePath = Paths.get(DATA_DIR, PACIENTES_FILE);
            if (!Files.exists(filePath)) {
                System.out.println("Archivo de pacientes no encontrado, usando datos de prueba");
                return null;
            }
            
            String json = new String(Files.readAllBytes(filePath));
            Type listType = new TypeToken<List<Paciente>>(){}.getType();
            List<Paciente> pacientes = gson.fromJson(json, listType);
            System.out.println("Pacientes cargados desde: " + filePath.toAbsolutePath());
            return pacientes;
        } catch (IOException e) {
            System.err.println("Error al cargar pacientes: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Guarda la lista de médicos en archivo JSON
     */
    public static boolean saveMedicos(List<Medico> medicos) {
        try {
            initializeDataDirectory();
            String json = gson.toJson(medicos);
            Path filePath = Paths.get(DATA_DIR, MEDICOS_FILE);
            Files.write(filePath, json.getBytes());
            System.out.println("Médicos guardados en: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar médicos: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carga la lista de médicos desde archivo JSON
     */
    public static List<Medico> loadMedicos() {
        try {
            Path filePath = Paths.get(DATA_DIR, MEDICOS_FILE);
            if (!Files.exists(filePath)) {
                System.out.println("Archivo de médicos no encontrado, usando datos de prueba");
                return null;
            }
            
            String json = new String(Files.readAllBytes(filePath));
            Type listType = new TypeToken<List<Medico>>(){}.getType();
            List<Medico> medicos = gson.fromJson(json, listType);
            System.out.println("Médicos cargados desde: " + filePath.toAbsolutePath());
            return medicos;
        } catch (IOException e) {
            System.err.println("Error al cargar médicos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Guarda la lista de consultas en archivo JSON
     */
    public static boolean saveConsultas(List<Consulta> consultas) {
        try {
            initializeDataDirectory();
            String json = gson.toJson(consultas);
            Path filePath = Paths.get(DATA_DIR, CONSULTAS_FILE);
            Files.write(filePath, json.getBytes());
            System.out.println("Consultas guardadas en: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar consultas: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carga la lista de consultas desde archivo JSON
     */
    public static List<Consulta> loadConsultas() {
        try {
            Path filePath = Paths.get(DATA_DIR, CONSULTAS_FILE);
            if (!Files.exists(filePath)) {
                System.out.println("Archivo de consultas no encontrado, usando datos de prueba");
                return null;
            }
            
            String json = new String(Files.readAllBytes(filePath));
            Type listType = new TypeToken<List<Consulta>>(){}.getType();
            List<Consulta> consultas = gson.fromJson(json, listType);
            System.out.println("Consultas cargadas desde: " + filePath.toAbsolutePath());
            return consultas;
        } catch (IOException e) {
            System.err.println("Error al cargar consultas: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Guarda todos los datos (pacientes, médicos, consultas)
     */
    public static boolean saveAllData(List<Paciente> pacientes, List<Medico> medicos, List<Consulta> consultas) {
        boolean success = true;
        success &= savePacientes(pacientes);
        success &= saveMedicos(medicos);
        success &= saveConsultas(consultas);
        
        if (success) {
            System.out.println("Todos los datos guardados exitosamente");
        } else {
            System.err.println("Error al guardar algunos datos");
        }
        
        return success;
    }
    
    /**
     * Carga todos los datos desde archivos JSON
     */
    public static boolean loadAllData() {
        List<Paciente> pacientes = loadPacientes();
        List<Medico> medicos = loadMedicos();
        List<Consulta> consultas = loadConsultas();
        
        if (pacientes != null) {
            Paciente.cargarDesdeLista(pacientes);
        }
        
        if (medicos != null) {
            Medico.cargarDesdeLista(medicos);
        }
        
        if (consultas != null) {
            Consulta.cargarDesdeLista(consultas);
        }
        
        return pacientes != null || medicos != null || consultas != null;
    }
    
    /**
     * Verifica si existen archivos de datos guardados
     */
    public static boolean hasSavedData() {
        Path pacientesPath = Paths.get(DATA_DIR, PACIENTES_FILE);
        Path medicosPath = Paths.get(DATA_DIR, MEDICOS_FILE);
        Path consultasPath = Paths.get(DATA_DIR, CONSULTAS_FILE);
        
        return Files.exists(pacientesPath) || 
               Files.exists(medicosPath) || 
               Files.exists(consultasPath);
    }
    
    /**
     * Obtiene la ruta del directorio de datos
     */
    public static String getDataDirectoryPath() {
        return Paths.get(DATA_DIR).toAbsolutePath().toString();
    }
}
