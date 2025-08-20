package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
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
import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.utils.SesionManager;

public class ReprogramarCitaController {

    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<String> cmbFecha;
    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnAceptar;
    
    // Elementos del panel de información de la cita
    @FXML private VBox panelInfoCita;
    @FXML private Label lblInfoFecha;
    @FXML private Label lblInfoHora;
    @FXML private Label lblInfoMedico;
    @FXML private Label lblInfoMotivo;
    
    // Elementos del menú expandible
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemMiPerfil;
    @FXML private VBox menuItemHistorialCitas;
    @FXML private VBox menuItemEditarInformacion;
    @FXML private VBox menuItemAgendarCita;
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemSalir;
    
    // Botones del menú
    @FXML private Button btnMiPerfil;
    @FXML private Button btnHistorialCitas;
    @FXML private Button btnEditarInformacion;
    @FXML private Button btnAgendarCita;
    @FXML private Button btnCancelarCita;
    @FXML private Button btnReprogramarCita;
    @FXML private Button btnSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;
    private int pacienteId; // ID del paciente logueado
    private Consulta citaSeleccionada; // Cita seleccionada para reprogramar
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
        System.out.println("ReprogramarCitaController - Paciente ID establecido: " + this.pacienteId);
        
        // No abrir automáticamente la ventana de selección aquí
        // Se abrirá cuando el usuario haga clic en el botón correspondiente
    }
    
    public void setPacienteId() {
        this.pacienteId = SesionManager.getInstance().getUsuarioId();
        System.out.println("ReprogramarCitaController - Paciente ID obtenido de sesión: " + this.pacienteId);
    }
    
    public void setCitaSeleccionada(Consulta cita) {
        this.citaSeleccionada = cita;
        System.out.println("ReprogramarCitaController - Cita seleccionada: ID=" + cita.getId() + 
                         ", Fecha=" + cita.getFecha() + ", Hora=" + cita.getHora());
        
        // Mostrar información de la cita seleccionada
        mostrarInformacionCita();
    }

    @FXML
    private void initialize() {
        System.out.println("ReprogramarCitaController inicializando...");
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Cargar opciones de hora y fecha
        cargarOpcionesHora();
        cargarOpcionesFecha();
        
        // Configurar efectos hover para los botones
        configurarEfectosHover();
        
        System.out.println("ReprogramarCitaController inicializado correctamente");
    }
    
    private void abrirSeleccionarCita() {
        try {
            System.out.println("ReprogramarCitaController - Abriendo seleccionar cita con pacienteId: " + pacienteId);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Obtener el controlador y configurarlo para "reprogramar"
            SeleccionarCitaController controller = loader.getController();
            System.out.println("ReprogramarCitaController - Controlador obtenido: " + (controller != null));
            
            controller.setAccion("reprogramar");
            System.out.println("ReprogramarCitaController - Acción establecida");
            
            controller.setPacienteId(pacienteId);
            System.out.println("ReprogramarCitaController - PacienteId establecido en el controlador: " + pacienteId);
            
            // Configurar el controlador para que use esta ventana actual
            controller.setVentanaActual((Stage) btnAceptar.getScene().getWindow());
            
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cita para Reprogramar");
            stage.setScene(new Scene(seleccionarRoot));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la selección de citas", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarOpcionesHora() {
        ObservableList<String> horas = FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"
        );
        cmbHora.setItems(horas);
    }
    
    private void cargarOpcionesFecha() {
        ObservableList<String> fechas = FXCollections.observableArrayList();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Generar fechas para los próximos 30 días
        for (int i = 1; i <= 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            fechas.add(fecha.format(formatter));
        }
        
        cmbFecha.setItems(fechas);
    }
    
    private void mostrarInformacionCita() {
        if (citaSeleccionada != null) {
            System.out.println("ReprogramarCitaController - Mostrando información de la cita seleccionada");
            
            // Obtener información del médico
            com.gestorcitasmedicas.model.Medico medico = com.gestorcitasmedicas.model.Medico.obtenerTodos().stream()
                .filter(m -> m.getId() == citaSeleccionada.getIdMedico())
                .findFirst()
                .orElse(null);
            
            // Mostrar información en el panel
            lblInfoFecha.setText(citaSeleccionada.getFecha().toString());
            lblInfoHora.setText(citaSeleccionada.getHora().toString());
            lblInfoMedico.setText(medico != null ? medico.getNombre() + " - " + medico.getEspecialidad() : "Médico " + citaSeleccionada.getIdMedico());
            lblInfoMotivo.setText(citaSeleccionada.getMotivo());
            
            // Mostrar el panel de información
            panelInfoCita.setVisible(true);
            panelInfoCita.setManaged(true);
            
            // Actualizar el prompt del área de texto
            txtMotivo.setPromptText("Motivo de reprogramación para cita del " + citaSeleccionada.getFecha() + 
                                   " a las " + citaSeleccionada.getHora());
        }
    }
    
    private void configurarEfectosHover() {
        // Efecto hover para el botón Cancelar
        btnCancelar.setOnMouseEntered(e -> {
            btnCancelar.setStyle("-fx-background-color: #666666; -fx-cursor: hand;");
        });
        
        btnCancelar.setOnMouseExited(e -> {
            btnCancelar.setStyle("-fx-background-color: #787a7d; -fx-cursor: hand;");
        });
        
        // Efecto hover para el botón Aceptar
        btnAceptar.setOnMouseEntered(e -> {
            btnAceptar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnAceptar.setOnMouseExited(e -> {
            btnAceptar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
        
        // Efectos hover para los botones del menú lateral
        Button[] botonesMenu = {btnMiPerfil, btnHistorialCitas, btnEditarInformacion, 
                               btnAgendarCita, btnCancelarCita, btnReprogramarCita, btnSalir};
        
        for (Button boton : botonesMenu) {
            if (boton != null) {
                boton.setOnMouseEntered(e -> {
                    boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 20;");
                });
                
                boton.setOnMouseExited(e -> {
                    boton.setStyle("-fx-background-color: transparent;");
                });
            }
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
        System.out.println("Aceptando reprogramación de cita...");
        
        // Validar que se haya seleccionado una cita
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para reprogramar", Alert.AlertType.ERROR);
            return;
        }
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Convertir fecha y hora seleccionadas
        final LocalDate nuevaFecha;
        final LocalTime nuevaHora;
        
        try {
            // Convertir fecha del formato dd/MM/yyyy
            String fechaStr = cmbFecha.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            nuevaFecha = LocalDate.parse(fechaStr, formatter);
            
            // Convertir hora del formato HH:mm
            String horaStr = cmbHora.getValue();
            nuevaHora = LocalTime.parse(horaStr);
            
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar fecha u hora seleccionada", Alert.AlertType.ERROR);
            return;
        }
        
        // Mostrar confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Reprogramación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
            "¿Está seguro de que desea reprogramar su cita?\n\n" +
            "Cita actual: " + citaSeleccionada.getFecha() + " a las " + citaSeleccionada.getHora() + "\n" +
            "Nueva fecha: " + nuevaFecha + "\n" +
            "Nueva hora: " + nuevaHora + "\n" +
            "Motivo: " + txtMotivo.getText()
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Reprogramar la cita
                try {
                    boolean reprogramacionExitosa = Consulta.reprogramarCita(
                        citaSeleccionada.getId(), 
                        nuevaFecha, 
                        nuevaHora, 
                        txtMotivo.getText()
                    );
                    
                    if (reprogramacionExitosa) {
                        System.out.println("Cita reprogramada exitosamente");
                        System.out.println("ID: " + citaSeleccionada.getId());
                        System.out.println("Nueva fecha: " + nuevaFecha);
                        System.out.println("Nueva hora: " + nuevaHora);
                        System.out.println("Motivo: " + txtMotivo.getText());
                        
                        // Mostrar ventana de confirmación exitosa
                        mostrarConfirmacionExitosa();
                    } else {
                        mostrarAlerta("Error", "No se pudo reprogramar la cita. Verifique la disponibilidad del médico.", Alert.AlertType.ERROR);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al reprogramar la cita: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        System.out.println("Cancelando operación...");
        
        // Regresar al panel principal del paciente
        regresarAlPanelPrincipal();
    }
    
    private boolean validarCampos() {
        // Validar hora seleccionada
        if (cmbHora.getValue() == null || cmbHora.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una hora", Alert.AlertType.ERROR);
            cmbHora.requestFocus();
            return false;
        }
        
        // Validar fecha seleccionada
        if (cmbFecha.getValue() == null || cmbFecha.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            cmbFecha.requestFocus();
            return false;
        }
        
        // Validar motivo
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo para la reprogramación", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        if (txtMotivo.getText().trim().length() < 10) {
            mostrarAlerta("Error", "El motivo debe tener al menos 10 caracteres", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void mostrarConfirmacionExitosa() {
        try {
            // Cargar la ventana de confirmación exitosa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCitaExitosa.fxml"));
            Parent exitosaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(exitosaRoot, 400, 200);
            Stage currentStage = (Stage) btnAceptar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cita Reprogramada Exitosamente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Éxito", "Su cita ha sido reprogramada exitosamente", Alert.AlertType.INFORMATION);
            regresarAlPanelPrincipal();
        }
    }
    
    private void regresarAlPanelPrincipal() {
        try {
            System.out.println("ReprogramarCitaController - Regresando al panel principal del paciente");
            
            // Intentar obtener la ventana actual de múltiples formas
            Stage currentStage = null;
            
            // Método 1: Desde cualquier nodo de la escena
            if (btnCancelar != null && btnCancelar.getScene() != null) {
                currentStage = (Stage) btnCancelar.getScene().getWindow();
                System.out.println("ReprogramarCitaController - Ventana obtenida desde btnCancelar");
            } else if (btnAceptar != null && btnAceptar.getScene() != null) {
                currentStage = (Stage) btnAceptar.getScene().getWindow();
                System.out.println("ReprogramarCitaController - Ventana obtenida desde btnAceptar");
            } else if (txtMotivo != null && txtMotivo.getScene() != null) {
                currentStage = (Stage) txtMotivo.getScene().getWindow();
                System.out.println("ReprogramarCitaController - Ventana obtenida desde txtMotivo");
            } else if (cmbHora != null && cmbHora.getScene() != null) {
                currentStage = (Stage) cmbHora.getScene().getWindow();
                System.out.println("ReprogramarCitaController - Ventana obtenida desde cmbHora");
            } else if (cmbFecha != null && cmbFecha.getScene() != null) {
                currentStage = (Stage) cmbFecha.getScene().getWindow();
                System.out.println("ReprogramarCitaController - Ventana obtenida desde cmbFecha");
            } else {
                // Método 2: Buscar cualquier nodo en la escena de manera más segura
                try {
                    if (btnCancelar != null && btnCancelar.getScene() != null && btnCancelar.getScene().getRoot() != null) {
                        for (javafx.scene.Node node : btnCancelar.getScene().getRoot().lookupAll("*")) {
                            if (node.getScene() != null) {
                                currentStage = (Stage) node.getScene().getWindow();
                                System.out.println("ReprogramarCitaController - Ventana obtenida desde nodo: " + node.getClass().getSimpleName());
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ReprogramarCitaController - Error en búsqueda de nodos: " + e.getMessage());
                }
            }
            
            if (currentStage != null) {
                System.out.println("ReprogramarCitaController - Ventana encontrada, cargando VistaPaciente.fxml");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                Parent pacienteRoot = loader.load();
                
                Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
                currentStage.setScene(nuevaEscena);
                currentStage.setTitle("Panel Principal - Paciente");
                currentStage.centerOnScreen();
                currentStage.show(); // Asegurar que la ventana se muestre
                System.out.println("ReprogramarCitaController - Navegación exitosa al panel principal");
            } else {
                System.out.println("ReprogramarCitaController - Error: No se pudo obtener la ventana actual");
                // Crear una nueva ventana como último recurso
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                Parent pacienteRoot = loader.load();
                
                Stage newStage = new Stage();
                Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
                newStage.setScene(nuevaEscena);
                newStage.setTitle("Panel Principal - Paciente");
                newStage.centerOnScreen();
                newStage.show();
                System.out.println("ReprogramarCitaController - Nueva ventana creada como respaldo");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ReprogramarCitaController - Error al cargar VistaPaciente.fxml: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo regresar al panel principal: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ReprogramarCitaController - Error inesperado: " + e.getMessage());
            mostrarAlerta("Error", "Error inesperado al regresar al panel principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Métodos del menú expandible
    private void configurarMenuExpandible() {
        // Configurar animación del menú
        timelineExpansion = new Timeline();

        // Verificar que los elementos del menú no sean null antes de configurar eventos
        if (menuLateral != null) {
            // Eventos del menú lateral
            menuLateral.setOnMouseEntered(e -> expandirMenu());
            menuLateral.setOnMouseExited(e -> contraerMenu());

            // Mostrar etiquetas inicialmente
            mostrarEtiquetasMenu(false);
        }
    }

    private void expandirMenu() {
        if (!menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = true;

            // Detener animación anterior si está en curso
            timelineExpansion.stop();

            // Hacer que el menú aparezca por encima del contenido
            menuLateral.toFront();

            // Animación de expansión
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 200);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);

            // Mostrar etiquetas durante la expansión
            mostrarEtiquetasMenu(true);

            timelineExpansion.play();
        }
    }

    private void contraerMenu() {
        if (menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = false;

            // Detener animación anterior si está en curso
            timelineExpansion.stop();

            // Animación de contracción
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);

            // Ocultar etiquetas durante la contracción
            mostrarEtiquetasMenu(false);

            timelineExpansion.play();
        }
    }

    private void mostrarEtiquetasMenu(boolean mostrar) {
        // Mostrar u ocultar etiquetas de texto en los elementos del menú
        if (menuItemMiPerfil != null) {
            menuItemMiPerfil.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemHistorialCitas != null) {
            menuItemHistorialCitas.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemEditarInformacion != null) {
            menuItemEditarInformacion.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemAgendarCita != null) {
            menuItemAgendarCita.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemCancelarCita != null) {
            menuItemCancelarCita.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemReprogramarCita != null) {
            menuItemReprogramarCita.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemSalir != null) {
            menuItemSalir.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
    }
    
    // Métodos de navegación del menú
    @FXML
    private void abrirMiPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot, 1366, 768);
            Stage currentStage = (Stage) btnMiPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Mi Perfil", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirHistorialCitas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/HISTCITAS.fxml"));
            Parent historialRoot = loader.load();
            
            Scene nuevaEscena = new Scene(historialRoot, 1366, 768);
            Stage currentStage = (Stage) btnHistorialCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Historial de Citas - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el Historial de Citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirEditarInformacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/EditarInformacion.fxml"));
            Parent editarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(editarRoot, 1366, 768);
            Stage currentStage = (Stage) btnEditarInformacion.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Editar Información - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Editar Información", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirAgendarCita() {
        try {
            System.out.println("Abriendo agendar cita...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaPaciente.fxml"));
            Parent agendarRoot = loader.load();
            
            // Obtener el controlador y configurar el ID del paciente
            com.gestorcitasmedicas.controller.AgendarCitaPacienteController controller = loader.getController();
            controller.setPacienteId(SesionManager.getInstance().getUsuarioId());
            
            Scene nuevaEscena = new Scene(agendarRoot, 1366, 768);
            Stage currentStage = (Stage) btnAgendarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCancelarCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCita.fxml"));
            Parent cancelarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(cancelarRoot, 1366, 768);
            Stage currentStage = (Stage) btnCancelarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cancelar Cita - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Cancelar Cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirReprogramarCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCita.fxml"));
            Parent reprogramarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(reprogramarRoot, 1366, 768);
            Stage currentStage = (Stage) btnReprogramarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Reprogramar Cita - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Reprogramar Cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salir() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            
            Scene nuevaEscena = new Scene(loginRoot, 1366, 768);
            Stage currentStage = (Stage) btnSalir.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Inicio de Sesión");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar sesión", Alert.AlertType.ERROR);
        }
    }
}
