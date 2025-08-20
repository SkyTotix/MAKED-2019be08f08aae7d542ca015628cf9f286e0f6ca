package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.utils.SesionManager;

public class CancelarCitaController {

    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnContinuar;
    
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
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemSalir;
    
    // Botones del menú
    @FXML private Button btnMiPerfil;
    @FXML private Button btnHistorialCitas;
    @FXML private Button btnEditarInformacion;
    @FXML private Button btnCancelarCita;
    @FXML private Button btnReprogramarCita;
    @FXML private Button btnSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;
    private int pacienteId; // ID del paciente logueado
    private Consulta citaSeleccionada; // Cita seleccionada para cancelar
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
        System.out.println("CancelarCitaController - Paciente ID establecido: " + this.pacienteId);
        
        // No abrir automáticamente la ventana de selección aquí
        // Se abrirá cuando el usuario haga clic en el botón correspondiente
    }
    
    public void setPacienteId() {
        this.pacienteId = SesionManager.getInstance().getUsuarioId();
        System.out.println("CancelarCitaController - Paciente ID obtenido de sesión: " + this.pacienteId);
    }
    
    public void setCitaSeleccionada(Consulta cita) {
        this.citaSeleccionada = cita;
        System.out.println("CancelarCitaController - Cita seleccionada: ID=" + cita.getId() + 
                         ", Fecha=" + cita.getFecha() + ", Hora=" + cita.getHora());
        
        // Mostrar información de la cita seleccionada
        mostrarInformacionCita();
    }

    @FXML
    private void initialize() {
        System.out.println("CancelarCitaController inicializando...");
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar efectos hover para los botones
        configurarEfectosHover();
        
        System.out.println("CancelarCitaController inicializado correctamente");
    }
    
    private void abrirSeleccionarCita() {
        try {
            System.out.println("CancelarCitaController - Abriendo seleccionar cita con pacienteId: " + pacienteId);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Obtener el controlador y configurarlo para "cancelar"
            SeleccionarCitaController controller = loader.getController();
            System.out.println("CancelarCitaController - Controlador obtenido: " + (controller != null));
            
            controller.setAccion("cancelar");
            System.out.println("CancelarCitaController - Acción establecida");
            
            controller.setPacienteId(pacienteId);
            System.out.println("CancelarCitaController - PacienteId establecido en el controlador: " + pacienteId);
            
            // Configurar el controlador para que use esta ventana actual
            controller.setVentanaActual((Stage) btnCancelar.getScene().getWindow());
            
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cita para Cancelar");
            stage.setScene(new Scene(seleccionarRoot));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la selección de citas", Alert.AlertType.ERROR);
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
        
        // Efecto hover para el botón Continuar
        btnContinuar.setOnMouseEntered(e -> {
            btnContinuar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnContinuar.setOnMouseExited(e -> {
            btnContinuar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
        
        // Efectos hover para los botones del menú lateral
        Button[] botonesMenu = {btnMiPerfil, btnHistorialCitas, btnEditarInformacion, 
                               btnCancelarCita, btnReprogramarCita, btnSalir};
        
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

    private void mostrarInformacionCita() {
        if (citaSeleccionada != null) {
            System.out.println("CancelarCitaController - Mostrando información de la cita seleccionada");
            
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
            txtMotivo.setPromptText("Motivo de cancelación para cita del " + citaSeleccionada.getFecha() + 
                                   " a las " + citaSeleccionada.getHora());
        }
    }
    
    @FXML
    private void continuar(ActionEvent event) {
        System.out.println("Continuando con la cancelación de cita...");
        
        // Validar que se haya seleccionado una cita
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para cancelar", Alert.AlertType.ERROR);
            return;
        }
        
        // Validar que se haya ingresado un motivo
        if (!validarMotivo()) {
            return;
        }
        
        // Mostrar confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cancelación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea cancelar su cita?\n\nMotivo: " + txtMotivo.getText());
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Cancelar la cita en memoria manteniendo el historial
                try {
                    boolean cancelacionExitosa = Consulta.cancelarCita(citaSeleccionada.getId(), txtMotivo.getText());
                    
                    if (cancelacionExitosa) {
                        System.out.println("Cita cancelada exitosamente en memoria: ID=" + citaSeleccionada.getId());
                        System.out.println("Motivo: " + txtMotivo.getText());
                        System.out.println("La cita se mantiene en el historial del paciente");
                        
                        // Mostrar ventana de confirmación exitosa
                        mostrarConfirmacionExitosa();
                    } else {
                        mostrarAlerta("Error", "No se pudo cancelar la cita", Alert.AlertType.ERROR);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al cancelar la cita: " + e.getMessage(), Alert.AlertType.ERROR);
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
    
    private boolean validarMotivo() {
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo para la cancelación", Alert.AlertType.ERROR);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCitaExitosa.fxml"));
            Parent exitosaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(exitosaRoot, 400, 200);
            Stage currentStage = (Stage) btnContinuar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cita Cancelada Exitosamente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Éxito", "Su cita ha sido cancelada exitosamente", Alert.AlertType.INFORMATION);
            regresarAlPanelPrincipal();
        }
    }
    
    private void regresarAlPanelPrincipal() {
        try {
            System.out.println("CancelarCitaController - Regresando al panel principal del paciente");
            
            // Intentar obtener la ventana actual de múltiples formas
            Stage currentStage = null;
            
            // Método 1: Desde cualquier nodo de la escena
            if (btnCancelar != null && btnCancelar.getScene() != null) {
                currentStage = (Stage) btnCancelar.getScene().getWindow();
                System.out.println("CancelarCitaController - Ventana obtenida desde btnCancelar");
            } else if (btnContinuar != null && btnContinuar.getScene() != null) {
                currentStage = (Stage) btnContinuar.getScene().getWindow();
                System.out.println("CancelarCitaController - Ventana obtenida desde btnContinuar");
            } else if (txtMotivo != null && txtMotivo.getScene() != null) {
                currentStage = (Stage) txtMotivo.getScene().getWindow();
                System.out.println("CancelarCitaController - Ventana obtenida desde txtMotivo");
            } else {
                // Método 2: Buscar cualquier nodo en la escena de manera más segura
                try {
                    if (btnCancelar != null && btnCancelar.getScene() != null && btnCancelar.getScene().getRoot() != null) {
                        for (javafx.scene.Node node : btnCancelar.getScene().getRoot().lookupAll("*")) {
                            if (node.getScene() != null) {
                                currentStage = (Stage) node.getScene().getWindow();
                                System.out.println("CancelarCitaController - Ventana obtenida desde nodo: " + node.getClass().getSimpleName());
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("CancelarCitaController - Error en búsqueda de nodos: " + e.getMessage());
                }
            }
            
            if (currentStage != null) {
                System.out.println("CancelarCitaController - Ventana encontrada, cargando VistaPaciente.fxml");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                Parent pacienteRoot = loader.load();
                
                Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
                currentStage.setScene(nuevaEscena);
                currentStage.setTitle("Panel Principal - Paciente");
                currentStage.centerOnScreen();
                currentStage.show(); // Asegurar que la ventana se muestre
                System.out.println("CancelarCitaController - Navegación exitosa al panel principal");
            } else {
                System.out.println("CancelarCitaController - Error: No se pudo obtener la ventana actual");
                // Crear una nueva ventana como último recurso
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                Parent pacienteRoot = loader.load();
                
                Stage newStage = new Stage();
                Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
                newStage.setScene(nuevaEscena);
                newStage.setTitle("Panel Principal - Paciente");
                newStage.centerOnScreen();
                newStage.show();
                System.out.println("CancelarCitaController - Nueva ventana creada como respaldo");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("CancelarCitaController - Error al cargar VistaPaciente.fxml: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo regresar al panel principal: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CancelarCitaController - Error inesperado: " + e.getMessage());
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
