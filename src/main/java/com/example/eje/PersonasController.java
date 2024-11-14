package com.example.eje;

import com.example.eje.model.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar las operaciones sobre personas, como agregar, modificar y eliminar.
 */
public class PersonasController implements Initializable {

    @FXML
    private TableView<Persona> tableView;

    @FXML
    private TableColumn<Persona, String> nombre;

    @FXML
    private TableColumn<Persona, String> apellidos;

    @FXML
    private TableColumn<Persona, Integer> edad;

    /**
     * Inicializa las columnas de la tabla y las enlaza con las propiedades de Persona.
     *
     * @param location  Ubicacion utilizada para resolver rutas relativas para el objeto raiz, o null si no se conoce.
     * @param resources Recursos utilizados para localizar el objeto raiz, o null si no se especifican.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        edad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    }

    /**
     * Maneja el evento de agregar una nueva persona.
     * Abre una ventana modal para ingresar los datos de la nueva persona.
     *
     * @param event Evento que dispara la accion.
     */
    @FXML
    void agregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agregar.fxml"));
            Scene scene = new Scene(loader.load());

            AgregarController agregarController = loader.getController();
            agregarController.setMainController(this);
            agregarController.setModoModificar(false);

            Stage stage = new Stage();
            stage.setTitle("Nueva Persona");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de modificar una persona seleccionada.
     * Abre una ventana modal para editar los datos de la persona seleccionada.
     *
     * @param event Evento que dispara la accion.
     */
    @FXML
    void modificar(ActionEvent event) {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada == null) {
            mostrarAlertaError("Error", "Debes seleccionar una persona para modificarla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agregar.fxml"));
            Scene scene = new Scene(loader.load());

            AgregarController agregarController = loader.getController();
            agregarController.setMainController(this);
            agregarController.setModoModificar(true);
            agregarController.llenarCampos(personaSeleccionada);

            Stage stage = new Stage();
            stage.setTitle("Editar persona");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de eliminar una persona seleccionada.
     * Elimina la persona de la tabla y muestra un mensaje de confirmacion.
     *
     * @param event Evento que dispara la accion.
     */
    @FXML
    void eliminar(ActionEvent event) {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada == null) {
            mostrarAlertaError("Error", "Debes seleccionar una persona para eliminarla.");
            return;
        }

        tableView.getItems().remove(personaSeleccionada);
        mostrarAlertaExito("Eliminacion", "Persona eliminada correctamente.");
    }

    /**
     * Verifica si una persona ya existe en la tabla.
     *
     * @param persona La persona a verificar.
     * @return true si la persona existe, false en caso contrario.
     */
    public boolean existePersona(Persona persona) {
        return tableView.getItems().contains(persona);
    }

    /**
     * Agrega una nueva persona a la tabla.
     *
     * @param persona La nueva persona a agregar.
     */
    public void agregarPersonaTabla(Persona persona) {
        tableView.getItems().add(persona);
    }

    /**
     * Modifica una persona existente en la tabla.
     *
     * @param personaOriginal  La persona original a modificar.
     * @param personaModificada Los nuevos datos de la persona.
     */
    public void modificarPersonaTabla(Persona personaOriginal, Persona personaModificada) {
        int indice = tableView.getItems().indexOf(personaOriginal);
        tableView.getItems().set(indice, personaModificada);
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
