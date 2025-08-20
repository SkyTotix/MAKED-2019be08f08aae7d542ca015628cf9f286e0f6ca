package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestUsuariosController {

    @FXML
    private TableView<Usuario> tablaUsuarios;
    
    @FXML
    private TableColumn<Usuario, String> colNombre;
    
    @FXML
    private TableColumn<Usuario, String> colCurp;
    

    
    @FXML
    private TableColumn<Usuario, String> colMatricula;
    
    @FXML
    private TableColumn<Usuario, String> colEstatus;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnPacientes;
    
    @FXML
    private Button btnMedicos;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private VBox menuLateral;
    
    @FXML
    private VBox menuItemUsuarios;
    
    @FXML
    private VBox menuItemMedicos;
    
    @FXML
    private VBox menuItemCitas;
    
    @FXML
    private VBox menuItemSalir;

    // Lista observable de usuarios
    private ObservableList<Usuario> usuariosList;
    
    // Variables para el menú expandible
    private boolean menuExpandido = false;
    private javafx.animation.Timeline timelineExpansion;

    @FXML
    private void initialize() {
        // Configurar la tabla
        configurarTabla();
        
        // Cargar datos iniciales
        cargarDatosIniciales();
        
        // Configurar eventos de botones
        configurarEventosBotones();
    }
    
    private void configurarTabla() {
        // Configurar las columnas
        colNombre.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombreCompleto()));
        colCurp.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCurp()));
        colMatricula.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMatricula()));
        colEstatus.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstatus()));
        
        // Configurar estilo de la tabla
        tablaUsuarios.setStyle("-fx-background-color: #E7ECF0; -fx-table-cell-border-color: #3B6F89;");
        
        // Configurar selección múltiple
        tablaUsuarios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Configurar evento de doble clic para editar
        tablaUsuarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
                if (usuarioSeleccionado != null) {
                    editarUsuario(usuarioSeleccionado);
                }
            }
        });
    }
    
    private void cargarDatosIniciales() {
        // Crear lista observable
        usuariosList = FXCollections.observableArrayList();
        
        // Agregar datos simulados
        usuariosList.addAll(generarUsuariosSimulados());
        
        // Asignar la lista a la tabla
        tablaUsuarios.setItems(usuariosList);
    }
    
    private List<Usuario> generarUsuariosSimulados() {
        List<Usuario> usuarios = new ArrayList<>();
        
        usuarios.add(new Usuario("María García López", "GAGL800315MDFLNR09", 43, 65.5, 1.65, "Femenino", "P001", "Activo"));
        usuarios.add(new Usuario("Carlos Rodríguez Pérez", "ROPC750822HDFLNR08", 48, 78.2, 1.72, "Masculino", "P002", "Activo"));
        usuarios.add(new Usuario("Ana Martínez Silva", "MASL820510MDFLNR07", 41, 58.0, 1.60, "Femenino", "P003", "Activo"));
        usuarios.add(new Usuario("Luis Torres Vega", "TOVL770625HDFLNR06", 46, 82.5, 1.75, "Masculino", "P004", "Inactivo"));
        usuarios.add(new Usuario("Carmen Ruiz Díaz", "RUDC850320MDFLNR05", 38, 62.8, 1.63, "Femenino", "P005", "Activo"));
        usuarios.add(new Usuario("Roberto Silva Mendoza", "SIMR790415HDFLNR04", 44, 75.0, 1.70, "Masculino", "P006", "Activo"));
        
        return usuarios;
    }
    
    private void configurarEventosBotones() {
        // Evento para el botón de agregar
        if (btnAgregar != null) {
            btnAgregar.setOnAction(this::agregarUsuario);
        }
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar eventos del menú lateral
        configurarEventosMenuLateral();
    }
    
    private void configurarMenuExpandible() {
        // Eventos del mouse para expandir/contraer el menú
        menuLateral.setOnMouseEntered(event -> {
            if (!menuExpandido) {
                expandirMenu();
            }
        });
        
        // Usar un delay para evitar contracciones accidentales
        menuLateral.setOnMouseExited(event -> {
            // Usar Platform.runLater para evitar conflictos
            javafx.application.Platform.runLater(() -> {
                if (menuExpandido && !menuLateral.isHover()) {
                    contraerMenu();
                }
            });
        });
        
        // Configurar timeline para animación
        timelineExpansion = new Timeline();
    }
    
    private void expandirMenu() {
        if (!menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = true;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 160);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Mostrar las etiquetas
            mostrarEtiquetasMenu(true);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void contraerMenu() {
        if (menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = false;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Ocultar las etiquetas
            mostrarEtiquetasMenu(false);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void mostrarEtiquetasMenu(boolean mostrar) {
        // Obtener las etiquetas de cada elemento del menú
        for (javafx.scene.Node node : menuItemUsuarios.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemMedicos.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemCitas.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemSalir.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Evento para gestión de usuarios (ya estamos aquí)
        menuItemUsuarios.setOnMouseClicked(event -> {
            mostrarAlerta("Gestión de Pacientes", "Ya estás en la gestión de pacientes", Alert.AlertType.INFORMATION);
        });
        
        // Evento para gestión de médicos
        menuItemMedicos.setOnMouseClicked(event -> {
            abrirGestionMedicos();
        });
        
        // Evento para agendar citas
        menuItemCitas.setOnMouseClicked(event -> {
            abrirGestionCitas();
        });
        
        // Evento para cerrar sesión
        menuItemSalir.setOnMouseClicked(event -> {
            salir();
        });
    }
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            // Cargar la ventana principal completa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRecepcionistaRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(mainRecepcionistaRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void agregarUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agregarUsuario.fxml"));
            Parent agregarUsuarioRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agregarUsuarioRoot);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agregar Paciente - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agregar usuario", Alert.AlertType.ERROR);
        }
    }
    
    private void editarUsuario(Usuario usuario) {
        mostrarAlerta("Editar Usuario", 
            "Editando usuario: " + usuario.getNombreCompleto() + "\n\nFunción en desarrollo", 
            Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void abrirPerfil(ActionEvent event) {
        mostrarAlerta("Perfil", "Función de perfil en desarrollo", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void abrirGestionPacientes(ActionEvent event) {
        mostrarAlerta("Gestión de Pacientes", "Ya estás en la gestión de pacientes", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void abrirGestionMedicos(ActionEvent event) {
        abrirGestionMedicos();
    }
    
    private void abrirGestionMedicos() {
        try {
            // Cargar la nueva ventana completa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestMedicosRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionCitas(ActionEvent event) {
        abrirGestionCitas();
    }
    
    private void abrirGestionCitas() {
        try {
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salir(ActionEvent event) {
        salir();
    }
    
    private void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea salir del sistema?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Volver al login
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setMaximized(true);
                    stage.show();

                    // Cerrar la ventana actual
                    Stage currentStage = (Stage) menuLateral.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Clase interna para representar un usuario
    public static class Usuario {
        private String nombreCompleto;
        private String curp;
        private int edad;
        private double peso;
        private double altura;
        private String genero;
        private String matricula;
        private String estatus;
        
        public Usuario(String nombreCompleto, String curp, int edad, double peso, 
                      double altura, String genero, String matricula, String estatus) {
            this.nombreCompleto = nombreCompleto;
            this.curp = curp;
            this.edad = edad;
            this.peso = peso;
            this.altura = altura;
            this.genero = genero;
            this.matricula = matricula;
            this.estatus = estatus;
        }
        
        // Getters
        public String getNombreCompleto() { return nombreCompleto; }
        public String getCurp() { return curp; }
        public int getEdad() { return edad; }
        public double getPeso() { return peso; }
        public double getAltura() { return altura; }
        public String getGenero() { return genero; }
        public String getMatricula() { return matricula; }
        public String getEstatus() { return estatus; }
        
        // Setters
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
        public void setCurp(String curp) { this.curp = curp; }
        public void setEdad(int edad) { this.edad = edad; }
        public void setPeso(double peso) { this.peso = peso; }
        public void setAltura(double altura) { this.altura = altura; }
        public void setGenero(String genero) { this.genero = genero; }
        public void setMatricula(String matricula) { this.matricula = matricula; }
        public void setEstatus(String estatus) { this.estatus = estatus; }
    }
}
