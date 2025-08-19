package com.gestorcitasmedicas.model;
import com.gestorcitasmedicas.utils.OracleDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int id;
    private String correo;
    private String nombre;
    private String apellidos;
    private String matricula;
    private String curp;
    private String telefono;
    private String contrasena;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String rol, String contrasena, String nombre, String correo) {
        this.id = id;
        this.rol = rol;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public static Usuario autenticar (String correo, String contrasena) {
        String query = "SELECT u.id_usuario, u.nombre, r.nombre_rol " +
                "FROM usuarios u " +
                "JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.correo = ? AND u.contrasena = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("nombre_rol");
                return new Usuario(id, rol, contrasena, nombre, correo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    }

