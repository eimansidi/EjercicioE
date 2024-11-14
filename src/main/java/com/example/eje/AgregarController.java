package com.example.eje;

import com.example.eje.model.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para gestionar la ventana de agregar o modificar una persona.
 * Permite ingresar los datos de una nueva persona o editar los datos de una existente.
 */
public class AgregarController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtEdad;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private PersonasController helloController;
    private Persona personaOriginal;
    private boolean modoModificar;

    /**
     * Establece el controlador principal para comunicar ambos controladores.
     *
     * @param helloController El controlador principal de personas.
     */
    public void setMainController(PersonasController helloController) {
        this.helloController = helloController;
    }

    /**
     * Define si la ventana esta en modo modificar o agregar.
     * Cambia el texto del boton guardar segun el modo.
     *
     * @param modificar true si esta en modo modificar, false si esta en modo agregar.
     */
    public void setModoModificar(boolean modificar) {
        this.modoModificar = modificar;
        btnGuardar.setText(modificar ? "Modificar" : "Agregar");
    }

    /**
     * Llena los campos de texto con los datos de una persona para modificarla.
     *
     * @param persona La persona cuyos datos se mostraran en los campos de texto.
     */
    public void llenarCampos(Persona persona) {
        this.personaOriginal = persona;
        txtNombre.setText(persona.getNombre());
        txtApellidos.setText(persona.getApellidos());
        txtEdad.setText(String.valueOf(persona.getEdad()));
    }

    /**
     * Maneja el evento de guardar los datos de una persona.
     * Valida los campos, agrega una nueva persona o modifica una existente, y cierra la ventana.
     *
     * @param event Evento que dispara la accion.
     */
    @FXML
    void guardar(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String edadStr = txtEdad.getText();

        StringBuilder errores = new StringBuilder();
        if (nombre.isEmpty()) {
            errores.append("El campo nombre es obligatorio.\n");
        }

        if (apellidos.isEmpty()) {
            errores.append("El campo apellidos es obligatorio.\n");
        }

        int edad = -1;
        if (edadStr.isEmpty()) {
            errores.append("El campo edad es obligatorio.\n");
        } else {
            try {
                edad = Integer.parseInt(edadStr);
            } catch (NumberFormatException e) {
                errores.append("La edad debe ser un numero entero.\n");
            }
        }

        if (!errores.isEmpty()) {
            mostrarAlertaError("Datos invalidos", errores.toString());
            return;
        }

        Persona nuevaPersona = new Persona(nombre, apellidos, edad);

        if (modoModificar && personaOriginal != null) {
            helloController.modificarPersonaTabla(personaOriginal, nuevaPersona);
            mostrarAlertaExito("Info", "Persona modificada correctamente");
        } else {
            if (helloController.existePersona(nuevaPersona)) {
                mostrarAlertaError("Error", "No puede haber dos personas iguales.");
                return;
            }
            helloController.agregarPersonaTabla(nuevaPersona);
            mostrarAlertaExito("Info", "Persona añadida correctamente");
        }

        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento de cancelar la accion.
     * Cierra la ventana actual sin guardar cambios.
     *
     * @param event Evento que dispara la accion.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta de exito con un mensaje especifico.
     *
     * @param titulo Titulo de la alerta.
     * @param mensaje Mensaje de exito a mostrar.
     */
    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de error con un mensaje especifico.
     *
     * @param titulo Titulo de la alerta.
     * @param mensaje Mensaje de error a mostrar.
     */
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}