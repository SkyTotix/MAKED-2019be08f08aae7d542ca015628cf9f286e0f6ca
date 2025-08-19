package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainRecepcionistaController {

    @FXML
    private Label consultasProgramadasLabel;
    
    @FXML
    private Label consultasConcluidasLabel;
    
    @FXML
    private Label consultasReprogramadasLabel;
    
    @FXML
    private DatePicker calendario;
    
    @FXML
    private VBox contenedorConsultas;
    
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

    // Datos simulados de consultas
    private List<Consulta> consultasDelDia;
    
    // Variables para el menú expandible
    private boolean menuExpandido = false;
    private Timeline timelineExpansion;
    
    @FXML
    private void initialize() {
        System.out.println("MainRecepcionistaController inicializando...");
        
        // Configurar el calendario
        configurarCalendario();
        
        // Cargar datos iniciales
        cargarDatosIniciales();
        
        // Configurar eventos de botones
        configurarEventosBotones();
        
        System.out.println("MainRecepcionistaController inicializado correctamente");
    }
    
    private void configurarCalendario() {
        System.out.println("Configurando calendario en MainRecepcionistaController...");
        
        // Establecer fecha actual
        calendario.setValue(LocalDate.now());
        
        // Configurar el evento de cambio de fecha
        calendario.setOnAction(event -> {
            LocalDate fechaSeleccionada = calendario.getValue();
            System.out.println("Fecha seleccionada: " + fechaSeleccionada);
            if (fechaSeleccionada != null) {
                cargarConsultasDelDia(fechaSeleccionada);
            }
        });
        
        // Cargar consultas del día actual
        cargarConsultasDelDia(LocalDate.now());
        
        System.out.println("Calendario configurado correctamente en MainRecepcionistaController");
    }
    
    private void cargarDatosIniciales() {
        // Simular datos de contadores
        actualizarContadores(15, 8, 3);
        
        // Cargar consultas del día actual
        cargarConsultasDelDia(LocalDate.now());
    }
    
    private void actualizarContadores(int programadas, int concluidas, int reprogramadas) {
        if (consultasProgramadasLabel != null) {
            consultasProgramadasLabel.setText(String.valueOf(programadas));
        }
        if (consultasConcluidasLabel != null) {
            consultasConcluidasLabel.setText(String.valueOf(concluidas));
        }
        if (consultasReprogramadasLabel != null) {
            consultasReprogramadasLabel.setText(String.valueOf(reprogramadas));
        }
    }
    
    private void actualizarContadoresSegunFecha(LocalDate fecha) {
        int diaSemana = fecha.getDayOfWeek().getValue();
        int diaMes = fecha.getDayOfMonth();
        
        // Solo mostrar contadores para días laborables (lunes a viernes)
        if (diaSemana >= 1 && diaSemana <= 5) {
            if (diaMes % 3 == 0) {
                // Día con muchas citas
                actualizarContadores(7, 3, 1);
            } else if (diaMes % 3 == 1) {
                // Día con citas moderadas
                actualizarContadores(4, 2, 0);
            } else {
                // Día con pocas citas
                actualizarContadores(2, 1, 0);
            }
        } else {
            // Fines de semana - sin citas
            actualizarContadores(0, 0, 0);
        }
    }
    
    private void cargarConsultasDelDia(LocalDate fecha) {
        System.out.println("Cargando consultas para fecha: " + fecha);
        
        // Limpiar contenedor
        if (contenedorConsultas != null) {
            contenedorConsultas.getChildren().clear();
        }
        
        // Simular consultas del día
        consultasDelDia = generarConsultasSimuladas(fecha);
        System.out.println("Consultas generadas: " + consultasDelDia.size());
        
        // Crear y mostrar las consultas
        if (consultasDelDia.isEmpty()) {
            // Mostrar mensaje cuando no hay citas
            Label mensajeLabel = new Label("No hay citas programadas para este día");
            mensajeLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #666; -fx-font-style: italic;");
            mensajeLabel.setAlignment(javafx.geometry.Pos.CENTER);
            if (contenedorConsultas != null) {
                contenedorConsultas.getChildren().add(mensajeLabel);
            }
            System.out.println("Mostrando mensaje de no hay citas");
        } else {
            for (Consulta consulta : consultasDelDia) {
                VBox consultaBox = crearConsultaBox(consulta);
                if (contenedorConsultas != null) {
                    contenedorConsultas.getChildren().add(consultaBox);
                }
            }
            System.out.println("Agregadas " + consultasDelDia.size() + " consultas al contenedor");
        }
        
        // Actualizar contadores según las citas del día
        actualizarContadoresSegunFecha(fecha);
    }
    
    private List<Consulta> generarConsultasSimuladas(LocalDate fecha) {
        List<Consulta> consultas = new ArrayList<>();
        
        // Generar citas diferentes según la fecha
        int diaSemana = fecha.getDayOfWeek().getValue();
        int diaMes = fecha.getDayOfMonth();
        
        // Solo mostrar citas de lunes a viernes (1-5)
        if (diaSemana >= 1 && diaSemana <= 5) {
            
            // Generar citas basadas en el día del mes para simular variación
            if (diaMes % 3 == 0) {
                // Día con muchas citas
                consultas.add(new Consulta("Dr. Juan Pérez", "María García", "Consultorio 1", 
                    LocalTime.of(8, 0), LocalTime.of(8, 30), "Consulta general"));
                consultas.add(new Consulta("Dra. Ana López", "Carlos Rodríguez", "Consultorio 2", 
                    LocalTime.of(9, 0), LocalTime.of(9, 30), "Revisión"));
                consultas.add(new Consulta("Dr. Roberto Silva", "Laura Martínez", "Consultorio 3", 
                    LocalTime.of(10, 0), LocalTime.of(10, 30), "Control"));
                consultas.add(new Consulta("Dra. Patricia Ruiz", "Miguel Torres", "Consultorio 1", 
                    LocalTime.of(11, 0), LocalTime.of(11, 30), "Consulta especializada"));
                consultas.add(new Consulta("Dr. Fernando Díaz", "Carmen Vega", "Consultorio 2", 
                    LocalTime.of(14, 0), LocalTime.of(14, 30), "Seguimiento"));
                consultas.add(new Consulta("Dra. Carmen Vega", "Roberto Silva", "Consultorio 3", 
                    LocalTime.of(15, 0), LocalTime.of(15, 30), "Control rutinario"));
                consultas.add(new Consulta("Dr. Miguel Torres", "Ana López", "Consultorio 1", 
                    LocalTime.of(16, 0), LocalTime.of(16, 30), "Consulta de urgencia"));
            } else if (diaMes % 3 == 1) {
                // Día con citas moderadas
                consultas.add(new Consulta("Dr. Juan Pérez", "Fernando Díaz", "Consultorio 1", 
                    LocalTime.of(9, 0), LocalTime.of(9, 30), "Consulta general"));
                consultas.add(new Consulta("Dra. Ana López", "Patricia Ruiz", "Consultorio 2", 
                    LocalTime.of(10, 30), LocalTime.of(11, 0), "Revisión"));
                consultas.add(new Consulta("Dr. Roberto Silva", "Carmen Vega", "Consultorio 3", 
                    LocalTime.of(14, 0), LocalTime.of(14, 30), "Control"));
                consultas.add(new Consulta("Dra. Patricia Ruiz", "Miguel Torres", "Consultorio 1", 
                    LocalTime.of(15, 30), LocalTime.of(16, 0), "Consulta especializada"));
            } else {
                // Día con pocas citas
                consultas.add(new Consulta("Dr. Juan Pérez", "Laura Martínez", "Consultorio 1", 
                    LocalTime.of(10, 0), LocalTime.of(10, 30), "Consulta general"));
                consultas.add(new Consulta("Dra. Ana López", "Roberto Silva", "Consultorio 2", 
                    LocalTime.of(14, 0), LocalTime.of(14, 30), "Revisión"));
            }
        }
        
        return consultas;
    }
    
    private VBox crearConsultaBox(Consulta consulta) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #E7ECF0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        box.setPrefWidth(300);
        
        // Título con doctor y paciente
        Label tituloLabel = new Label(consulta.getDoctor() + " - " + consulta.getPaciente());
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        // Información de la consulta
        Label consultorioLabel = new Label("Consultorio: " + consulta.getConsultorio());
        Label horarioLabel = new Label("Horario: " + consulta.getHoraInicio() + " - " + consulta.getHoraFin());
        Label motivoLabel = new Label("Motivo: " + consulta.getMotivo());
        
        // Botones de acción
        HBox botonesBox = new HBox(10);
        Button btnEditar = new Button("Editar");
        Button btnCancelar = new Button("Cancelar");
        
        btnEditar.setStyle("-fx-background-color: #3B6F89; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        
        botonesBox.getChildren().addAll(btnEditar, btnCancelar);
        
        box.getChildren().addAll(tituloLabel, consultorioLabel, horarioLabel, motivoLabel, botonesBox);
        
        return box;
    }
    
    private void configurarEventosBotones() {
        System.out.println("Configurando eventos de botones...");
        
        // Evento para el botón de perfil
        if (btnPerfil != null) {
            btnPerfil.setOnAction(this::abrirPerfil);
            System.out.println("Botón de perfil configurado");
        }
        
        // Configurar menú expandible
        configurarMenuExpandible();
        System.out.println("Menú expandible configurado");
        
        // Configurar eventos del menú lateral
        configurarEventosMenuLateral();
        System.out.println("Eventos del menú lateral configurados");
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
        // Evento para gestión de usuarios
        menuItemUsuarios.setOnMouseClicked(event -> {
            System.out.println("Click en Gestión de Usuarios");
            abrirGestionPacientes();
        });
        
        // Evento para gestión de médicos
        menuItemMedicos.setOnMouseClicked(event -> {
            System.out.println("Click en Gestión de Médicos");
            abrirGestionMedicos();
        });
        
        // Evento para agendar citas
        menuItemCitas.setOnMouseClicked(event -> {
            System.out.println("Click en Agendar Citas");
            abrirGestionCitas();
        });
        
        // Evento para cerrar sesión
        menuItemSalir.setOnMouseClicked(event -> {
            System.out.println("Click en Cerrar Sesión");
            salir();
        });
        
        // Efectos de hover para mejor feedback visual
        configurarEfectosHover();
    }
    
    private void configurarEfectosHover() {
        // Efectos para Gestión de Usuarios
        menuItemUsuarios.setOnMouseEntered(event -> {
            menuItemUsuarios.setStyle("-fx-background-color: #4A7A9A; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        menuItemUsuarios.setOnMouseExited(event -> {
            menuItemUsuarios.setStyle("-fx-background-color: #3B6F89; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        
        // Efectos para Gestión de Médicos
        menuItemMedicos.setOnMouseEntered(event -> {
            menuItemMedicos.setStyle("-fx-background-color: #4A7A9A; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        menuItemMedicos.setOnMouseExited(event -> {
            menuItemMedicos.setStyle("-fx-background-color: #3B6F89; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        
        // Efectos para Agendar Citas
        menuItemCitas.setOnMouseEntered(event -> {
            menuItemCitas.setStyle("-fx-background-color: #4A7A9A; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        menuItemCitas.setOnMouseExited(event -> {
            menuItemCitas.setStyle("-fx-background-color: #3B6F89; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        
        // Efectos para Cerrar Sesión
        menuItemSalir.setOnMouseEntered(event -> {
            menuItemSalir.setStyle("-fx-background-color: #4A7A9A; -fx-background-radius: 5; -fx-cursor: hand;");
        });
        menuItemSalir.setOnMouseExited(event -> {
            menuItemSalir.setStyle("-fx-background-color: #3B6F89; -fx-background-radius: 5; -fx-cursor: hand;");
        });
    }
    
    @FXML
    private void abrirPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo ventana de perfil...");
            
            // Verificar si el archivo existe
            java.net.URL resourceUrl = getClass().getResource("/com/gestorcitasmedicas/perfilAdmMod.fxml");
            if (resourceUrl == null) {
                throw new IOException("No se pudo encontrar el archivo perfilAdmMod.fxml en el classpath");
            }
            System.out.println("Archivo FXML encontrado en: " + resourceUrl);
            
            // Cargar la ventana de perfil
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            
            Parent perfilRoot = loader.load();
            System.out.println("FXML cargado exitosamente");
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(perfilRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Perfil - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
            System.out.println("Ventana de perfil abierta exitosamente");
            
        } catch (IOException e) {
            System.err.println("Error al cargar perfilAdmMod.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar perfilAdmMod.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al cargar la ventana de perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionPacientes(ActionEvent event) {
        abrirGestionPacientes();
    }
    
    private void abrirGestionPacientes() {
        try {
            // Cargar la nueva ventana completa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionMedicos(ActionEvent event) {
        abrirGestionMedicos();
    }
    
    private void abrirGestionMedicos() {
        try {
            System.out.println("Intentando cargar gestMedicos.fxml...");
            
            // Verificar si el archivo existe
            java.net.URL resourceUrl = getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml");
            if (resourceUrl == null) {
                throw new IOException("No se pudo encontrar el archivo gestMedicos.fxml en el classpath");
            }
            System.out.println("Archivo FXML encontrado en: " + resourceUrl);
            
            // Cargar la nueva ventana completa
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            
            Parent gestMedicosRoot = loader.load();
            System.out.println("FXML cargado exitosamente");
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
            System.out.println("Ventana de gestión de médicos abierta exitosamente");
            
        } catch (IOException e) {
            System.err.println("Error al cargar gestMedicos.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de médicos: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar gestMedicos.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al cargar la gestión de médicos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionCitas(ActionEvent event) {
        abrirGestionCitas();
    }
    
    private void abrirGestionCitas() {
        try {
            System.out.println("Intentando cargar agendarCitaAdm.fxml...");
            
            // Verificar si el archivo existe
            java.net.URL resourceUrl = getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml");
            if (resourceUrl == null) {
                throw new IOException("No se pudo encontrar el archivo agendarCitaAdm.fxml en el classpath");
            }
            System.out.println("Archivo FXML encontrado en: " + resourceUrl);
            
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            
            Parent agendarCitaRoot = loader.load();
            System.out.println("FXML cargado exitosamente");
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
            System.out.println("Ventana de agendar citas abierta exitosamente");
            
        } catch (IOException e) {
            System.err.println("Error al cargar agendarCitaAdm.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar agendarCitaAdm.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al cargar la ventana de agendar citas: " + e.getMessage(), Alert.AlertType.ERROR);
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
    
    // Clase interna para representar una consulta
    private static class Consulta {
        private String doctor;
        private String paciente;
        private String consultorio;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private String motivo;
        
        public Consulta(String doctor, String paciente, String consultorio, 
                       LocalTime horaInicio, LocalTime horaFin, String motivo) {
            this.doctor = doctor;
            this.paciente = paciente;
            this.consultorio = consultorio;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.motivo = motivo;
        }
        
        // Getters
        public String getDoctor() { return doctor; }
        public String getPaciente() { return paciente; }
        public String getConsultorio() { return consultorio; }
        public LocalTime getHoraInicio() { return horaInicio; }
        public LocalTime getHoraFin() { return horaFin; }
        public String getMotivo() { return motivo; }
    }
}
